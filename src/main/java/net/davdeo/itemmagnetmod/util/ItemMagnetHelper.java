package net.davdeo.itemmagnetmod.util;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.nbt.NbtKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemMagnetHelper {
    private ItemMagnetHelper() {
        super();
    }

    public static final int getFirstActiveMagnetInventoryIndex(PlayerEntity player) {
        List<Integer> indices = InventoryUtil.getInventoryIndices(player, ModItems.ITEM_MAGNET);

        for (int inventoryIndex : indices) {
            ItemStack stack =  player.getInventory().getStack(inventoryIndex);

            if (ItemMagnetHelper.getIsActive(stack)) {
                return inventoryIndex;
            }
        }

        return -1;
    }

    public static final PlayerEntity getClosestPlayerWithActiveMagnet(World world, Entity target) {
        List<? extends  PlayerEntity> playersWithActiveMagnet = world.getPlayers().stream().filter(playerEntity -> {
            int magnetInventoryPosition = ItemMagnetHelper.getFirstActiveMagnetInventoryIndex(playerEntity);

            if (magnetInventoryPosition != -1) {
                ItemStack stack = playerEntity.getInventory().getStack(magnetInventoryPosition);

                return ItemMagnetHelper.getIsActive(stack);
            }

            return false;
        }).toList();

        int playerIndex  = WorldUtil.getClosestEntity(playersWithActiveMagnet, target);

        if (playerIndex != -1) {
            return playersWithActiveMagnet.get(playerIndex);
        } else {
           return null;
        }
    }


    public static final void toggleIsActive(ItemStack stack) {
        NbtCompound nbtData = stack.getOrCreateNbt();

        boolean isActive = false;
        if (nbtData.contains(NbtKeys.ITEM_MAGNET_ITEM_IS_ACTIVE)) {
            isActive = nbtData.getBoolean(NbtKeys.ITEM_MAGNET_ITEM_IS_ACTIVE);
        }

        nbtData.putBoolean(NbtKeys.ITEM_MAGNET_ITEM_IS_ACTIVE, !isActive);

        stack.setNbt(nbtData);
    }

    public static final boolean getIsActive(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(NbtKeys.ITEM_MAGNET_ITEM_IS_ACTIVE)) {
            return stack.getNbt().getBoolean(NbtKeys.ITEM_MAGNET_ITEM_IS_ACTIVE);
        }

        return false;
    }
}
