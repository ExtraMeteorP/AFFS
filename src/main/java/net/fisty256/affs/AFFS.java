package net.fisty256.affs;

import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.proxy.CommonProxy;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.reference.ProxyReferences;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModReferences.MODID, version = ModReferences.VERSION)
public class AFFS {
	
	@SidedProxy(clientSide = ProxyReferences.CLIENT_PROXY_CLASS, serverSide = ProxyReferences.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		BlocksAFFS.init();
		BlocksAFFS.register();
		ItemsAFFS.init();
		ItemsAFFS.register();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
	}
}