package net.davdeo.itemmagnetmod.event.custom;

import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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

    static ActionResult onPickupEvent(PlayerEntity player, ItemEntity pickedUpEntity) {
        int activeMagnetInventoryIndex = ItemMagnetHelper.getFirstActiveMagnetInventoryIndex(player);

        if (activeMagnetInventoryIndex == -1) {
            return ActionResult.PASS;
        }

        ItemStack activeMagnet = player.getInventory().getStack(activeMagnetInventoryIndex);
        ItemStack pickedUpStack = pickedUpEntity.getStack();


        int numberOfItems = pickedUpStack.getCount();
        player.sendMessage(Text.literal("Picked up " + numberOfItems + " items"), false);

        activeMagnet.damage(numberOfItems, player,
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand())
        );

        return ActionResult.PASS;
    }
}
