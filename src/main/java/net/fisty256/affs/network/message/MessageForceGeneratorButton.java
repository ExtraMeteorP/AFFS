package net.fisty256.affs.network.message;

import io.netty.buffer.ByteBuf;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageForceGeneratorButton implements IMessage, IMessageHandler<MessageForceGeneratorButton, IMessage> {
	int x, y, z;
	int button;
	
	public MessageForceGeneratorButton()
	{
		
	}
	
	public MessageForceGeneratorButton(BlockPos pos, int btn)
	{
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		button = btn;
	}
	
	@Override
	public IMessage onMessage(MessageForceGeneratorButton msg, MessageContext ctx) {
		TileEntity te = FMLClientHandler.instance().getServer().worldServerForDimension(0).getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
		
		if (te instanceof TileEntityForceGenerator)
		{
			TileEntityForceGenerator tf = (TileEntityForceGenerator)te;
			
			tf.buttonEvent(msg.button);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		button = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(button);
	}
	
	public String toString()
	{
		return String.format("MessageForceGeneratorButton");
	}
}