package com.turtywurty.moturtles.common.entities.goals;

import java.util.Random;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TurtleTravelGoal extends Goal {

	private final BetterTurtleEntity turtle;
	private final double speed;
	private boolean isStationary;

	public TurtleTravelGoal(BetterTurtleEntity turtle, double speedIn) {
		this.turtle = turtle;
		this.speed = speedIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		return !this.turtle.isGoingHome() && !this.turtle.hasEgg() && this.turtle.isInWater();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		Random random = this.turtle.getEntityWorld().getRandom();
		int randX = random.nextInt(1025) - 512;
		int randY = random.nextInt(9) - 4;
		int randZ = random.nextInt(1025) - 512;
		if ((double) randY + this.turtle.getPosY() > (double) (this.turtle.world.getSeaLevel() - 1)) {
			randY = 0;
		}

		BlockPos blockpos = new BlockPos((double) randX + this.turtle.getPosX(), (double) randY + this.turtle.getPosY(),
				(double) randZ + this.turtle.getPosZ());
		this.turtle.setTravelPos(blockpos);
		this.turtle.setTravelling(true);
		this.isStationary = false;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@SuppressWarnings("deprecation")
	public void tick() {
		if (this.turtle.getNavigator().noPath()) {
			Vec3d travelVec = new Vec3d(this.turtle.getTravelPos());
			Vec3d randTargetVec = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, travelVec,
					(double) ((float) Math.PI / 10F));
			if (randTargetVec == null) {
				randTargetVec = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, travelVec);
			}

			if (randTargetVec != null) {
				int i = MathHelper.floor(randTargetVec.x);
				int j = MathHelper.floor(randTargetVec.z);
				if (!this.turtle.world.isAreaLoaded(i - 34, 0, j - 34, i + 34, 0, j + 34)) {
					randTargetVec = null;
				}
			}

			if (randTargetVec == null) {
				this.isStationary = true;
				return;
			}

			this.turtle.getNavigator().tryMoveToXYZ(randTargetVec.x, randTargetVec.y, randTargetVec.z, this.speed);
		}

	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return !this.turtle.getNavigator().noPath() && !this.isStationary && !this.turtle.isGoingHome()
				&& !this.turtle.isInLove() && !this.turtle.hasEgg();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		this.turtle.setTravelling(false);
		super.resetTask();
	}
}
