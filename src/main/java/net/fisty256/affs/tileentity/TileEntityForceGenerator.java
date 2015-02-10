package net.fisty256.affs.tileentity;

import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceGenerator;
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

public class TileEntityForceGenerator extends TileEntity implements IInventory, IUpdatePlayerListBox {

	public Container container;
	protected ItemStack[] content = new ItemStack[3];
	
	public int forceStored = 0;
	public int forcePt = 0;
	public int burnTime = 0;
	public int upgradeBurnTime = 0;
	public int upgradeStrength = 0;
	
	private boolean sendUpdate = false;
	private int slowdownTimer = 0;
	private int slowdownTimerMax = 10;
	private boolean endInfoSent = false;
	
	private int SLOT_FUEL = 0;
	private int SLOT_UPGRADE = 1;
	private int SLOT_LINKCARD = 2;
	
	private int BURN_TIME_FULL = 400;
	private int MAX_BUFFER = 1000000;
	
	public void update()
	{
		if (!worldObj.isRemote) //Server side
		{
			if (burnTime > 0)
			{
				endInfoSent = false;
				
				forcePt = 50;
				
				if (upgradeBurnTime <= 0)
				{
					upgradeStrength = 0;
					if (getStackInSlot(SLOT_UPGRADE) != null)
					{
						if (getStackInSlot(SLOT_UPGRADE).getItem() == Items.redstone)
						{
							decrStackSize(SLOT_UPGRADE, 1);
							upgradeStrength = 70;
							upgradeBurnTime = 400;
						}
						else if (getStackInSlot(SLOT_UPGRADE).getItem() == Items.glowstone_dust)
						{
							decrStackSize(SLOT_UPGRADE, 1);
							upgradeStrength = 180;
							upgradeBurnTime = 400;
						}
					}
				}
				else
				{
					upgradeBurnTime--;
				}
				forcePt += upgradeStrength;
				
				if (forceStored < MAX_BUFFER)
				{
					forceStored += forcePt;
					if (forceStored > MAX_BUFFER)
					{
						forceStored = MAX_BUFFER;
					}
				}
				
				burnTime--;
				sendUpdate = true;
			}
			else
			{
				if (forceStored < MAX_BUFFER)
				{
					if (getStackInSlot(SLOT_FUEL) != null)
					{
						if (getStackInSlot(SLOT_FUEL).getItem() == Items.coal)
						{
							decrStackSize(SLOT_FUEL, 1);
							burnTime += BURN_TIME_FULL;
						}
						else
						{
							forcePt = 0;
							sendUpdate = true;
						}
					}
				}
				else if (!endInfoSent)
				{
					endInfoSent = true;
					sendUpdate = true;
				}
			}
            
			slowdownTimer++;
            if (sendUpdate && slowdownTimer >= slowdownTimerMax) //Send packet to the client twice a second telling it progress
            {
            	this.markDirty();
    			PacketHandler.INSTANCE.sendToAllAround(new MessageForceGenerator(this), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(),
    					(double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 128d));
                this.worldObj.notifyBlockOfStateChange(new BlockPos((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ()), this.getBlockType());
                sendUpdate = false;
                slowdownTimer = 0;
            }
		}
	}
	
	@Override
	public String getName() {
		return "Force Generator";
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
		if (index == 0)
		{
			return stack.getItem() == Items.coal; //Only coal can be inserted in fuel slot
		}
		else if (index == 1)
		{
			return stack.getItem() == Items.redstone | stack.getItem() == Items.glowstone_dust; //Only redstone or glowstone dust can be inserted in upgrade slot
		}
		else if (index == 2)
		{
			return stack.getItem() == ItemsAFFS.link_card; //Only link cards are allowed in link card slot
		}
		return false;
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
        
        nbt.setInteger("ForceStored", forceStored);
        nbt.setInteger("BurnTime", burnTime);
        nbt.setInteger("UpgradeBurnTime", upgradeBurnTime);
        nbt.setInteger("UpgradeStrength", upgradeStrength);
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
        
        forceStored = nbt.getInteger("ForceStored");
        burnTime = nbt.getInteger("BurnTime");
        upgradeBurnTime = nbt.getInteger("UpgradeBurnTime");
        upgradeStrength = nbt.getInteger("UpgradeStrength");
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.INSTANCE.getPacketFrom(new MessageForceGenerator(this));
	}
}