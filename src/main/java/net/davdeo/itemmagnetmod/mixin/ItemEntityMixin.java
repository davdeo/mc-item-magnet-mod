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

//	private void applyWaterMovement() {
//		ItemEntity thisObj = (ItemEntity)(Object)this;
//		Vec3d vec3d = thisObj.getVelocity();
//		thisObj.setVelocity(vec3d.x * (double)0.99f, Math.min(vec3d.y + (double)5.0E-4f, (double)0.06f), vec3d.z * (double)0.99f);
//	}

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

//	private void tickDoc() {
//		ItemEntity thisObj = (ItemEntity)(Object)this;
//		int i;
//
//		// if no item is in the stack, remove it
//		if (thisObj.getStack().isEmpty()) {
//			thisObj.discard();
//			return;
//		}
//
//		super.tick();
//
//		// decrease pickupDelay until 0 -> then pick it up?
//		if (thisObj.pickupDelay > 0 && thisObj.pickupDelay != Short.MAX_VALUE) {
//			--thisObj.pickupDelay;
//		}
//		// Store prev pos
//		thisObj.prevX = thisObj.getX();
//		thisObj.prevY = thisObj.getY();
//		thisObj.prevZ = thisObj.getZ();
//
//		// store velocity before manipulating it
//		Vec3d initialVelocity = thisObj.getVelocity();
//
//
//		float eyeHeight = thisObj.getStandingEyeHeight() - 0.11111111f;
//
//		// Drift up in water
//		if (thisObj.isTouchingWater() && thisObj.getFluidHeight(FluidTags.WATER) > (double)eyeHeight) {
//			thisObj.applyWaterBuoyancy();
//		}
//		// Drift up in lava
//		else if (thisObj.isInLava() && thisObj.getFluidHeight(FluidTags.LAVA) > (double)eyeHeight) {
//			thisObj.applyLavaBuoyancy();
//		}
//		// increase fallSpeed every tick if item has gravity
//		else if (!thisObj.hasNoGravity()) {
//			thisObj.setVelocity(thisObj.getVelocity().add(0.0, -0.04, 0.0));
//		}
//
//		if (thisObj.getWorld().isClient) {
//			// if client
//			thisObj.noClip = false;
//		} else {
//			// if server and overlap -> push item out of block
//			thisObj.noClip = !thisObj.getWorld().isSpaceEmpty(thisObj, thisObj.getBoundingBox().contract(1.0E-7));
//			if (thisObj.noClip) {
//				thisObj.pushOutOfBlocks(thisObj.getX(), (thisObj.getBoundingBox().minY + thisObj.getBoundingBox().maxY) / 2.0, thisObj.getZ());
//			}
//		}
//
//		// if in air
//		// if is moving any direction that has a horizontal component to it
//		// if (age + objID) % 4 = 0 -> to create a difference between the items (probably responsible for bobbing on ground)
//		// then
//		if (!thisObj.isOnGround()
//				|| thisObj.getVelocity().horizontalLengthSquared() > (double)1.0E-5f
//				|| (thisObj.age + thisObj.getId()) % 4 == 0
//		) {
//			// move by velocity
//			thisObj.move(MovementType.SELF, thisObj.getVelocity());
//
//			float velocityReductionFactor = 0.98f;
//
//			if (thisObj.isOnGround()) {
//				// also take slipperiness of block below into account
//				velocityReductionFactor = thisObj.getWorld().getBlockState(thisObj.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.98f;
//			}
//
//			// reduce current velocity
//			thisObj.setVelocity(thisObj.getVelocity().multiply(velocityReductionFactor, 0.98, velocityReductionFactor));
//
//			if (thisObj.isOnGround()) {
//
//				Vec3d updatedVelocity = thisObj.getVelocity();
//
//				// if velocity.y falls under 0 while on ground set velocity to positive value
//				// This happens when moving downwards -> posy 0 is never reached exactly, it is always overshot, thats why we need to set a positive y vel to move it out of the ground (and create a bouncing effect)
//				if (updatedVelocity.y < 0.0) {
//					thisObj.setVelocity(updatedVelocity.multiply(1.0, -0.5, 1.0));
//				}
//			}
//		}
//
//		// check for merge
//		// as long as entity is moving, check for merge every 2nd tick, if it is not moving, only check every 40th tick
//		boolean wasMovingSinceLastTick = MathHelper.floor(thisObj.prevX) != MathHelper.floor(thisObj.getX())
//				|| MathHelper.floor(thisObj.prevY) != MathHelper.floor(thisObj.getY())
//				|| MathHelper.floor(thisObj.prevZ) != MathHelper.floor(thisObj.getZ());
//		i = wasMovingSinceLastTick ? 2 : 40;
//
//		// if age dividable by 2 or 40
//		// AND if server
//		// AND if canMerge
//		if (thisObj.age % i == 0
//				&& !thisObj.getWorld().isClient
//				&& thisObj.canMerge()
//		) {
//			thisObj.tryMerge();
//		}
//
//		// increase itemAge if not -32768
//		if (thisObj.itemAge != Short.MIN_VALUE) {
//			++thisObj.itemAge;
//		}
//
//		// set velocitydirty to true if velocity changed during this tick
//		thisObj.velocityDirty |= thisObj.updateWaterState();
//		if (
//				!thisObj.getWorld().isClient
//				&& thisObj.getVelocity().subtract(initialVelocity).lengthSquared() > 0.01
//		) {
//			thisObj.velocityDirty = true;
//		}
//
//		// if server & age > 6000 remove item
//		if (!thisObj.getWorld().isClient && thisObj.itemAge >= 6000) {
//			thisObj.discard();
//		}
//	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;getStandingEyeHeight()F"))
	private void moveToTarget(CallbackInfo info) {
		ItemEntity thisObj = (ItemEntity)(Object)this;

		Vec3d targetEyeVector;
		double squaredTargetEyeDistance;

//      // Already set in the ItemEntity tick() function
//		thisObj.prevX = thisObj.getX();
//		thisObj.prevY = thisObj.getY();
//		thisObj.prevZ = thisObj.getZ();

		// Already done in ItemEntity tick() function
//		if (thisObj.isSubmergedIn(FluidTags.WATER)) {
//			this.applyWaterMovement();
//		} else if (!thisObj.hasNoGravity()) {
//			thisObj.setVelocity(thisObj.getVelocity().add(0.0, -0.03, 0.0));
//		}

//		// Is in lava -> not needed since most items are destoyed there anyways
//		if (thisObj.getWorld().getFluidState(thisObj.getBlockPos()).isIn(FluidTags.LAVA)) {
//			thisObj.setVelocity((thisObj.random.nextFloat() - thisObj.random.nextFloat()) * 0.2f, 0.2f, (thisObj.random.nextFloat() - thisObj.random.nextFloat()) * 0.2f);
//		}
// 		// Pushing out of blocks -> not needed, should be supported by native ItemEntity already
// 		if (!thisObj.getWorld().isSpaceEmpty(thisObj.getBoundingBox())) {
//			thisObj.pushOutOfBlocks(thisObj.getX(), (thisObj.getBoundingBox().minY + thisObj.getBoundingBox().maxY) / 2.0, thisObj.getZ());
//		}
		if (thisObj.age % 20 == 1) {
			this.updateTarget();
		}

		// add to velocity depending on how far the items are away -> increased velocity, the closer the items get
		if (this.target != null && (squaredTargetEyeDistance = (targetEyeVector = new Vec3d(this.target.getX() - thisObj.getX(), this.target.getY() + (double)this.target.getStandingEyeHeight() / 2.0 - thisObj.getY(), this.target.getZ() - thisObj.getZ())).lengthSquared()) < SQUARED_PICKUP_DISTANCE) {
			double relativeTargetEyeDistance = 1.0 - Math.sqrt(squaredTargetEyeDistance) / PICKUP_DISTANCE;
			thisObj.setVelocity(thisObj.getVelocity().add(targetEyeVector.normalize().multiply(relativeTargetEyeDistance * relativeTargetEyeDistance * 0.05)));
		}
		if (this.target != null) {
			thisObj.move(MovementType.SELF, thisObj.getVelocity());
		}

		// velocity reduction is already done in itemEntity
//		float velocityReductionFactor = 0.98f;
//
//		if (thisObj.isOnGround()) {
//			velocityReductionFactor = thisObj.getWorld().getBlockState(thisObj.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.98f;
//		}
//
//		thisObj.setVelocity(thisObj.getVelocity().multiply(velocityReductionFactor, 0.98, velocityReductionFactor));

		// Should be responsible for items sticking to the ground
//		if (thisObj.isOnGround()) {
//			thisObj.setVelocity(thisObj.getVelocity().multiply(1.0, -0.9, 1.0));
//		}
	}
}