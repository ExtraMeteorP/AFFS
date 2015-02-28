package net.fisty256.affs.gui;

import com.sun.media.jfxmedia.logging.Logger;

import net.fisty256.affs.container.ContainerForceGenerator;
import net.fisty256.affs.network.PacketHandler;
import net.fisty256.affs.network.message.MessageForceGeneratorButton;
import net.fisty256.affs.reference.ModReferences;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiForceGenerator extends InventoryEffectRenderer {

	private final ResourceLocation backgroundTexture;
	
	private TileEntityForceGenerator te;
	
	private EntityPlayer player;
	
	public GuiForceGenerator(Container container, TileEntityForceGenerator te, EntityPlayer player)
	{
		super(container);
		
		this.te = te;
		
		this.player = player;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_generator.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166); //Draw background
		
		this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+65, 4210752);
		
		this.fontRendererObj.drawString("Force Stored: " + te.forceStored, guiLeft+40, guiTop+23, 4210752);
		this.fontRendererObj.drawString("Force Pt: " + te.forcePt, guiLeft+40, guiTop+40, 4210752);
		this.fontRendererObj.drawString(""+Math.round(te.burnTime/20), guiLeft+14, guiTop+73, 4210752);
		this.fontRendererObj.drawString(""+Math.round(te.upgradeBurnTime/20), guiLeft+14, guiTop+7, 4210752);
		
		GuiButton guiButton = (GuiButton)this.buttonList.get(0);
		if (te.turnedOn)
		{
			guiButton.displayString = "Stop";
		}
		else
		{
			guiButton.displayString = "Start";
		}
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(new GuiButton(0, guiLeft+35, guiTop+60, 60, 20, ""));
	}
	
	protected void actionPerformed(GuiButton guibutton)
	{
        PacketHandler.INSTANCE.sendToServer(new MessageForceGeneratorButton(te.getPos(), player.dimension, guibutton.id));
	}
}