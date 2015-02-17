package net.fisty256.affs.item;

import java.util.List;

import net.fisty256.affs.forceenergy.ForceDB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInventoryCleaner extends ItemEnergyStorage {
	@Override
	public int getMaxForce()
	{
		return 500000;
	}
}