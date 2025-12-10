package net.davdeo.itemmagnetmod.mixin;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.event.custom.PickupItemEvent;
import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = ItemEntity.class, priority = 1002)
public abstract class ItemEntityMixin extends Entity implements TraceableEntity {
	@Unique
	private static final double PICKUP_DISTANCE = 32.0;
	@Unique
	private static final double SQUARED_PICKUP_DISTANCE = PICKUP_DISTANCE * PICKUP_DISTANCE;
	@Unique
	private Player target;

	protected ItemEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	/*
	 * Updates the target of the ItemEntity.
	 * It searches all players for the closest player with an active magnet in its inventory.
	 * As soon as an ItemEntity has a target, it tries to move towards it.
	 */
	@Unique
	private void updateTarget() {
		ItemEntity thisObj = (ItemEntity)(Object)this;

		Player nextTarget = ItemMagnetHelper.getClosestPlayerWithActiveMagnet(thisObj.level(), thisObj);

		if (nextTarget != null && (nextTarget.isSpectator() || nextTarget.isDeadOrDying())) {
			this.target = null;

			return;
		}

		if (
				this.target == null
						|| this.target.distanceToSqr(thisObj) > SQUARED_PICKUP_DISTANCE
						|| this.target != nextTarget
		) {
			this.target = nextTarget;
		}
	}

	/**
	 * Injects movement logic in tick method, before call of getStandingEyeHeight.
	 * Method is responsible for updating the target every 20 ticks and moving the ItemEntity towards the target.
	 * The velocity towards the target is getting higher the closer the entity is to the target.
	 * The logic used here is inspired by the behaviour of the ExperienceOrbEntity.
	 *
	 *  public void tick() {
	 * ...
	 * 		this.prevX = this.getX();
	 * 		this.prevY = this.getY();
	 * 		this.prevZ = this.getZ();
	 * 		Vec3d vec3d = this.getDeltaMovement();
	 *
	 * ---> Inject here. After call to getDeltaMovement and before applying any movement
	 *
	 *      if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > 0.10000000149011612) {
	 *          this.applyWaterBuoyancy();
	 *      } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > 0.10000000149011612) {
	 *          this.applyLavaBuoyancy();
	 *      } else {
	 *          this.applyGravity();
	 *      }
	 * ...
	 */
	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;isTouchingWater()Z"))
	private void moveToTarget(CallbackInfo info) {
		ItemEntity thisObj = (ItemEntity)(Object)this;

		if (thisObj.tickCount % 20 == 1) {
			this.updateTarget();
		}

		// add to velocity depending on how far the items are away -> increased velocity, the closer the items get#
		// Following logic was taken from ExperienceOrbEntity and slightly modified.
		if (this.target != null) {
			Vec3 targetEyeVector = new Vec3(this.target.getX() - thisObj.getX(), this.target.getY() + this.target.getEyeHeight() / 2.0 - thisObj.getY(), this.target.getZ() - thisObj.getZ());
			double squaredTargetEyeDistance = targetEyeVector.horizontalDistanceSqr();

			if (squaredTargetEyeDistance < SQUARED_PICKUP_DISTANCE) {
				double relativeTargetEyeDistance = 1.0 - Math.sqrt(squaredTargetEyeDistance) / PICKUP_DISTANCE;
				thisObj.setDeltaMovement(thisObj.getDeltaMovement().add(targetEyeVector.normalize().scale(relativeTargetEyeDistance * relativeTargetEyeDistance * 0.1)));
			}
		}

		if (
				this.target != null &&
						this.onGround() &&
						this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5f &&
						(this.tickCount + this.getId()) % 4 == 0
		) {
			thisObj.move(MoverType.SELF, thisObj.getDeltaMovement());
		}
	}


    @Unique
    private int stackCountBeforePickup = 0;

    /**
     * Captures the stack count BEFORE the insertStack call.
     * Injects at the beginning of onPlayerCollision.
     */
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z",
                    shift = At.Shift.BEFORE
            ),
            method = "onPlayerCollision"
    )
    private void captureStackCountBefore(PlayerEntity player, CallbackInfo ci) {
        ItemEntity thisObj = (ItemEntity)(Object)this;
        this.stackCountBeforePickup = thisObj.getStack().getCount();
    }

    /**
     * Invokes the PickupItemEvent AFTER the insertStack call.
     * Calculates the difference between before and after pickup counts.
     */
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z",
                    shift = At.Shift.AFTER
            ),
            method = "onPlayerCollision"
    )
    private void onItemPickup(PlayerEntity player, CallbackInfo ci) {
        ItemEntity thisObj = (ItemEntity)(Object)this;
        int stackCountAfterPickup = thisObj.getStack().getCount();
        int pickedUpItemsCount = this.stackCountBeforePickup - stackCountAfterPickup;

        if (pickedUpItemsCount > 0) {
            PickupItemEvent.EVENT.invoker().onPickup(player, pickedUpItemsCount);
        }
    }
}
