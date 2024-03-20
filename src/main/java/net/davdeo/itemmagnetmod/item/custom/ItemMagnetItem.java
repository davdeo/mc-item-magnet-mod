package net.davdeo.itemmagnetmod.item.custom;

import net.davdeo.itemmagnetmod.event.custom.PickupItemEvent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemMagnetItem extends Item {
    private ItemStack stack;
    private PlayerEntity player;

    private boolean isActive;

    public ItemMagnetItem(Settings settings) {
        super(settings);

        this.isActive = false;

        PickupItemEvent.EVENT.register(this::onPickupEvent);
    }

    private ActionResult onPickupEvent(PlayerEntity aPlayer, ItemEntity aEntity) {
        if (aPlayer != this.player || !this.isActive) {
            return ActionResult.PASS;
        }

        int numberOfItems = aEntity.getStack().getCount();
        this.player.sendMessage(Text.literal("Picked up " + numberOfItems + " items"), false);

        this.stack.damage(numberOfItems, this.player,
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand())
        );

        return ActionResult.PASS;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        this.stack = player.getStackInHand(hand);
        this.player = player;

        if(!world.isClient()) {
            this.isActive = !this.isActive;
        }

        return TypedActionResult.success(this.stack, true);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.isActive;
    }
}
