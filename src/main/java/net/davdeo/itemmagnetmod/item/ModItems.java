package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.component.ModComponents;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    private ModItems() {
        super();
    }

    public static final Item ITEM_MAGNET = registerItem("item_magnet",
            new ItemMagnetItem(
                    new Item.Settings()
                            .maxDamage(1024)
                            .component(ModComponents.ITEM_MAGNET_ITEM_IS_ACTIVE_COMPONENT, false)
            )
    );

    public static final Item ITEM_MAGNET_BROKEN = registerItem("item_magnet_broken",
            new Item(new Item.Settings().maxCount(1)));
    public static final Item MAGNET_CORE = registerItem("magnet_core",
            new Item(new Item.Settings().maxCount(1)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ItemMagnetMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemMagnetMod.LOGGER.info("Registering mod items for: " + ItemMagnetMod.MOD_ID);
    }
}
