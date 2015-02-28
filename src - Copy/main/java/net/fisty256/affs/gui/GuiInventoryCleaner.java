package net.fisty256.affs.gui;

import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceGeneratorButton;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryCleaner extends GuiContainer {
private final ResourceLocation backgroundTexture;
	
	private EntityPlayer player;
	
	public GuiInventoryCleaner(Container container, EntityPlayer player)
	{
		super(container);
		
		this.player = player;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/inventory_cleaner.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166); //Draw background
		
		//this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+65, 4210752);
	}
}