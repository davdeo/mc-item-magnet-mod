package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.component.ModComponents;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {
    private ModItems() {
        super();
    }

    public static final ResourceKey<Item> ITEM_MAGNET_KEY = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(ItemMagnetMod.MOD_ID, "item_magnet"));
    public static final Item ITEM_MAGNET = registerItem(
            new ItemMagnetItem(
                    new Item.Properties()
                            .setId(ITEM_MAGNET_KEY)
                            .durability(1024)
                            .component(ModComponents.ITEM_MAGNET_ITEM_IS_ACTIVE_COMPONENT, false)
            ),
            ITEM_MAGNET_KEY
    );

    public static final ResourceKey<Item> ITEM_MAGNET_BROKEN_KEY = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(ItemMagnetMod.MOD_ID, "item_magnet_broken"));
    public static final Item ITEM_MAGNET_BROKEN = registerItem(
            new Item(
                    new Item.Properties()
                            .setId(ITEM_MAGNET_BROKEN_KEY)
                            .stacksTo(1)
            ),
            ITEM_MAGNET_BROKEN_KEY
    );

    public static final ResourceKey<Item> MAGNET_CORE_KEY = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(ItemMagnetMod.MOD_ID, "magnet_core"));
    public static final Item MAGNET_CORE = registerItem(
            new Item(
                    new Item.Properties()
                            .setId(MAGNET_CORE_KEY)
                            .stacksTo(1)
            ),
            MAGNET_CORE_KEY
    );


    private static Item registerItem(Item item, ResourceKey<Item> registryKey) {
        return Registry.register(BuiltInRegistries.ITEM, registryKey.identifier(), item);
    }

    public static void registerModItems() {
        ItemMagnetMod.LOGGER.info("Registering mod items for: " + ItemMagnetMod.MOD_ID);
    }
}
