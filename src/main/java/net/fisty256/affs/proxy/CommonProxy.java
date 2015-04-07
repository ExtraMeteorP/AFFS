package net.fisty256.affs.proxy;

import net.fisty256.affs.container.ContainerAreaProtector;
import net.fisty256.affs.container.ContainerConfigurator;
import net.fisty256.affs.container.ContainerForceCharger;
import net.fisty256.affs.container.ContainerForceCombiner;
import net.fisty256.affs.container.ContainerForceFieldProjector;
import net.fisty256.affs.container.ContainerForceGenerator;
import net.fisty256.affs.item.ItemInventoryCleaner;
import net.fisty256.affs.reference.GUIReferences;
import net.fisty256.affs.tileentity.TileEntityAreaProtector;
import net.fisty256.affs.tileentity.TileEntityConfigurator;
import net.fisty256.affs.tileentity.TileEntityForceCharger;
import net.fisty256.affs.tileentity.TileEntityForceCombiner;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public void registerRenders()
	{
		
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if (ID == GUIReferences.ID_FORCE_GENERATOR && te instanceof TileEntityForceGenerator)
		{
			TileEntityForceGenerator fe = (TileEntityForceGenerator)te;
			fe.container = new ContainerForceGenerator(player, fe);
			return fe.container;
		}
		else if (ID == GUIReferences.ID_FORCE_FIELD_PROJECTOR && te instanceof TileEntityForceFieldProjector)
		{
			TileEntityForceFieldProjector fe = (TileEntityForceFieldProjector)te;
			fe.container = new ContainerForceFieldProjector(player, fe);
			return fe.container;
		}
		else if (ID == GUIReferences.ID_FORCE_CHARGER && te instanceof TileEntityForceCharger)
		{
			TileEntityForceCharger fe = (TileEntityForceCharger)te;
			fe.container = new ContainerForceCharger(player, fe);
			return fe.container;
		}
		else if (ID == GUIReferences.ID_CONFIGURATOR && te instanceof TileEntityConfigurator)
		{
			TileEntityConfigurator fe = (TileEntityConfigurator)te;
			fe.container = new ContainerConfigurator(player, fe);
			return fe.container;
		}
		else if (ID == GUIReferences.ID_FORCE_COMBINER && te instanceof TileEntityForceCombiner)
		{
			TileEntityForceCombiner fe = (TileEntityForceCombiner)te;
			fe.container = new ContainerForceCombiner(player, fe);
			return fe.container;
		}
		else if (ID == GUIReferences.ID_AREA_PROTECTOR && te instanceof TileEntityAreaProtector)
		{
			TileEntityAreaProtector fe = (TileEntityAreaProtector)te;
			fe.container = new ContainerAreaProtector(player, fe);
			return fe.container;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}