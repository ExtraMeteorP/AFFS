package net.fisty256.affs.tileentity;

import net.fisty256.affs.item.ItemEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.IChatComponent;

public class TileEntityForceCombiner extends TileEntityForceManager implements IInventory, IUpdatePlayerListBox {
	public Container container;
	protected ItemStack[] content = new ItemStack[3];
	
	private int SLOT_LINK1 = 0;
	private int SLOT_LINK2 = 1;
	private int SLOT_LINK3 = 2;
	
	@Override
	public void update()
	{
		if (!worldObj.isRemote) //Server update
		{
			if (getStackInSlot(SLOT_LINK1) != null && (getStackInSlot(SLOT_LINK2) != null | getStackInSlot(SLOT_LINK3) != null))
			{
				int max = 1000000 - getForceAmount(getStackInSlot(SLOT_LINK1));
				if (max > 0)
				{
					int ob;
					if (max > 10000)
					{
						max = 10000;
					}
					if (getStackInSlot(SLOT_LINK2) != null)
					{
						ob = getForceAmount(getStackInSlot(SLOT_LINK2));
						if (ob > 0)
						{
							if (ob > max)
							{
								ob = max;
							}
							increaseForceAmount(getStackInSlot(SLOT_LINK1), ob);
							decreaseForceAmount(getStackInSlot(SLOT_LINK2), ob);
							max -= ob;
						}
						ob = 0;
					}
					if (getStackInSlot(SLOT_LINK3) != null)
					{
						ob = getForceAmount(getStackInSlot(SLOT_LINK3));
						if (ob > 0)
						{
							if (ob > max)
							{
								ob = max;
							}
							increaseForceAmount(getStackInSlot(SLOT_LINK1), ob);
							decreaseForceAmount(getStackInSlot(SLOT_LINK3), ob);
							max -= ob;
						}
						ob = 0;
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Force Combiner";
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
}