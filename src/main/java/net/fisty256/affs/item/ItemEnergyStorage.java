package net.fisty256.affs.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnergyStorage extends ItemConfigurable {
	
	public ItemEnergyStorage()
	{
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean advanced)
	{
		int force = getForceAmount(stack);
		if (force == 0)
		{
			toolTip.add(EnumChatFormatting.RED + "Force: " + force);
		}
		else
		{
			toolTip.add(EnumChatFormatting.GREEN + "Force: " + force);
		}
	}
	
	public int getMaxForce()
	{
		return 0;
	}
	
	public int getForceAmount(ItemStack stack)
	{
		if (stack.getTagCompound() != null)
		{
			return stack.getTagCompound().getInteger("Force");
		}
		else
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("Force", 0);
			return 0;
		}
	}
	
	public void setForceAmount(ItemStack stack, int amount)
	{
		if (stack.getTagCompound() != null)
		{
			stack.getTagCompound().setInteger("Force", amount);
		}
		else
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("Force", amount);
		}
	}
	
	public int increaseForceAmount(ItemStack stack, int amount)
	{
		int last = 0;
		if (stack.getTagCompound() != null)
		{
			int force = stack.getTagCompound().getInteger("Force");
			if (force + amount > getMaxForce())
			{
				last = (force + amount) - getMaxForce();
				stack.getTagCompound().setInteger("Force", getMaxForce());
			}
			else
			{
				stack.getTagCompound().setInteger("Force", force + amount);
			}
		}
		else
		{
			stack.setTagCompound(new NBTTagCompound());
			if (amount > getMaxForce())
			{
				last = amount - getMaxForce();
				stack.getTagCompound().setInteger("Force", getMaxForce());
			}
			else
			{
				stack.getTagCompound().setInteger("Force", amount);
			}
		}
		return last;
	}
	
	public boolean decreaseForceAmount(ItemStack stack, int amount)
	{
		if (stack.getTagCompound() != null)
		{
			int force = stack.getTagCompound().getInteger("Force");
			if (force-amount < 0)
			{
				stack.getTagCompound().setInteger("Force", 0);
				return false;
			}
			else
			{
				stack.getTagCompound().setInteger("Force", force-amount);
				return true;
			}
		}
		else
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("Force", 0);
			return false;
		}
	}
}