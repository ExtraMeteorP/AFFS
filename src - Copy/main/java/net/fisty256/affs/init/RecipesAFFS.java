package net.fisty256.affs.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipesAFFS {
	public static void registerOreDict()
	{
		OreDictionary.registerOre("ingotSteel", ItemsAFFS.ingot_steel);
		OreDictionary.registerOre("ingotColorium", ItemsAFFS.ingot_colorium);
		OreDictionary.registerOre("ingotSteelTriple", ItemsAFFS.ingot_steel_triple);
		OreDictionary.registerOre("ingotIronTriple", ItemsAFFS.ingot_iron_triple);
	}
	
	public static void registerCrafting()
	{
		GameRegistry.addRecipe(new ItemStack(ItemsAFFS.ingot_steel, 2), new Object[]{
			"IC", "CI",
			'I', new ItemStack(Items.iron_ingot), 'C', new ItemStack(Items.coal)
		});
		GameRegistry.addRecipe(new ItemStack(ItemsAFFS.ingot_steel, 2), new Object[]{
			"CI", "IC",
			'I', new ItemStack(Items.iron_ingot), 'C', new ItemStack(Items.coal)
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.iron_casing, true, new Object[]{
			" S ", "S S", " S ",
			'S', "ingotIronTriple"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.steel_casing, true, new Object[]{
			" S ", "S S", " S ",
			'S', "ingotSteelTriple"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAFFS.chip, true, new Object[]{
			"GGG", " D ", " I ",
			'D', new ItemStack(Items.dye, 1, 2), 'G', new ItemStack(Items.gold_nugget, 1), 'I', "ingotSteel"
		}));
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemsAFFS.inventory_cleaner), new Object[]{
			"SCS", "C C", "SCS",
			'S', new ItemStack(Blocks.cobblestone), 'C', new ItemStack(ItemsAFFS.chip)
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAFFS.force_floodlight, true, new Object[]{
			" S ", "SDS", " S ",
			'D', "ingotColorium", 'S', "ingotSteel"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAFFS.force_inductor_component, true, new Object[]{
			"SCS", "RRR", "SCS",
			'S', "ingotSteel", 'R', new ItemStack(Items.redstone), 'C', new ItemStack(ItemsAFFS.chip)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAFFS.wireless_force_component, true, new Object[]{
			"IRI", "CCC", "IRI",
			'I', new ItemStack(Items.iron_ingot), 'R', new ItemStack(Items.redstone), 'C', new ItemStack(ItemsAFFS.chip)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAFFS.advanced_wireless_force_component, true, new Object[]{
			"CGC", "WSW", "CGC",
			'W', new ItemStack(ItemsAFFS.wireless_force_component), 'G', new ItemStack(Items.gold_ingot), 'C', new ItemStack(ItemsAFFS.chip), 'S', "ingotSteelTriple"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.force_generator, true, new Object[]{
			"IWI", "RMR", "IFI",
			'M', BlocksAFFS.steel_casing, 'F', ItemsAFFS.force_inductor_component, 'I', Items.iron_ingot, 'R', Items.redstone, 'W', ItemsAFFS.advanced_wireless_force_component
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.force_charger, true, new Object[]{
			" C ", "RMR", " W ",
			'M', BlocksAFFS.steel_casing, 'R', Items.redstone, 'C', ItemsAFFS.chip, 'W', ItemsAFFS.wireless_force_component
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.configurator, true, new Object[]{
			" C ", "SMS", " C ",
			'M', BlocksAFFS.steel_casing, 'C', ItemsAFFS.chip, 'S', "ingotSteel"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(BlocksAFFS.force_field_projector, true, new Object[]{
			"IFI", "CMC", "CWC",
			'M', BlocksAFFS.iron_casing, 'C', ItemsAFFS.chip, 'W', ItemsAFFS.advanced_wireless_force_component, 'I', "ingotColorium", 'F', ItemsAFFS.force_floodlight
		}));
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsAFFS.link_card), Items.paper, Items.redstone, ItemsAFFS.chip);
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsAFFS.ingot_iron_triple), Items.iron_ingot, Items.iron_ingot, Items.iron_ingot);
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemsAFFS.ingot_steel_triple, "ingotSteel", "ingotSteel", "ingotSteel"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemsAFFS.ingot_colorium, "dye", "dye", "dye", "ingotSteel", "ingotIron"));
	}
	
	public static void registerSmelting()
	{
		
	}
	
	public static void registerCustom()
	{
		
	}
}