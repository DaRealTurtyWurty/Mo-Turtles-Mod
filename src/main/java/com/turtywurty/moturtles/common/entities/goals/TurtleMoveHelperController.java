package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class TurtleMoveHelperController extends MovementController {

	private final BetterTurtleEntity turtle;

	public TurtleMoveHelperController(BetterTurtleEntity turtleIn) {
		super(turtleIn);
		this.turtle = turtleIn;
	}

	private void updateSpeed() {
		if (this.turtle.isInWater() || this.turtle.isInLava()) {
			this.turtle.setMotion(this.turtle.getMotion().add(0.0D, 0.005D, 0.0D));
			if (!this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 16.0D)) {
				this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0F, 0.08F));
			}

			if (this.turtle.isChild()) {
				this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 3.0F, 0.06F));
			}
		} else if (this.turtle.onGround) {
			this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0F, 0.06F));
		}

	}

	public void tick() {
		this.updateSpeed();
		if (this.action == MovementController.Action.MOVE_TO && !this.turtle.getNavigator().noPath()) {
			double d0 = this.posX - this.turtle.getPosX();
			double d1 = this.posY - this.turtle.getPosY();
			double d2 = this.posZ - this.turtle.getPosZ();
			double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
			d1 = d1 / d3;
			float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
			this.turtle.rotationYaw = this.limitAngle(this.turtle.rotationYaw, f, 90.0F);
			this.turtle.renderYawOffset = this.turtle.rotationYaw;
			float f1 = (float) (this.speed
					* this.turtle.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
			this.turtle.setAIMoveSpeed(MathHelper.lerp(0.125F, this.turtle.getAIMoveSpeed(), f1));
			this.turtle.setMotion(
					this.turtle.getMotion().add(0.0D, (double) this.turtle.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
		} else {
			this.turtle.setAIMoveSpeed(0.0F);
		}
	}
}
