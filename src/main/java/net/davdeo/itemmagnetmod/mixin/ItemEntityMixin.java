package net.davdeo.itemmagnetmod.mixin;

import net.davdeo.itemmagnetmod.ItemMagnetMod;
import net.davdeo.itemmagnetmod.util.ItemMagnetHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
	private static final double PICKUP_DISTANCE = 32.0;
	private static final double SQUARED_PICKUP_DISTANCE = PICKUP_DISTANCE * PICKUP_DISTANCE;
	private PlayerEntity target;

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	private void updateTarget() {
		ItemEntity thisObj = (ItemEntity)(Object)this;

		PlayerEntity target = ItemMagnetHelper.getClosestPlayerWithActiveMagnet(thisObj.getWorld(), thisObj);

		if (target != null && (target.isSpectator() || target.isDead())) {
			this.target = null;

			return;
		}

		if (
				this.target == null
				|| this.target.squaredDistanceTo(thisObj) > SQUARED_PICKUP_DISTANCE
				|| this.target != target
		) {
			ItemMagnetMod.LOGGER.info("Updating target " + (target == null ? ">>to no target<<" : ">>to player<<"));
			this.target = target;
		}
	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;getStandingEyeHeight()F"))
	private void moveToTarget(CallbackInfo info) {
		ItemEntity thisObj = (ItemEntity)(Object)this;

		Vec3d targetEyeVector;
		double squaredTargetEyeDistance;

		if (thisObj.age % 20 == 1) {
			this.updateTarget();
		}

		// add to velocity depending on how far the items are away -> increased velocity, the closer the items get
		if (this.target != null && (squaredTargetEyeDistance = (targetEyeVector = new Vec3d(this.target.getX() - thisObj.getX(), this.target.getY() + (double)this.target.getStandingEyeHeight() / 2.0 - thisObj.getY(), this.target.getZ() - thisObj.getZ())).lengthSquared()) < SQUARED_PICKUP_DISTANCE) {
			double relativeTargetEyeDistance = 1.0 - Math.sqrt(squaredTargetEyeDistance) / PICKUP_DISTANCE;
			thisObj.setVelocity(thisObj.getVelocity().add(targetEyeVector.normalize().multiply(relativeTargetEyeDistance * relativeTargetEyeDistance * 0.1)));
		}
		if (
			this.target != null &&
			this.isOnGround() &&
			this.getVelocity().horizontalLengthSquared() > (double)1.0E-5f &&
			(this.age + this.getId()) % 4 == 0
		) {
			thisObj.move(MovementType.SELF, thisObj.getVelocity());
		}
	}
}