package net.davdeo.itemmagnetmod;

import net.davdeo.itemmagnetmod.datagen.ModLootTableModifier;
import net.davdeo.itemmagnetmod.event.ModEvents;
import net.davdeo.itemmagnetmod.item.ModItemGroups;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * TODO BUG - Fix "jumping" of affected items after disable of magnet
 * 		-> could be connected to merging stacks???
 *
 * V1.0
 * TODO - Reduce probability in end_city
 * TODO - Reduce probability in library (0.1?)
 * TODO - publish on
 *  	- Forge
 * 		- Modrinth
 *
 *
 * V2.0
 * TODO - Add different magnet versions -> smithing with template?
 *  	- Hardened magnet -> more durability (maybe enchant with unbreaking?)
 * 		- Crafter magnet -> auto crafts recipe upon collecting necessary res
* 		- Container magnet  -> has a container (shulker box) attached and moves items into it
 * 		- Ranged magnet -> More range
 * 		- Ender magnet -> Directly inserts items into the inventory
 *
 * TODO - Reduce distance and durability if there are upgraded versions.
 */

public class ItemMagnetMod implements ModInitializer {
	public static final String MOD_ID = "itemmagnetmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();

		ModEvents.registerModEvents();

		ModLootTableModifier.modifyLootTables();
	}
}
