package net.davdeo.itemmagnetmod;

import net.davdeo.itemmagnetmod.item.ModItemGroups;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMagnetMod implements ModInitializer {
	public static final String MOD_ID = "itemmagnetmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
	}
}