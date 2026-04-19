package net.davdeo.itemmagnetmod.item.custom;

import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import java.util.function.Consumer;


public class ItemMagnetItem extends Item {
    public ItemMagnetItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Consumable consumableComponent = itemStack.get(DataComponents.CONSUMABLE);

        if(!world.isClientSide()) {
            ItemMagnetHelper.toggleIsActive(itemStack);
        }

        if (consumableComponent != null) {
            return consumableComponent.startConsuming(player, itemStack, hand);
        } else {
            Equippable equippableComponent = itemStack.get(DataComponents.EQUIPPABLE);
            return equippableComponent != null && equippableComponent.swappable() ? equippableComponent.swapWithEquipmentSlot(itemStack, player) : InteractionResult.PASS;
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ItemMagnetHelper.getIsActive(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        boolean isActive = ItemMagnetHelper.getIsActive(stack);

        if (isActive) {
            textConsumer.accept(Component.translatable("item.itemmagnetmod.item_magnet.active").withStyle(ChatFormatting.GOLD));
        } else {
            textConsumer.accept(Component.translatable("item.itemmagnetmod.item_magnet.not_active"));
        }
    }
}
