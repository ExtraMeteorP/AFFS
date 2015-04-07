package net.fisty256.affs.gui;

import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityAreaProtector;
import net.fisty256.affs.tileentity.TileEntityConfigurator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiAreaProtector extends GuiContainer {
private final ResourceLocation backgroundTexture;
	
	private TileEntityAreaProtector te;
	
	private EntityPlayer player;
	
	public GuiAreaProtector(Container container, TileEntityAreaProtector te, EntityPlayer player)
	{
		super(container);
		
		this.te = te;
		this.player = player;
		
		this.xSize = 176;
		this.ySize = 223;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/area_protector.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 222); //Draw background
		
		this.fontRendererObj.drawString("Radius: " + te.currentRadius, guiLeft+8, guiTop+8, 4210752);
		this.fontRendererObj.drawString("Force Usage: " + te.currentForceUsage, guiLeft+8, guiTop+20, 4210752);
		this.fontRendererObj.drawString("Force Usage per hit: " + te.forceUsagePerHit, guiLeft+8, guiTop+32, 4210752);
		
		if (te.isRunning)
		{
			this.fontRendererObj.drawString("Status: ONLINE", guiLeft+8, guiTop+44, 4210752);
		}
		else
		{
			this.fontRendererObj.drawString("Status: OFFLINE", guiLeft+8, guiTop+44, 4210752);
		}
	}
}