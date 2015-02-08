package net.fisty256.affs.gui;

import net.fisty256.affs.reference.ModReferences;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiForceGenerator extends GuiContainer {

	private final ResourceLocation backgroundTexture;
	
	public GuiForceGenerator(Container container) {
		super(container);
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_generator.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(backgroundTexture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166);
	}
}