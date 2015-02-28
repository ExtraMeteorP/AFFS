package net.fisty256.affs.tileentity;

import net.fisty256.affs.forceenergy.ForceDB;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.item.ItemConfigurable;
import net.fisty256.affs.item.ItemEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntityConfigurator extends TileEntity implements IInventory, IUpdatePlayerListBox {
	public Container container;
	protected ItemStack[] content = new ItemStack[2];
	
	private int SLOT_CONFIGURABLEI = 0;
	private int SLOT_CONFIG = 1;
	
	@Override
	public void update()
	{
		if (!worldObj.isRemote) //Server update
		{
			if (getStackInSlot(SLOT_CONFIGURABLEI) != null && getStackInSlot(SLOT_CONFIGURABLEI).getItem() instanceof ItemConfigurable)
			{
				((ItemConfigurable)getStackInSlot(SLOT_CONFIGURABLEI).getItem()).onConfigured(getStackInSlot(SLOT_CONFIGURABLEI), new ItemStack[]{getStackInSlot(SLOT_CONFIG)});
			}
		}
	}
	
	@Override
	public String getName() {
		return "Configurator";
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