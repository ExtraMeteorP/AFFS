package net.fisty256.affs.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.fisty256.affs.forceenergy.ForceDB;
import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceFieldProjector;
import net.fisty256.affs.network.message.MessageForceGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityForceFieldProjector extends TileEntity implements IInventory, IUpdatePlayerListBox {

	public Container container;
	protected ItemStack[] content = new ItemStack[1];
	
	public int client_forceAmount = 0;
	
	public boolean isRunning = false;
	public int mode = 0;
	
	public int forcefieldX = 0;
	public int forcefieldY = 0;
	public int forcefieldZ = 0;
	public int n, s, w, e, u, d;
	
	public int[] originalPositionsX;
	public int[] originalPositionsY;
	public int[] originalPositionsZ;
	
	public int[] positionsX;
	public int[] positionsY;
	public int[] positionsZ;
	
	private int oldForceSent = 0;
	
	private int SLOT_LINKCARD = 0;
	
	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			updateHead();
			updateBody();

			sendUpdate();
		}
	}
	
	int syncTimer = 0;
	
	public void updateHead()
	{
		syncTimer++;
		if (syncTimer >= 20)
		{
			syncTimer = 0;
			sendUpdate = true;
		}
		
		if (getForceAmount() != oldForceSent)
		{
			sendUpdate = true;
		}
		
		if (this.worldObj.isBlockPowered(new BlockPos(this.getPos())))
		{
			this.isRunning = true;
		}
		else
		{
			this.isRunning = false;
		}
	}
	
	public void updateBody()
	{
		if (isRunning && getForceAmount() >= positionsX.length)
		{
			decreaseForceAmount(positionsX.length);
			if (positionsX != null)
			{
				tryPlaceBlocks();
			}
		}
		else
		{
			if (positionsX != null)
			{
				removePlacedBlocks();
			}
		}
	}
	
	private void tryPlaceBlocks()
	{
		for (int i = 0; i < positionsX.length; i++)
		{
			BlockPos pos = new BlockPos(getPos().getX()+forcefieldX+positionsX[i], getPos().getY()+forcefieldY+positionsY[i], getPos().getZ()+forcefieldZ+positionsZ[i]);
			if (this.worldObj.isAirBlock(pos))
				this.worldObj.setBlockState(pos, BlocksAFFS.force_field.getDefaultState());
		}
	}
	
	private void removePlacedBlocks()
	{
		for (int i = 0; i < originalPositionsX.length; i++)
		{
			BlockPos pos = new BlockPos(getPos().getX()+forcefieldX+originalPositionsX[i], getPos().getY()+forcefieldY+originalPositionsY[i], getPos().getZ()+forcefieldZ+originalPositionsZ[i]);
			if (this.worldObj.getBlockState(pos).getBlock() == BlocksAFFS.force_field)
				this.worldObj.setBlockToAir(pos);
		}
	}
	
	private void calculateBlocks()
	{
		List<Integer> posX = new ArrayList<Integer>();
		List<Integer> posY = new ArrayList<Integer>();
		List<Integer> posZ = new ArrayList<Integer>();
		
		int west = w - (2*w);
		int north = n - (2*n);
		int down = d - (2*d);
		for (int x = west; x < e+1; x++)
		{
			for (int y = down; y < u+1; y++)
			{
				for (int z = north; z < s+1; z++)
				{
					if (mode == 0)
					{
						if (x == west | x == e | z == north | z == s | y == down | y == u)
						{
							posX.add(x);
							posY.add(y);
							posZ.add(z);
						}
					}
					else if (mode == 1)
					{
						if (x == west | x == e | z == north | z == s)
						{
							posX.add(x);
							posY.add(y);
							posZ.add(z);
						}
					}
				}
			}
		}
		
		originalPositionsX = new int[posX.size()];
		for (int i = 0; i < posX.size(); i++)
		{
			originalPositionsX[i] = posX.get(i);
		}
		originalPositionsY = new int[posY.size()];
		for (int i = 0; i < posY.size(); i++)
		{
			originalPositionsY[i] = posY.get(i);
		}
		originalPositionsZ = new int[posZ.size()];
		for (int i = 0; i < posZ.size(); i++)
		{
			originalPositionsZ[i] = posZ.get(i);
		}
		positionsX = originalPositionsX;
		positionsY = originalPositionsY;
		positionsZ = originalPositionsZ;
	}
	
	boolean sendUpdate = false;
	
	int slowdownCounter = 0;
	public void sendUpdate()
	{
		slowdownCounter++;
        if (sendUpdate && slowdownCounter >= 10)
        {
        	slowdownCounter = 0;
        	oldForceSent = getForceAmount();
        	
        	this.markDirty();
			PacketHandler.INSTANCE.sendToAllAround(new MessageForceFieldProjector(this), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(),
					(double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 128d));
            this.worldObj.notifyBlockOfStateChange(new BlockPos((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ()), this.getBlockType());
            sendUpdate = false;
        }
	}
	
	public void buttonEvent(int buttonID)
	{
		if (buttonID == 0) //Mode
		{
			mode++;
			if (mode > 1)
				mode = 0;
			calculateBlocks();
		}
		else if (!isRunning && buttonID >= 1 && buttonID <= 18)
		{
			if (buttonID == 1) //Position X++
			{
				if (forcefieldX < 50)
					forcefieldX++;
			}
			else if (buttonID == 2) //Position X--
			{
				if (forcefieldX > -50)
					forcefieldX--;
			}
			else if (buttonID == 3) //Position Y++
			{
				if (forcefieldY < 50)
					forcefieldY++;
			}
			else if (buttonID == 4) //Position Y--
			{
				if (forcefieldY > -50)
					forcefieldY--;
			}
			else if (buttonID == 5) //Position Z++
			{
				if (forcefieldZ < 50)
					forcefieldZ++;
			}
			else if (buttonID == 6) //Position Z--
			{
				if (forcefieldZ > -50)
					forcefieldZ--;
			}
			else if (buttonID == 7) //Size N++
			{
				if (n < 50)
					n++;
			}
			else if (buttonID == 8) //Size N--
			{
				if (n > 0)
					n--;
			}
			else if (buttonID == 9) //Size S++
			{
				if (s < 50)
					s++;
			}
			else if (buttonID == 10) //Size S--
			{
				if (s > 0)
					s--;
			}
			else if (buttonID == 11) //Size W++
			{
				if (w < 50)
					w++;
			}
			else if (buttonID == 12) //Size W--
			{
				if (w > 0)
					w--;
			}
			else if (buttonID == 13) //Size E++
			{
				if (e < 50)
					e++;
			}
			else if (buttonID == 14) //Size E--
			{
				if (e > 0)
					e--;
			}
			else if (buttonID == 15) //Size U++
			{
				if (u < 50)
					u++;
			}
			else if (buttonID == 16) //Size U--
			{
				if (u > 0)
					u--;
			}
			else if (buttonID == 17) //Size D++
			{
				if (d < 50)
					d++;
			}
			else if (buttonID == 18) //Size D--
			{
				if (d > 0)
					d--;
			}
			calculateBlocks();
		}
		sendUpdate = true;
	}
	
	@Override
	public String getName() {
		return "ForceField Projector";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return content.length;
	}

	@Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return content[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (content[slotIndex] != null)
        {
            ItemStack itemStack = content[slotIndex];
            content[slotIndex] = null;
            return itemStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
    	content[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < content.length; ++currentIndex)
        {
            if (content[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                content[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbt.setTag("InventoryContent", tagList);
        
        nbt.setBoolean("IsRunning", isRunning);
        nbt.setInteger("FposX", forcefieldX);
        nbt.setInteger("FposY", forcefieldY);
        nbt.setInteger("FposZ", forcefieldZ);
        nbt.setIntArray("BlocksX", positionsX);
        nbt.setIntArray("BlocksY", positionsY);
        nbt.setIntArray("BlocksZ", positionsZ);
        nbt.setIntArray("OrigBlocksX", originalPositionsX);
        nbt.setIntArray("OrigBlocksY", originalPositionsY);
        nbt.setIntArray("OrigBlocksZ", originalPositionsZ);
        nbt.setInteger("n", n);
        nbt.setInteger("s", s);
        nbt.setInteger("w", w);
        nbt.setInteger("e", e);
        nbt.setInteger("u", u);
        nbt.setInteger("d", d);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		NBTTagList tagList = nbt.getTagList("InventoryContent", 10);
		content = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < content.length)
            {
            	content[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
        
        this.isRunning = nbt.getBoolean("IsRunning");
        forcefieldX = nbt.getInteger("FposX");
        forcefieldY = nbt.getInteger("FposY");
        forcefieldZ = nbt.getInteger("FposZ");
        positionsX = nbt.getIntArray("BlocksX");
        positionsY = nbt.getIntArray("BlocksY");
        positionsZ = nbt.getIntArray("BlocksZ");
        originalPositionsX = nbt.getIntArray("OrigBlocksX");
        originalPositionsY = nbt.getIntArray("OrigBlocksY");
        originalPositionsZ = nbt.getIntArray("OrigBlocksZ");
        n = nbt.getInteger("n");
        s = nbt.getInteger("s");
        w = nbt.getInteger("w");
        e = nbt.getInteger("e");
        u = nbt.getInteger("u");
        d = nbt.getInteger("d");
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.INSTANCE.getPacketFrom(new MessageForceFieldProjector(this));
	}
	
	/* Force util */
	public int getForceAmount()
	{
		if (getStackInSlot(SLOT_LINKCARD) != null && getStackInSlot(SLOT_LINKCARD).getItem() == ItemsAFFS.link_card)
		{
			ItemStack slot = getStackInSlot(SLOT_LINKCARD);
			if (slot.getTagCompound() != null)
			{
				NBTTagCompound nbt = slot.getTagCompound();
				if (ForceDB.checkSource(nbt.getInteger("LinkID")))
				{
					return ForceDB.getSource(nbt.getInteger("LinkID"));
				}
			}
		}
		return 0;
	}
	
	public void setForceAmount(int amount)
	{
		if (getStackInSlot(SLOT_LINKCARD) != null && getStackInSlot(SLOT_LINKCARD).getItem() == ItemsAFFS.link_card)
		{
			ItemStack slot = getStackInSlot(SLOT_LINKCARD);
			if (slot.getTagCompound() != null)
			{
				NBTTagCompound nbt = slot.getTagCompound();
				if (ForceDB.checkSource(nbt.getInteger("LinkID")))
				{
					ForceDB.setSource(nbt.getInteger("LinkID"), amount);
				}
			}
		}
	}
	
	public void decreaseForceAmount(int amount)
	{
		setForceAmount(getForceAmount()-amount);
	}
	
	public void increaseForceAmount(int amount)
	{
		setForceAmount(getForceAmount()+amount);
	}
	
	public boolean canDecreaseBy(int amount)
	{
		if (getForceAmount() >= amount)
			return true;
		return false;
	}
}