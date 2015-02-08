package net.fisty256.affs;

import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.proxy.CommonProxy;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.reference.ProxyReferences;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
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
		proxy.registerRenders();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		GameRegistry.registerTileEntity(TileEntityForceGenerator.class, "TileEntityForceGenerator");
	}
}