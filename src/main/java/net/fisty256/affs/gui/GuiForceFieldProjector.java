package net.fisty256.affs.gui;

import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceGeneratorButton;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityForceFieldProjector;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiForceFieldProjector extends InventoryEffectRenderer {
private final ResourceLocation backgroundTexture;
	
	private TileEntityForceFieldProjector te;
	
	private EntityPlayer player;
	
	public GuiForceFieldProjector(Container container, TileEntityForceFieldProjector te, EntityPlayer player)
	{
		super(container);
		
		this.te = te;
		
		this.player = player;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_field_projector.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166); //Draw background
		
		this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+65, 4210752);
		
		this.fontRendererObj.drawString("Force: " + te.client_forceAmount, guiLeft+7, guiTop+65, 4210752);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		//this.buttonList.add(new GuiButton(0, guiLeft+35, guiTop+60, 60, 20, ""));
	}
	
	protected void actionPerformed(GuiButton guibutton)
	{
        //PacketHandler.INSTANCE.sendToServer(new MessageForceGeneratorButton(te.getPos(), player.dimension, guibutton.id));
	}
}