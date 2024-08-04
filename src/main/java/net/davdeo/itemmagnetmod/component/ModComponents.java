package net.davdeo.itemmagnetmod.component;

import com.mojang.serialization.Codec;
import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.davdeo.itemmagnetmod.component.ModComponentKeys.ITEM_MAGNET_ITEM_IS_ACTIVE;

public class ModComponents {
    private ModComponents() {
        super();
    }

    public static final ComponentType<Boolean> ITEM_MAGNET_ITEM_IS_ACTIVE_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ItemMagnetMod.MOD_ID, ITEM_MAGNET_ITEM_IS_ACTIVE),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void registerComponents() {
        ItemMagnetMod.LOGGER.info("Registering mod components for: " + ItemMagnetMod.MOD_ID);
    }
}
