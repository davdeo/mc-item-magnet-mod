package net.davdeo.itemmagnetmod.event;

import net.davdeo.itemmagnetmod.ItemMagnetMod;

public class ModEvents {
    private ModEvents() {
        super();
    }

    public static void registerModEvents() {
        ItemMagnetMod.LOGGER.info("Registering mod events for: " + ItemMagnetMod.MOD_ID);

    }
}
