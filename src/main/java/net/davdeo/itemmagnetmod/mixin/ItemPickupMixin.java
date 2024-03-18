package net.davdeo.itemmagnetmod.mixin;

import net.davdeo.itemmagnetmod.event.custom.PickupItemCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"), method = "onPlayerCollision")
    private boolean onPickup(PlayerInventory instance, ItemStack stack) {
        PlayerEntity player = instance.player;

        PickupItemCallback.EVENT.invoker().pickup(player, (ItemEntity)(Object)this);

        return instance.insertStack(stack);
    }
}
