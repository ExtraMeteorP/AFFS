package net.fisty256.affs.item;

import java.util.List;

import net.fisty256.affs.AFFS;
import net.fisty256.affs.forceenergy.ForceDB;
import net.fisty256.affs.reference.GUIReferences;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInventoryCleaner extends ItemEnergyStorage {
	
	@Override
	public int getMaxForce()
	{
		return 500000;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean advanced)
	{
		super.addInformation(stack, player, toolTip, advanced);
		
		if (stack.getTagCompound().getInteger("Item") > 0)
			toolTip.add(EnumChatFormatting.AQUA + "Item: " + Item.getItemById(stack.getTagCompound().getInteger("Item")).getUnlocalizedName().substring(5));
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (entity instanceof EntityPlayer && !isSelected)
		{
			if (getForceAmount(stack) > 0)
			{
				if (stack.getTagCompound().getInteger("Item") > 0)
				{
					EntityPlayer player = (EntityPlayer)entity;
					InventoryPlayer inv = player.inventory;
					for (int i = 0; i < inv.getSizeInventory(); i++)
					{
						if (inv.getStackInSlot(i) != null && Item.getIdFromItem(inv.getStackInSlot(i).getItem()) == stack.getTagCompound().getInteger("Item"))
						{
							inv.decrStackSize(i, 1);
							decreaseForceAmount(stack, 1);
							return;
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getCountRequiredSlots()
	{
		return 1;
	}
	
	@Override
	public boolean isItemValid(int slot, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean onConfigured(ItemStack stackConfigured, ItemStack[] configs)
	{
		NBTTagCompound nbt = stackConfigured.getTagCompound();
		if (nbt == null)
			nbt = new NBTTagCompound();
		
		if (configs[0] != null)
			nbt.setInteger("Item", Item.getIdFromItem(configs[0].getItem()));
		return false;
	}
}