package net.fisty256.affs.init;

import net.fisty256.affs.block.BlockForceGenerator;
import net.fisty256.affs.reference.ModReferences;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAFFS {
	
	public static Block force_generator;
	
	public static void init()
	{
		force_generator = new BlockForceGenerator(Material.rock).setUnlocalizedName("force_generator");
	}
	
	public static void register()
	{
		GameRegistry.registerBlock(force_generator, force_generator.getUnlocalizedName().substring(5));
	}
	
	public static void registerTextures()
	{
		registerTexture(force_generator);
	}
	
	private static void registerTexture(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModReferences.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}