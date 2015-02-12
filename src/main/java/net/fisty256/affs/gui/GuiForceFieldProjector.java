package net.fisty256.affs.gui;

import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceFieldProjectorButton;
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
		
		this.fontRendererObj.drawString("Force: " + te.client_forceAmount, guiLeft+97, guiTop+12, 4210752);
		
		this.fontRendererObj.drawString("Mode:", guiLeft+7, guiTop+12, 4210752);
		String modeString = te.mode+"";
		switch (te.mode)
		{
		case 0:
			modeString = "Cube";
			break;
		case 1:
			modeString = "Walls";
			break;
		}
		((GuiButton)buttonList.get(0)).displayString = modeString;
		
		this.fontRendererObj.drawString("Position:", guiLeft+7, guiTop+30, 4210752);
		this.fontRendererObj.drawString("X: " + te.forcefieldX, guiLeft+13, guiTop+41, 4210752);
		((GuiButton)buttonList.get(1)).xPosition = guiLeft+45;
		((GuiButton)buttonList.get(1)).yPosition = guiTop+39;
		((GuiButton)buttonList.get(2)).xPosition = guiLeft+57;
		((GuiButton)buttonList.get(2)).yPosition = guiTop+39;
		this.fontRendererObj.drawString("Y: " + te.forcefieldY, guiLeft+13, guiTop+51, 4210752);
		((GuiButton)buttonList.get(3)).xPosition = guiLeft+45;
		((GuiButton)buttonList.get(3)).yPosition = guiTop+49;
		((GuiButton)buttonList.get(4)).xPosition = guiLeft+57;
		((GuiButton)buttonList.get(4)).yPosition = guiTop+49;
		this.fontRendererObj.drawString("Z: " + te.forcefieldZ, guiLeft+13, guiTop+61, 4210752);
		((GuiButton)buttonList.get(5)).xPosition = guiLeft+45;
		((GuiButton)buttonList.get(5)).yPosition = guiTop+59;
		((GuiButton)buttonList.get(6)).xPosition = guiLeft+57;
		((GuiButton)buttonList.get(6)).yPosition = guiTop+59;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(new GuiButton(0, guiLeft+35, guiTop+6, 60, 20, "Cube"));
		this.buttonList.add(new GuiButton(1, guiLeft+35, guiTop+30, 10, 10, "+"));	this.buttonList.add(new GuiButton(2, guiLeft+55, guiTop+30, 10, 10, "-"));
		this.buttonList.add(new GuiButton(3, guiLeft+35, guiTop+30, 10, 10, "+"));	this.buttonList.add(new GuiButton(4, guiLeft+55, guiTop+30, 10, 10, "-"));
		this.buttonList.add(new GuiButton(5, guiLeft+35, guiTop+30, 10, 10, "+"));	this.buttonList.add(new GuiButton(6, guiLeft+55, guiTop+30, 10, 10, "-"));
	}
	
	protected void actionPerformed(GuiButton guibutton)
	{
        PacketHandler.INSTANCE.sendToServer(new MessageForceFieldProjectorButton(te.getPos(), player.dimension, guibutton.id));
	}
}