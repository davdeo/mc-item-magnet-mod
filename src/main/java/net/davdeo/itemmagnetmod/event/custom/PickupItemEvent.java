package net.davdeo.itemmagnetmod.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PickupItemEvent {
    Event<PickupItemEvent> EVENT = EventFactory.createArrayBacked(PickupItemEvent.class,
            listeners -> (player, entity) -> {
                for(PickupItemEvent listener : listeners) {
                    ActionResult result = listener.onPickup(player, entity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult onPickup(PlayerEntity player, ItemEntity entity);
}
