package net.fisty256.affs.gui;

import net.fisty256.affs.item.ItemEnergyStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnergyStorageItem extends Slot {

	public SlotEnergyStorageItem(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack item)
	{
		if (item.getItem() instanceof ItemEnergyStorage)
			return true;
		return false;
	}
}