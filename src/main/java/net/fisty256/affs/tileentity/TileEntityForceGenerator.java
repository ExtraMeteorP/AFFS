package net.fisty256.affs.tileentity;

import net.fisty256.affs.init.ItemsAFFS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntityForceGenerator extends TileEntity implements IInventory {

	public Container container;
	protected ItemStack[] content = new ItemStack[3];
	
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
	public ItemStack getStackInSlot(int index) {
		if (index >= getSizeInventory())
			return null;
		return content[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index >= getSizeInventory())
			return null;
		ItemStack is = getStackInSlot(index);
		if (is.stackSize > count)
		{
			is.stackSize -= count;
		}
		else if (is.stackSize <= count)
		{
			is = null;
		}
		return is;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return getStackInSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index >= getSizeInventory())
			return;
		content[index] = stack;
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
}