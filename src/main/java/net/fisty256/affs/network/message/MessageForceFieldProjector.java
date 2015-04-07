package net.fisty256.affs.network.message;

import io.netty.buffer.ByteBuf;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;

public class MessageForceFieldProjector implements IMessage, IMessageHandler<MessageForceFieldProjector, IMessage> {

	int x, y, z;
	int force;
	int mode;
	int forcefieldX = 0;
	int forcefieldY = 0;
	int forcefieldZ = 0;
	boolean isRunning;
	int n, s, w, e, u, d;
	
	public MessageForceFieldProjector()
	{
		
	}
	
	public MessageForceFieldProjector(TileEntityForceFieldProjector te, ItemStack linkcardSlot)
	{
		x = te.getPos().getX();
		y = te.getPos().getY();
		z = te.getPos().getZ();
		force = te.getForceAmount(linkcardSlot);
		mode = te.mode;
		forcefieldX = te.forcefieldX;
		forcefieldY = te.forcefieldY;
		forcefieldZ = te.forcefieldZ;
		isRunning = te.isRunning;
		n = te.n;
		s = te.s;
		w = te.w;
		e = te.e;
		u = te.u;
		d = te.d;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		force = buf.readInt();
		mode = buf.readInt();
		forcefieldX = buf.readInt();
		forcefieldY = buf.readInt();
		forcefieldZ = buf.readInt();
		isRunning = buf.readBoolean();
		n = buf.readInt();
		s = buf.readInt();
		w = buf.readInt();
		e = buf.readInt();
		u = buf.readInt();
		d = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(force);
		buf.writeInt(mode);
		buf.writeInt(forcefieldX);
		buf.writeInt(forcefieldY);
		buf.writeInt(forcefieldZ);
		buf.writeBoolean(isRunning);
		buf.writeInt(n);
		buf.writeInt(s);
		buf.writeInt(w);
		buf.writeInt(e);
		buf.writeInt(u);
		buf.writeInt(d);
	}
	
	@Override
	public IMessage onMessage(MessageForceFieldProjector msg, MessageContext ctx)
	{
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
		
		if (te instanceof TileEntityForceFieldProjector)
		{
			TileEntityForceFieldProjector tf = (TileEntityForceFieldProjector)te;
			
			tf.client_forceAmount = msg.force;
			tf.mode = msg.mode;
			tf.forcefieldX = msg.forcefieldX;
			tf.forcefieldY = msg.forcefieldY;
			tf.forcefieldZ = msg.forcefieldZ;
			tf.isRunning = msg.isRunning;
			tf.n = msg.n;
			tf.s = msg.s;
			tf.w = msg.w;
			tf.e = msg.e;
			tf.u = msg.u;
			tf.d = msg.d;
		}
		
		return null;
	}
}