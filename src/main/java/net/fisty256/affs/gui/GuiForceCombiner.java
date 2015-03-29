package net.fisty256.affs.gui;

import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityForceCombiner;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiForceCombiner extends GuiContainer {
private final ResourceLocation backgroundTexture;
	
	private TileEntityForceCombiner te;
	
	private EntityPlayer player;
	
	public GuiForceCombiner(Container container, TileEntityForceCombiner te, EntityPlayer player)
	{
		super(container);
		
		this.te = te;
		this.player = player;
		
		this.xSize = 176;
		this.ySize = 223;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_combiner.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 222); //Draw background
		
		//this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+125, 4210752);
	}
}