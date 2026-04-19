package net.davdeo.itemmagnetmod.component;

import com.mojang.serialization.Codec;
import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import static net.davdeo.itemmagnetmod.component.ModComponentKeys.ITEM_MAGNET_ITEM_IS_ACTIVE;

public class ModComponents {
    private ModComponents() {
        super();
    }

    public static final DataComponentType<Boolean> ITEM_MAGNET_ITEM_IS_ACTIVE_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(ItemMagnetMod.MOD_ID, ITEM_MAGNET_ITEM_IS_ACTIVE),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );

    public static void registerComponents() {
        ItemMagnetMod.LOGGER.info("Registering mod components for: " + ItemMagnetMod.MOD_ID);
    }
}
