package net.fisty256.affs.proxy;

import net.fisty256.affs.container.ContainerForceGenerator;
import net.fisty256.affs.gui.GuiForceGenerator;
import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders()
	{
		BlocksAFFS.registerTextures();
		ItemsAFFS.registerTextures();
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te != null)
		{
			if (te instanceof TileEntityForceGenerator)
			{
				TileEntityForceGenerator fe = (TileEntityForceGenerator)te;
				return new GuiForceGenerator(new ContainerForceGenerator(player, fe));
			}
		}
		return null;
	}
}