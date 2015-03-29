package net.fisty256.affs.init;

import net.fisty256.affs.AFFS;
import net.fisty256.affs.block.BlockConfigurator;
import net.fisty256.affs.block.BlockForceCharger;
import net.fisty256.affs.block.BlockForceCombiner;
import net.fisty256.affs.block.BlockForceField;
import net.fisty256.affs.block.BlockForceFieldProjector;
import net.fisty256.affs.block.BlockForceGenerator;
import net.fisty256.affs.block.BlockIron;
import net.fisty256.affs.block.BlockSteel;
import net.fisty256.affs.reference.ModReferences;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksAFFS {
	
	public static Block force_generator;
	public static Block force_field;
	public static Block force_field_projector;
	public static Block force_charger;
	public static Block configurator;
	public static Block force_combiner;
	
	public static Block iron_casing, steel_casing;
	
	public static void init()
	{
		force_generator = new BlockForceGenerator(Material.rock).setUnlocalizedName("force_generator").setCreativeTab(AFFS.tab_affs);
		force_field = new BlockForceField(Material.rock).setUnlocalizedName("force_field").setCreativeTab(AFFS.tab_affs);
		force_field_projector = new BlockForceFieldProjector(Material.rock).setUnlocalizedName("force_field_projector").setCreativeTab(AFFS.tab_affs);
		force_charger = new BlockForceCharger(Material.rock).setUnlocalizedName("force_charger").setCreativeTab(AFFS.tab_affs);
		configurator = new BlockConfigurator(Material.rock).setUnlocalizedName("configurator").setCreativeTab(AFFS.tab_affs);
		iron_casing = new BlockIron().setUnlocalizedName("iron_casing").setCreativeTab(AFFS.tab_affs);
		steel_casing = new BlockSteel().setUnlocalizedName("steel_casing").setCreativeTab(AFFS.tab_affs);
		force_combiner = new BlockForceCombiner(Material.rock).setUnlocalizedName("force_combiner").setCreativeTab(AFFS.tab_affs);
	}
	
	public static void register()
	{
		GameRegistry.registerBlock(force_generator, force_generator.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(force_field, force_field.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(force_field_projector, force_field_projector.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(force_charger, force_charger.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(configurator, configurator.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(iron_casing, iron_casing.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(steel_casing, steel_casing.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(force_combiner, force_combiner.getUnlocalizedName().substring(5));
	}
	
	public static void registerTextures()
	{
		registerTexture(force_generator);
		registerTexture(force_field);
		registerTexture(force_field_projector);
		registerTexture(force_charger);
		registerTexture(configurator);
		registerTexture(iron_casing);
		registerTexture(steel_casing);
		registerTexture(force_combiner);
	}
	
	private static void registerTexture(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModReferences.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}