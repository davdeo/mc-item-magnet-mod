package net.davdeo.itemmagnetmod.event;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.event.custom.PickupItemEvent;

public class ModEvents {
    private ModEvents() {
        super();
    }

    public static void registerModEvents() {
        ItemMagnetMod.LOGGER.info("Registering mod events for: " + ItemMagnetMod.MOD_ID);

        PickupItemEvent.EVENT.register(PickupItemEvent::onPickupEvent);
    }
}
