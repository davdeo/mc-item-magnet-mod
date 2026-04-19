package net.davdeo.itemmagnetmod.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    private InventoryUtil() {
        super();
    }

    public static final boolean hasPlayerStackInInventory(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if(!currentStack.isEmpty() && currentStack.is(item)) {
                return true;
            }
        }

        return false;
    }

    public static final int getFirstInventoryIndex(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.is(item)) {
                return i;
            }
        }

        return -1;
    }

    public static final List<Integer> getInventoryIndices(Player player, Item item) {
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (!currentStack.isEmpty() && currentStack.is(item)) {
                indices.add(i);
            }
        }

        return indices;
    }
}
