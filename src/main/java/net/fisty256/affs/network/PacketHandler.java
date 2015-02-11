package net.fisty256.affs.network;

import net.fisty256.affs.network.message.MessageForceFieldProjector;
import net.fisty256.affs.network.message.MessageForceGenerator;
import net.fisty256.affs.network.message.MessageForceGeneratorButton;
import net.fisty256.affs.reference.ModReferences;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModReferences.MODID);
	
	public static void init()
	{
		INSTANCE.registerMessage(MessageForceGenerator.class, MessageForceGenerator.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageForceGeneratorButton.class, MessageForceGeneratorButton.class, 1, Side.SERVER);
		INSTANCE.registerMessage(MessageForceFieldProjector.class, MessageForceFieldProjector.class, 2, Side.CLIENT);
	}
}