package net.fisty256.affs.gui;

import net.fisty256.affs.item.ItemConfigurable;
import net.fisty256.affs.item.ItemEnergyStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotConfigurableItem extends Slot {
	public SlotConfigurableItem(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack item)
	{
		if (item.getItem() instanceof ItemConfigurable)
			return true;
		return false;
	}
}