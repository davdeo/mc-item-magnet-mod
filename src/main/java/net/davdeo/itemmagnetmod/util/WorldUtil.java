package net.davdeo.itemmagnetmod.util;

import net.minecraft.entity.Entity;

import java.util.List;

public class WorldUtil {
    public static final int getClosestEntity(List<? extends Entity> entities, Entity target) {
        int closestIndex = -1;
        float closestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < entities.size(); i++) {
            float currentDistance = entities.get(i).distanceTo(target);

            if (currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestIndex = i;
            }
        }

        return closestIndex;
    }
}
