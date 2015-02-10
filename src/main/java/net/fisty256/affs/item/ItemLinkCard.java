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

public class ItemLinkCard extends Item {
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List toolTip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			int x = nbt.getInteger("BlockX"), y = nbt.getInteger("BlockY"), z = nbt.getInteger("BlockZ");
			int id = nbt.getInteger("LinkID");
			toolTip.add("ID: " + id);
			toolTip.add("X: " + x + " Y: " + y + " Z: " + z);
			if (ForceDB.checkSource(id))
			{
				toolTip.add("Force: " + ForceDB.getSource(id));
			}
			else
			{
				toolTip.add("Cannot link!");
				stack.setStackDisplayName(EnumChatFormatting.RED + "Link Card - Wrong link");
			}
		}
		else
		{
			toolTip.clear();
			stack.setStackDisplayName("Link Card - Empty");
		}
	}
}