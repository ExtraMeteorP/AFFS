package net.fisty256.affs.network.message;

import io.netty.buffer.ByteBuf;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageForceFieldProjector implements IMessage, IMessageHandler<MessageForceFieldProjector, IMessage> {

	int x, y, z;
	int force;
	
	public MessageForceFieldProjector()
	{
		
	}
	
	public MessageForceFieldProjector(TileEntityForceFieldProjector te)
	{
		x = te.getPos().getX();
		y = te.getPos().getY();
		z = te.getPos().getZ();
		force = te.getForceAmount();
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		force = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(force);
	}
	
	@Override
	public IMessage onMessage(MessageForceFieldProjector msg, MessageContext ctx)
	{
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
		
		if (te instanceof TileEntityForceFieldProjector)
		{
			TileEntityForceFieldProjector tf = (TileEntityForceFieldProjector)te;
			
			tf.client_forceAmount = msg.force;
		}
		
		return null;
	}
}