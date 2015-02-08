package net.fisty256.affs.proxy;

import net.fisty256.affs.init.BlocksAFFS;
import net.fisty256.affs.init.ItemsAFFS;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders()
	{
		BlocksAFFS.registerTextures();
		ItemsAFFS.registerTextures();
	}
}