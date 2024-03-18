package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item ITEM_MAGNET = registerItem("item_magnet",
            new ItemMagnetItem(new FabricItemSettings().maxDamage(1024)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ItemMagnetMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemMagnetMod.LOGGER.info("Registering mod items for: " + ItemMagnetMod.MOD_ID);
    }
}
