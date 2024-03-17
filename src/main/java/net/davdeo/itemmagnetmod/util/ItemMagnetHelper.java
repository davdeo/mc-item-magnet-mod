package net.davdeo.itemmagnetmod.util;

import net.davdeo.itemmagnetmod.item.ModItems;
import net.davdeo.itemmagnetmod.item.custom.ItemMagnetItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMagnetHelper {
    public static final PlayerEntity getClosestPlayerWithActiveMagnet(World world, Entity target) {
        List<? extends  PlayerEntity> playersWithActiveMagnet = world.getPlayers().stream().filter(playerEntity -> {
            int magnetInventoryPosition = InventoryUtil.getFirstInventoryIndex(playerEntity, ModItems.ITEM_MAGNET);
            if (magnetInventoryPosition != -1) {
                return ((ItemMagnetItem) playerEntity.getInventory().getStack(magnetInventoryPosition).getItem()).getIsActive();
            }

            return false;
        }).collect(Collectors.toList());

        int playerIndex  = WorldUtil.getClosestEntity(playersWithActiveMagnet, target);

        if (playerIndex != -1) {
            return playersWithActiveMagnet.get(playerIndex);
        } else {
           return null;
        }
    }
}
