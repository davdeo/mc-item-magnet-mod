package net.davdeo.itemmagnetmod;

import net.davdeo.itemmagnetmod.component.ModComponents;
import net.davdeo.itemmagnetmod.datagen.ModLootTableModifier;
import net.davdeo.itemmagnetmod.event.ModEvents;
import net.davdeo.itemmagnetmod.item.ModItemGroups;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMagnetMod implements ModInitializer {
	public static final String MOD_ID = "itemmagnetmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModComponents.registerComponents();

		ModEvents.registerModEvents();

		ModLootTableModifier.modifyLootTables();
	}
}
