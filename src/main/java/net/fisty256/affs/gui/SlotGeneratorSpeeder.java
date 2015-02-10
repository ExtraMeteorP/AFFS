package net.fisty256.affs.gui;

import net.fisty256.affs.init.ItemsAFFS;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGeneratorSpeeder extends Slot {
	public SlotGeneratorSpeeder(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	public boolean isItemValid(ItemStack item)
	{
		return item.getItem() == Items.glowstone_dust || item.getItem() == Items.redstone;
	}
}