package net.fisty256.affs.gui;

import net.fisty256.affs.init.ItemsAFFS;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWhitelistCard extends Slot {
	public SlotWhitelistCard(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	public boolean isItemValid(ItemStack item)
	{
		return item.getItem() == ItemsAFFS.whitelist_card;
	}
	
	public int getSlotStackLimit()
    {
        return 1;
    }
}