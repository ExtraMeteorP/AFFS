package net.fisty256.affs.creativetabs;

import net.fisty256.affs.init.ItemsAFFS;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabAFFS extends CreativeTabs {
	public TabAFFS()
	{
		super("tab_affs");
	}

	@Override
	public Item getTabIconItem()
	{
		return ItemsAFFS.inventory_cleaner;
	}
}