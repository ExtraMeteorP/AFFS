package net.fisty256.affs.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemConfigurable extends Item {
	public int getCountRequiredSlots()
	{
		return 0;
	}
	
	public boolean isItemValid(int slot, ItemStack stack) //TODO: not implemented yet
	{
		return false;
	}
	
	public boolean onConfigured(ItemStack stackConfigured, ItemStack[] configs)
	{
		return false; //TODO: return true if you want items to be removed from configurator after done (not implemented yet)
	}
}