package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TurtleGoHomeGoal extends Goal {

	private final BetterTurtleEntity turtle;
	private final double speed;
	private boolean homeless;
	private int homeTimer;

	public TurtleGoHomeGoal(BetterTurtleEntity turtle, double speedIn) {
		this.turtle = turtle;
		this.speed = speedIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		if (this.turtle.isChild()) {
			return false;
		} else if (this.turtle.hasEgg()) {
			return true;
		} else if (this.turtle.getRNG().nextInt(700) != 0) {
			return false;
		} else {
			return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 64.0D);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.turtle.setGoingHome(true);
		this.homeless = false;
		this.homeTimer = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		this.turtle.setGoingHome(false);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 7.0D) && !this.homeless
				&& this.homeTimer <= 600;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		BlockPos homePos = this.turtle.getHome();
		boolean withinDistance = homePos.withinDistance(this.turtle.getPositionVec(), 16.0D);
		if (withinDistance) {
			++this.homeTimer;
		}

		if (this.turtle.getNavigator().noPath()) {
			Vec3d homeVec = new Vec3d(homePos);
			Vec3d randTargetVec = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, homeVec,
					(double) ((float) Math.PI / 10F));
			if (randTargetVec == null) {
				randTargetVec = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, homeVec);
			}

			if (randTargetVec != null && !withinDistance
					&& this.turtle.world.getBlockState(new BlockPos(randTargetVec)).getBlock() != Blocks.WATER) {
				randTargetVec = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 16, 5, homeVec);
			}

			if (randTargetVec == null) {
				this.homeless = true;
				return;
			}

			this.turtle.getNavigator().tryMoveToXYZ(randTargetVec.x, randTargetVec.y, randTargetVec.z, this.speed);
		}

	}
}
