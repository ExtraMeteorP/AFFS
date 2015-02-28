package net.fisty256.affs;

import net.fisty256.affs.creativetabs.TabAFFS;
import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.init.RecipesAFFS;
import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.proxy.CommonProxy;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.reference.ProxyReferences;
import net.fisty256.affs.tileentity.TileEntityConfigurator;
import net.fisty256.affs.tileentity.TileEntityForceCharger;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModReferences.MODID, version = ModReferences.VERSION)
public class AFFS {
	
	public static AFFS instance;
	
	public static CreativeTabs tab_affs = new TabAFFS();
	
	@SidedProxy(clientSide = ProxyReferences.CLIENT_PROXY_CLASS, serverSide = ProxyReferences.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		
		BlocksAFFS.init();
		BlocksAFFS.register();
		ItemsAFFS.init();
		ItemsAFFS.register();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RecipesAFFS.registerOreDict();
		RecipesAFFS.registerCrafting();
		RecipesAFFS.registerSmelting();
		RecipesAFFS.registerCustom();
		
		proxy.registerRenders();
		
		PacketHandler.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		GameRegistry.registerTileEntity(TileEntityForceGenerator.class, "TileEntityForceGenerator");
		GameRegistry.registerTileEntity(TileEntityForceFieldProjector.class, "TileEntityForceFieldProjector");
		GameRegistry.registerTileEntity(TileEntityForceCharger.class, "TileEntityForceCharger");
		GameRegistry.registerTileEntity(TileEntityConfigurator.class, "TileEntityConfigurator");
	}
}