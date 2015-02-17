package net.fisty256.affs.init;

import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;

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
	
	public static void init()
	{
		link_card = new ItemLinkCard().setUnlocalizedName("link_card");
		inventory_cleaner = new ItemInventoryCleaner().setUnlocalizedName("inventory_cleaner");
	}
	
	public static void register()
	{
		GameRegistry.registerItem(link_card, link_card.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(inventory_cleaner, inventory_cleaner.getUnlocalizedName().substring(5));
	}
	
	public static void registerTextures()
	{
		registerTexture(link_card);
		registerTexture(inventory_cleaner);
	}
	
	private static void registerTexture(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(ModReferences.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}