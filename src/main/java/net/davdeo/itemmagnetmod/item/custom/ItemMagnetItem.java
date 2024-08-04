package net.davdeo.itemmagnetmod.item.custom;

import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;


public class ItemMagnetItem extends Item {
    public ItemMagnetItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if(!world.isClient()) {
            ItemMagnetHelper.toggleIsActive(stack);
        }

        return TypedActionResult.success(stack, true);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return ItemMagnetHelper.getIsActive(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        boolean isActive = ItemMagnetHelper.getIsActive(stack);

        if (isActive) {
            tooltip.add(Text.translatable("item.itemmagnetmod.item_magnet.active").formatted(Formatting.GOLD));
        } else {
            tooltip.add(Text.translatable("item.itemmagnetmod.item_magnet.not_active"));
        }
    }
}
