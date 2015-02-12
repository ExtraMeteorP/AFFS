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
		
		this.xSize = 176;
		this.ySize = 223;
		
		this.backgroundTexture = new ResourceLocation(ModReferences.MODID + ":textures/gui/force_field_projector.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(backgroundTexture);
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 222); //Draw background
		
		this.fontRendererObj.drawString("Link Card", guiLeft+100, guiTop+125, 4210752);
		
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
		this.fontRendererObj.drawString("Y: " + te.forcefieldY, guiLeft+13, guiTop+51, 4210752);
		this.fontRendererObj.drawString("Z: " + te.forcefieldZ, guiLeft+13, guiTop+61, 4210752);
		
		this.fontRendererObj.drawString("Size:", guiLeft+70, guiTop+30, 4210752);
		this.fontRendererObj.drawString("N: " + te.n, guiLeft+76, guiTop+41, 4210752);
		this.fontRendererObj.drawString("S: " + te.s, guiLeft+76, guiTop+51, 4210752);
		this.fontRendererObj.drawString("W: " + te.w, guiLeft+76, guiTop+61, 4210752);
		this.fontRendererObj.drawString("E: " + te.e, guiLeft+76, guiTop+71, 4210752);
		this.fontRendererObj.drawString("U: " + te.u, guiLeft+76, guiTop+81, 4210752);
		this.fontRendererObj.drawString("D: " + te.d, guiLeft+76, guiTop+91, 4210752);
	}
	
	@Override
	public void initGui()
	{
		this.xSize = 176;
		this.ySize = 223;
		
		super.initGui();
		
		this.buttonList.add(new GuiButton(0, guiLeft+35, guiTop+6, 60, 20, "Cube"));
		
		this.buttonList.add(new GuiButton(1, guiLeft+45, guiTop+39, 10, 10, "+"));		this.buttonList.add(new GuiButton(2, guiLeft+57, guiTop+39, 10, 10, "-")); //X
		this.buttonList.add(new GuiButton(3, guiLeft+45, guiTop+49, 10, 10, "+"));		this.buttonList.add(new GuiButton(4, guiLeft+57, guiTop+49, 10, 10, "-")); //Y
		this.buttonList.add(new GuiButton(5, guiLeft+45, guiTop+59, 10, 10, "+"));		this.buttonList.add(new GuiButton(6, guiLeft+57, guiTop+59, 10, 10, "-")); //Z
		
		this.buttonList.add(new GuiButton(7, guiLeft+108, guiTop+41, 10, 10, "+"));		this.buttonList.add(new GuiButton(8, guiLeft+120, guiTop+41, 10, 10, "-")); //North
		this.buttonList.add(new GuiButton(9, guiLeft+108, guiTop+51, 10, 10, "+"));		this.buttonList.add(new GuiButton(10, guiLeft+120, guiTop+51, 10, 10, "-")); //South
		this.buttonList.add(new GuiButton(11, guiLeft+108, guiTop+61, 10, 10, "+"));		this.buttonList.add(new GuiButton(12, guiLeft+120, guiTop+61, 10, 10, "-")); //West
		this.buttonList.add(new GuiButton(13, guiLeft+108, guiTop+71, 10, 10, "+"));		this.buttonList.add(new GuiButton(14, guiLeft+120, guiTop+71, 10, 10, "-")); //East
		this.buttonList.add(new GuiButton(15, guiLeft+108, guiTop+81, 10, 10, "+"));	this.buttonList.add(new GuiButton(16, guiLeft+120, guiTop+81, 10, 10, "-")); //Up
		this.buttonList.add(new GuiButton(17, guiLeft+108, guiTop+91, 10, 10, "+"));	this.buttonList.add(new GuiButton(18, guiLeft+120, guiTop+91, 10, 10, "-")); //Down
	}
	
	protected void actionPerformed(GuiButton guibutton)
	{
        PacketHandler.INSTANCE.sendToServer(new MessageForceFieldProjectorButton(te.getPos(), player.dimension, guibutton.id));
	}
}