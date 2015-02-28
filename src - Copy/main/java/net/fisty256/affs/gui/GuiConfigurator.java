package net.fisty256.affs.gui;

import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityConfigurator;
import net.fisty256.affs.tileentity.TileEntityForceCharger;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiConfigurator extends GuiContainer {
	private final ResourceLocation backgroundTexture;
	
	private TileEntityConfigurator te;
	
	private EntityPlayer player;
	
	public GuiConfigurator(Container container, TileEntityConfigurator te, EntityPlayer player)
	{
		super(container);
		
		this.te = te;
		this.player = player;
		
		this.xSize = 176;
		this.ySize = 223;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/configurator.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 222); //Draw background
		
		//this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+125, 4210752);
	}
}