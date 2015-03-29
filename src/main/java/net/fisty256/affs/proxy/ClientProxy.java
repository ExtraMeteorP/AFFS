package net.fisty256.affs.proxy;

import net.fisty256.affs.container.ContainerConfigurator;
import net.fisty256.affs.container.ContainerForceCharger;
import net.fisty256.affs.container.ContainerForceCombiner;
import net.fisty256.affs.container.ContainerForceFieldProjector;
import net.fisty256.affs.container.ContainerForceGenerator;
import net.fisty256.affs.gui.GuiConfigurator;
import net.fisty256.affs.gui.GuiForceCharger;
import net.fisty256.affs.gui.GuiForceCombiner;
import net.fisty256.affs.gui.GuiForceFieldProjector;
import net.fisty256.affs.gui.GuiForceGenerator;
import net.fisty256.affs.gui.GuiInventoryCleaner;
import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.item.ItemInventoryCleaner;
import net.fisty256.affs.reference.GUIReferences;
import net.fisty256.affs.tileentity.TileEntityConfigurator;
import net.fisty256.affs.tileentity.TileEntityForceCharger;
import net.fisty256.affs.tileentity.TileEntityForceCombiner;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
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
		
		if (ID == GUIReferences.ID_FORCE_GENERATOR && te instanceof TileEntityForceGenerator)
		{
			TileEntityForceGenerator fe = (TileEntityForceGenerator)te;
			return new GuiForceGenerator(new ContainerForceGenerator(player, fe), fe, player);
		}
		else if (ID == GUIReferences.ID_FORCE_FIELD_PROJECTOR && te instanceof TileEntityForceFieldProjector)
		{
			TileEntityForceFieldProjector fe = (TileEntityForceFieldProjector)te;
			return new GuiForceFieldProjector(new ContainerForceFieldProjector(player, fe), fe, player);
		}
		else if (ID == GUIReferences.ID_FORCE_CHARGER && te instanceof TileEntityForceCharger)
		{
			TileEntityForceCharger fe = (TileEntityForceCharger)te;
			return new GuiForceCharger(new ContainerForceCharger(player, fe), fe, player);
		}
		else if (ID == GUIReferences.ID_CONFIGURATOR && te instanceof TileEntityConfigurator)
		{
			TileEntityConfigurator fe = (TileEntityConfigurator)te;
			return new GuiConfigurator(new ContainerConfigurator(player, fe), fe, player);
		}
		else if (ID == GUIReferences.ID_FORCE_COMBINER && te instanceof TileEntityForceCombiner)
		{
			TileEntityForceCombiner fe = (TileEntityForceCombiner)te;
			return new GuiForceCombiner(new ContainerForceCombiner(player, fe), fe, player);
		}
		
		return null;
	}
}