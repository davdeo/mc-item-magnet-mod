package net.davdeo.itemmagnetmod.event.custom;

import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public interface PickupItemEvent {
    Event<PickupItemEvent> EVENT = EventFactory.createArrayBacked(PickupItemEvent.class,
            listeners -> (player, count) -> {
                for(PickupItemEvent listener : listeners) {
                    ActionResult result = listener.onPickup(player, count);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult onPickup(PlayerEntity player, int count);

    static ActionResult onPickupEvent(PlayerEntity player, int pickedUpItemsCount) {
        int activeMagnetInventoryIndex = ItemMagnetHelper.getFirstActiveMagnetInventoryIndex(player);

        if (activeMagnetInventoryIndex == -1) {
            return ActionResult.PASS;
        }

        ItemStack activeMagnet = player.getInventory().getStack(activeMagnetInventoryIndex);

        player.sendMessage(Text.literal("Picked up " + pickedUpItemsCount + " items"), false);

        boolean replaceWithBrokenMagnet = activeMagnet.getMaxDamage() - activeMagnet.getDamage() - pickedUpItemsCount <= 0;

        activeMagnet.damage(pickedUpItemsCount, player,
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand())
        );

        if (replaceWithBrokenMagnet) {
            player.getInventory().setStack(activeMagnetInventoryIndex, new ItemStack(ModItems.ITEM_MAGNET_BROKEN));
        }

        return ActionResult.PASS;
    }
}
