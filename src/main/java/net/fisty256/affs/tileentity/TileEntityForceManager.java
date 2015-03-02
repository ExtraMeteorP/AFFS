package net.fisty256.affs.tileentity;

import net.fisty256.affs.forceenergy.ForceDB;
import net.fisty256.affs.init.ItemsAFFS;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityForceManager extends TileEntitySyncedMachine {
	public int getForceAmount(ItemStack slot)
	{
		if (slot != null && slot.getItem() == ItemsAFFS.link_card)
		{
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
	
	public void setForceAmount(ItemStack slot, int amount)
	{
		if (slot != null && slot.getItem() == ItemsAFFS.link_card)
		{
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
	
	public void decreaseForceAmount(ItemStack slot, int amount)
	{
		setForceAmount(slot, getForceAmount(slot)-amount);
	}
	
	public void increaseForceAmount(ItemStack slot, int amount)
	{
		setForceAmount(slot, getForceAmount(slot)+amount);
	}
	
	public boolean canDecreaseBy(ItemStack slot, int amount)
	{
		if (getForceAmount(slot) >= amount)
			return true;
		return false;
	}
}