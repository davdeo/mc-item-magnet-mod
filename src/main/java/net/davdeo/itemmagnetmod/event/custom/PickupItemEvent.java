package net.davdeo.itemmagnetmod.event.custom;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        ItemMagnetMod.LOGGER.debug("On pickup event");
        int activeMagnetInventoryIndex = ItemMagnetHelper.getFirstActiveMagnetInventoryIndex(player);

        if (activeMagnetInventoryIndex == -1) {
            return ActionResult.PASS;
        }

        ItemStack activeMagnet = player.getInventory().getStack(activeMagnetInventoryIndex);

        if (player.getAbilities().creativeMode) {
            return ActionResult.PASS;
        }

        ServerPlayerEntity serverPlayer = null;
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayer = serverPlayerEntity;
        }

        int newDamage = activeMagnet.getDamage() + pickedUpItemsCount;

        if (serverPlayer != null && pickedUpItemsCount != 0) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(serverPlayer, activeMagnet, newDamage);
        }

        activeMagnet.setDamage(newDamage);

        if (newDamage >= activeMagnet.getMaxDamage()) {
            activeMagnet.decrement(1);

            player.getInventory().setStack(activeMagnetInventoryIndex, new ItemStack(ModItems.ITEM_MAGNET_BROKEN));
            if (serverPlayer != null && !serverPlayer.getWorld().isClient) {
                serverPlayer.getWorld().playSound(
                        null,
                        serverPlayer.getBlockPos(),
                        SoundEvents.ENTITY_ITEM_BREAK.value(),
                        SoundCategory.PLAYERS,
                        1f, 1f
                    );
            }
        }

        return ActionResult.PASS;
    }
}
