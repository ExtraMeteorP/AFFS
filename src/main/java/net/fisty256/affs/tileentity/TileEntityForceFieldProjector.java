package net.fisty256.affs.tileentity;

import net.fisty256.affs.forceenergy.ForceDB;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceFieldProjector;
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

public class TileEntityForceFieldProjector extends TileEntity implements IInventory, IUpdatePlayerListBox {

	public Container container;
	protected ItemStack[] content = new ItemStack[1];
	
	public int client_forceAmount = 0;
	
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
	
	public void updateHead()
	{
		syncTimer++;
		if (syncTimer >= 100)
		{
			syncTimer = 0;
			sendUpdate = true;
		}
		
		if (getForceAmount() != oldForceSent)
		{
			sendUpdate = true;
		}
	}
	
	public void updateBody()
	{
		
	}
	
	int syncTimer = 0;
	int slowdownTimer = 0;
	int slowdownTimerMax = 20;
	boolean sendUpdate = false;
	public void sendUpdate()
	{
		slowdownTimer++;
        if (sendUpdate && slowdownTimer >= slowdownTimerMax)
        {
        	oldForceSent = getForceAmount();
        	
        	this.markDirty();
			PacketHandler.INSTANCE.sendToAllAround(new MessageForceFieldProjector(this), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(),
					(double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 128d));
            this.worldObj.notifyBlockOfStateChange(new BlockPos((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ()), this.getBlockType());
            sendUpdate = false;
            slowdownTimer = 0;
        }
	}
	
	public void buttonEvent(int buttonID)
	{
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