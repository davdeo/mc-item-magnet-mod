package net.davdeo.itemmagnetmod.item.custom;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemMagnetItem extends Item {
    public boolean getIsActive() {
        return isActive;
    }

    private boolean isActive;

    public ItemMagnetItem(Settings settings) {
        super(settings);

        this.isActive = false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!world.isClient()) {
            ItemMagnetMod.LOGGER.info("Usage");

            if (!this.isActive) {
                ItemMagnetMod.LOGGER.info("Now Active");

                this.isActive = true;

                itemStack.damage(1, player,
                        playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand())
                );

            } else {
                ItemMagnetMod.LOGGER.info("Now Inactive");
                this.isActive = false;
            }
        }

        return TypedActionResult.success(itemStack, true);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.isActive;
    }
}
