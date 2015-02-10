package net.fisty256.affs.gui;

import com.sun.media.jfxmedia.logging.Logger;

import net.fisty256.affs.container.ContainerForceGenerator;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiForceGenerator extends GuiContainer {

	private final ResourceLocation backgroundTexture;
	
	private TileEntityForceGenerator te;
	
	public GuiForceGenerator(Container container, TileEntityForceGenerator te) {
		super(container);
		
		this.te = te;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_generator.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166); //Draw background
		
		this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+65, 4210752);
		
		this.fontRendererObj.drawString("Force Stored: " + te.forceStored, guiLeft+40, guiTop+23, 4210752);
		this.fontRendererObj.drawString("Force Pt: " + te.forcePt, guiLeft+40, guiTop+40, 4210752);
		this.fontRendererObj.drawString(""+Math.round(te.burnTime/20), guiLeft+14, guiTop+73, 4210752);
		this.fontRendererObj.drawString(""+Math.round(te.upgradeBurnTime/20), guiLeft+14, guiTop+7, 4210752);
	}
}