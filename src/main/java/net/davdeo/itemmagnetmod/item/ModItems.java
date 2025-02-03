package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.component.ModComponents;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
    private ModItems() {
        super();
    }

    public static final RegistryKey<Item> ITEM_MAGNET_KEY = RegistryKey.of(
            RegistryKeys.ITEM,
            Identifier.of(ItemMagnetMod.MOD_ID, "item_magnet"));
    public static final Item ITEM_MAGNET = registerItem(
            new ItemMagnetItem(
                    new Item.Settings()
                            .registryKey(ITEM_MAGNET_KEY)
                            .maxDamage(1024)
                            .component(ModComponents.ITEM_MAGNET_ITEM_IS_ACTIVE_COMPONENT, false)
            ),
            ITEM_MAGNET_KEY
    );

    public static final RegistryKey<Item> ITEM_MAGNET_BROKEN_KEY = RegistryKey.of(
            RegistryKeys.ITEM,
            Identifier.of(ItemMagnetMod.MOD_ID, "item_magnet_broken"));
    public static final Item ITEM_MAGNET_BROKEN = registerItem(
            new Item(new Item.Settings()
                    .registryKey(ITEM_MAGNET_BROKEN_KEY)
                    .maxCount(1)),
            ITEM_MAGNET_BROKEN_KEY);

    public static final RegistryKey<Item> MAGNET_CORE_KEY = RegistryKey.of(
            RegistryKeys.ITEM,
            Identifier.of(ItemMagnetMod.MOD_ID, "magnet_core"));
    public static final Item MAGNET_CORE = registerItem(
            new Item(new Item.Settings()
                    .registryKey(MAGNET_CORE_KEY)
                    .maxCount(1)),
            MAGNET_CORE_KEY);


    private static Item registerItem(Item item, RegistryKey<Item> registryKey) {
        return Registry.register(Registries.ITEM, registryKey.getValue(), item);
    }

    public static void registerModItems() {
        ItemMagnetMod.LOGGER.info("Registering mod items for: " + ItemMagnetMod.MOD_ID);
    }
}
