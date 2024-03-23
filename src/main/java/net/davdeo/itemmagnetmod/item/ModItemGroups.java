package net.davdeo.itemmagnetmod.item;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class ModItemGroups {
    private ModItemGroups() {
        super();
    }

    public static final ItemGroup ITEM_MAGNET_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ItemMagnetMod.MOD_ID, "item_magnet"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.item_magnet"))
                    .icon(() -> new ItemStack(ModItems.ITEM_MAGNET)).entries((displayContext, entries) -> {
                        entries.add(ModItems.ITEM_MAGNET);
                        entries.add(ModItems.ITEM_MAGNET_BROKEN);
                        entries.add(ModItems.MAGNET_CORE);
                    }).build());

    public static void registerItemGroups() {
        ItemMagnetMod.LOGGER.info("Registering item groups for: " + ItemMagnetMod.MOD_ID);
    }
}
