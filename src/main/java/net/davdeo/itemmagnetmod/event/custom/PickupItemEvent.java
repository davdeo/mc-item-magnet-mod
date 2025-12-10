package net.davdeo.itemmagnetmod.event.custom;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.davdeo.itemmagnetmod.config.ModConfig;

public interface PickupItemEvent {
    Event<PickupItemEvent> EVENT = EventFactory.createArrayBacked(PickupItemEvent.class,
            listeners -> (player, count) -> {
                for(PickupItemEvent listener : listeners) {
                    InteractionResult result = listener.onPickup(player, count);

                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }

                return InteractionResult.PASS;
            });

    InteractionResult onPickup(Player player, int count);

    static InteractionResult onPickupEvent(Player player, int pickedUpItemsCount) {
        ItemMagnetMod.LOGGER.debug("On pickup event");

        if (ModConfig.getInstance().isIndestructible) {
            return InteractionResult.PASS;
        }

        int activeMagnetInventoryIndex = ItemMagnetHelper.getFirstActiveMagnetInventoryIndex(player);

        if (activeMagnetInventoryIndex == -1) {
            return InteractionResult.PASS;
        }

        ItemStack activeMagnet = player.getInventory().getItem(activeMagnetInventoryIndex);

        // Игрок в креативе уже защищен
        if (player.getAbilities().instabuild) {
            return InteractionResult.PASS;
        }

        ServerPlayer serverPlayer = null;
        if (player instanceof ServerPlayer serverPlayerEntity) {
            serverPlayer = serverPlayerEntity;
        }

        int damageToApply = pickedUpItemsCount;
        int newDamage = activeMagnet.getDamageValue() + damageToApply;


        if (serverPlayer != null && damageToApply != 0) {
            CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(serverPlayer, activeMagnet, newDamage);
        }

        activeMagnet.setDamageValue(newDamage);

        if (newDamage >= activeMagnet.getMaxDamage()) {
            activeMagnet.shrink(1);

            player.getInventory().setItem(activeMagnetInventoryIndex, new ItemStack(ModItems.ITEM_MAGNET_BROKEN));
            if (serverPlayer != null && !serverPlayer.level().isClientSide()) {
                serverPlayer.level().playSound(
                        null,
                        serverPlayer.blockPosition(),
                        SoundEvents.ITEM_BREAK.value(),
                        SoundSource.PLAYERS,
                        1f, 1f
                );
            }
        }

        return InteractionResult.PASS;
    }
}
