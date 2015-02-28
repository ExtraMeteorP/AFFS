package net.fisty256.affs.init;

import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;

import net.fisty256.affs.AFFS;
import net.fisty256.affs.item.ItemInventoryCleaner;
import net.fisty256.affs.item.ItemLinkCard;
import net.fisty256.affs.reference.ModReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemsAFFS {
	
	public static Item link_card;
	public static Item inventory_cleaner;
	
	/* Crafting ings */
	public static Item chip, force_floodlight, force_inductor_component, ingot_colorium, ingot_steel, ingot_iron_triple, ingot_steel_triple, wireless_force_component, advanced_wireless_force_component;
	
	public static void init()
	{
		link_card = new ItemLinkCard().setUnlocalizedName("link_card").setCreativeTab(AFFS.tab_affs);
		inventory_cleaner = new ItemInventoryCleaner().setUnlocalizedName("inventory_cleaner").setCreativeTab(AFFS.tab_affs);
		
		chip = new Item().setUnlocalizedName("chip").setCreativeTab(AFFS.tab_affs);
		force_floodlight = new Item().setUnlocalizedName("force_floodlight").setCreativeTab(AFFS.tab_affs);
		force_inductor_component = new Item().setUnlocalizedName("force_inductor_component").setCreativeTab(AFFS.tab_affs);
		ingot_colorium = new Item().setUnlocalizedName("ingot_colorium").setCreativeTab(AFFS.tab_affs);
		ingot_steel = new Item().setUnlocalizedName("ingot_steel").setCreativeTab(AFFS.tab_affs);
		ingot_iron_triple = new Item().setUnlocalizedName("ingot_iron_triple").setCreativeTab(AFFS.tab_affs);
		ingot_steel_triple = new Item().setUnlocalizedName("ingot_steel_triple").setCreativeTab(AFFS.tab_affs);
		wireless_force_component = new Item().setUnlocalizedName("wireless_force_component").setCreativeTab(AFFS.tab_affs);
		advanced_wireless_force_component = new Item().setUnlocalizedName("advanced_wireless_force_component").setCreativeTab(AFFS.tab_affs);
	}
	
	public static void register()
	{
		GameRegistry.registerItem(link_card, link_card.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(inventory_cleaner, inventory_cleaner.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(chip, chip.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(force_floodlight, force_floodlight.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(force_inductor_component, force_inductor_component.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ingot_colorium, ingot_colorium.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ingot_steel, ingot_steel.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ingot_iron_triple, ingot_iron_triple.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ingot_steel_triple, ingot_steel_triple.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(wireless_force_component, wireless_force_component.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(advanced_wireless_force_component, advanced_wireless_force_component.getUnlocalizedName().substring(5));
	}
	
	public static void registerTextures()
	{
		registerTexture(link_card);
		registerTexture(inventory_cleaner);
		registerTexture(chip);
		registerTexture(force_floodlight);
		registerTexture(force_inductor_component);
		registerTexture(ingot_colorium);
		registerTexture(ingot_steel);
		registerTexture(ingot_iron_triple);
		registerTexture(ingot_steel_triple);
		registerTexture(wireless_force_component);
		registerTexture(advanced_wireless_force_component);
	}
	
	private static void registerTexture(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModReferences.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}