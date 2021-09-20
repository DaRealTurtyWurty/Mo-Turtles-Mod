package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class TurtleGoToWaterGoal extends MoveToBlockGoal {
	private final BetterTurtleEntity turtle;

	public TurtleGoToWaterGoal(BetterTurtleEntity turtleIn, double speedIn) {
		super(turtleIn, turtleIn.isChild() ? 2.0D : speedIn, 24);
		this.turtle = turtleIn;
		/* Something to do with a move timer maybe? */
		this.field_203112_e = -1;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return !this.turtle.isInWater() && this.timeoutCounter <= 1200
				&& this.shouldMoveTo(this.turtle.world, this.destinationBlock);
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		if (this.turtle.isChild() && !this.turtle.isInWater()) {
			return super.shouldExecute();
		} else {
			return !this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg()
					? super.shouldExecute()
					: false;
		}
	}

	public boolean shouldMove() {
		return this.timeoutCounter % 160 == 0;
	}

	/**
	 * Return true to set given position as destination
	 */
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		return block == Blocks.WATER;
	}
}
