package net.davdeo.itemmagnetmod;

import net.davdeo.itemmagnetmod.item.ModItemGroups;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * BUG - Glint / isActive state is set on Item level instead of ItemStack level -> leads to all magnet having the same state
 * 	   - could be solved by using nbt data on stack instead of isActive on Item instance
 * BUG - creating a new magnet has isActive flag set to true
 * 	   - could be solved by solving the other BUG
 * BUG - Fix magnet inactive after rejoin of world
 * 	   - could be solved by solving the other BUG
 * BUG - Fix "jumping" of affected items after disable of magnet
 *
 * TODO - Add recipe to craft magnet
 *  	- Add magnet core
 * 		- Edit loot-tables for magnet core to spawn in chests
 * TODO - Prevent magnet from breaking -> convert it to broken magnet upon reaching 1 durability
 * TODO - Add different magnet versions
 *  	- Hardened magnet -> more durability (maybe enchant with unbreaking?)
 * 		- Crafter magnet -> auto crafts recipe upon collecting necessary res
* 		- Container magnet  -> has a container (shulker box) attached and moves items into it
 * 		- Ranged magnet -> More range
 * 		- Ender magnet -> Directly inserts items into the inventory
 *
 */

public class ItemMagnetMod implements ModInitializer {
	public static final String MOD_ID = "itemmagnetmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
	}
}