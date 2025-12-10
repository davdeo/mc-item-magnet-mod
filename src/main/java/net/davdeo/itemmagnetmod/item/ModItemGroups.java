package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    private ModItemGroups() {
        super();
    }

    public static final CreativeModeTab ITEM_MAGNET_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(ItemMagnetMod.MOD_ID, "item_magnet"),
            FabricItemGroup.builder().title(Component.translatable("itemgroup.item_magnet"))
                    .icon(() -> new ItemStack(ModItems.ITEM_MAGNET)).displayItems((displayContext, entries) -> {
                        entries.accept(ModItems.ITEM_MAGNET);
                        entries.accept(ModItems.ITEM_MAGNET_BROKEN);
                        entries.accept(ModItems.MAGNET_CORE);
                    }).build());

    public static void registerItemGroups() {
        ItemMagnetMod.LOGGER.info("Registering item groups for: " + ItemMagnetMod.MOD_ID);
    }
}
