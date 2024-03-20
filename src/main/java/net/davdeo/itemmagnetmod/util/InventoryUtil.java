package net.davdeo.itemmagnetmod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {
    private InventoryUtil() {
        super();
    }

    public static final boolean hasPlayerStackInInventory(PlayerEntity player, Item item) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack currentStack = player.getInventory().getStack(i);
            if(!currentStack.isEmpty() && currentStack.isOf(item)) {
                return true;
            }
        }

        return false;
    }

    public static final int getFirstInventoryIndex(PlayerEntity player, Item item) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack currentStack = player.getInventory().getStack(i);
            if (!currentStack.isEmpty() && currentStack.isOf(item)) {
                return i;
            }
        }

        return -1;
    }

    public static final List<Integer> getInventoryIndices(PlayerEntity player, Item item) {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack currentStack = player.getInventory().getStack(i);
            if (!currentStack.isEmpty() && currentStack.isOf(item)) {
                indices.add(i);
            }
        }

        return indices;
    }
}
