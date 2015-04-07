package net.fisty256.affs.network.message;

import io.netty.buffer.ByteBuf;
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

public class MessageForceGenerator implements IMessage, IMessageHandler<MessageForceGenerator, IMessage> {
	private int x, y, z;
	private int forceStored = 0;
	private int forcePt = 0;
	private int burnTime = 0;
	private int upgradeBurnTime = 0;
	private int upgradeStrength = 0;
	private boolean turnedOn = false;
	
	public MessageForceGenerator()
	{
		
	}
	
	public MessageForceGenerator(TileEntityForceGenerator te)
	{
		x = te.getPos().getX();
		y = te.getPos().getY();
		z = te.getPos().getZ();
		forceStored = te.forceStored;
		forcePt = te.forcePt;
		burnTime = te.burnTime;
		upgradeBurnTime = te.upgradeBurnTime;
		upgradeStrength = te.upgradeStrength;
		turnedOn = te.turnedOn;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		forceStored = buf.readInt();
		forcePt = buf.readInt();
		burnTime = buf.readInt();
		upgradeBurnTime = buf.readInt();
		upgradeStrength = buf.readInt();
		turnedOn = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(forceStored);
		buf.writeInt(forcePt);
		buf.writeInt(burnTime);
		buf.writeInt(upgradeBurnTime);
		buf.writeInt(upgradeStrength);
		buf.writeBoolean(turnedOn);
	}

	@Override
	public IMessage onMessage(MessageForceGenerator msg, MessageContext ctx)
	{
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
		
		if (te instanceof TileEntityForceGenerator)
		{
			TileEntityForceGenerator tf = (TileEntityForceGenerator)te;
			
			tf.forceStored = msg.forceStored;
			tf.forcePt = msg.forcePt;
			tf.burnTime = msg.burnTime;
			tf.upgradeBurnTime = msg.upgradeBurnTime;
			tf.upgradeStrength = msg.upgradeStrength;
			tf.turnedOn = msg.turnedOn;
		}
		
		return null;
	}
	
	public String toString()
	{
		return String.format("MessageForceGenerator");
	}
}
