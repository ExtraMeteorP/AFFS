package net.fisty256.affs.item;

import java.util.List;

import net.fisty256.affs.forceenergy.ForceDB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWhitelistCard extends Item {
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String uuid = nbt.getString("uuid");
			String name = nbt.getString("name");
			if (uuid == "*NONE*")
			{
				toolTip.add(EnumChatFormatting.RED + "Right click to link!");
			}
			else
			{
				toolTip.add(EnumChatFormatting.GREEN + "Linked to: " + name);
				toolTip.add(EnumChatFormatting.GOLD + "UUID: " + uuid);
			}
		}
		else
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("uuid", "*NONE*");
			stack.getTagCompound().setString("name", "*NONE*");
		}
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player)
    {
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = stack.getTagCompound();
		
		if (player.isSneaking())
		{
			nbt.setString("uuid", "*NONE*");
			nbt.setString("name", "*NONE*");
			return stack;
		}
		
		nbt.setString("uuid", player.getUniqueID().toString());
		nbt.setString("name", player.getDisplayNameString());
		
        return stack;
    }
	
	
	public static boolean isValid(ItemStack stack)
	{
		if (stack == null)
			return false;
		
		if (stack.getTagCompound() == null)
			return false;
		
		if (stack.getTagCompound().getString("uuid") == "*NONE*")
			return false;
		
		if (stack.getTagCompound().getString("name") == "*NONE*")
			return false;
		
		return true;
	}
	
	public static String getPlayerUUID(ItemStack stack)
	{
		if (isValid(stack))
		{
			return stack.getTagCompound().getString("uuid");
		}
		return null;
	}
	
	public static String getPlayerVisibleName(ItemStack stack)
	{
		if (isValid(stack))
		{
			return stack.getTagCompound().getString("name");
		}
		return null;
	}
}