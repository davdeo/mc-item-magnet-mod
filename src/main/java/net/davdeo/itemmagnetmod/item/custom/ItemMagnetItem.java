package net.davdeo.itemmagnetmod.item.custom;

import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;


public class ItemMagnetItem extends Item {
    public ItemMagnetItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ConsumableComponent consumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);

        if(!world.isClient()) {
            ItemMagnetHelper.toggleIsActive(itemStack);
        }

        if (consumableComponent != null) {
            return consumableComponent.consume(player, itemStack, hand);
        } else {
            EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
            return equippableComponent != null && equippableComponent.swappable() ? equippableComponent.equip(itemStack, player) : ActionResult.PASS;
        }
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
