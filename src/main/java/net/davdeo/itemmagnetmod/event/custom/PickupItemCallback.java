package net.davdeo.itemmagnetmod.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PickupItemCallback {
    Event<PickupItemCallback> EVENT = EventFactory.createArrayBacked(PickupItemCallback.class,
            (listeners) -> (player, entity) -> {
                for(PickupItemCallback listener : listeners) {
                    ActionResult result = listener.pickup(player, entity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult pickup(PlayerEntity player, ItemEntity entity);
}
