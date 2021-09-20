package com.turtywurty.moturtles.common.entities.goals;

import java.util.Random;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class TurtleLayEggGoal extends MoveToBlockGoal {
	private final BetterTurtleEntity turtle;
	private final Block turtleEgg;
	private final IntegerProperty eggProperty;

	public TurtleLayEggGoal(BetterTurtleEntity turtle, double speedIn, Block turtleEggIn,
			IntegerProperty eggPropertyIn) {
		super(turtle, speedIn, 16);
		this.turtle = turtle;
		this.turtleEgg = turtleEggIn;
		this.eggProperty = eggPropertyIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		return this.turtle.hasEgg() && this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0D)
				? super.shouldExecute()
				: false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && this.turtle.hasEgg()
				&& this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0D);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		super.tick();
		BlockPos blockpos = new BlockPos(this.turtle);
		if (!this.turtle.isInWater() && this.getIsAboveDestination()) {
			if (this.turtle.isDigging < 1) {
				this.turtle.setDigging(true);
			} else if (this.turtle.isDigging > 200) {
				World world = this.turtle.world;
				world.playSound((PlayerEntity) null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS,
						0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
				world.setBlockState(this.destinationBlock.up(), this.turtleEgg.getDefaultState().with(this.eggProperty,
						Integer.valueOf(new Random().nextInt(4) + 1)), 3);
				this.turtle.setHasEgg(false);
				this.turtle.setDigging(false);
				this.turtle.setInLove(600);
			}

			if (this.turtle.isDigging()) {
				this.turtle.isDigging++;
			}
		}

	}

	/**
	 * Return true to set given position as destination
	 */
	protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
		if (!worldIn.isAirBlock(pos.up())) {
			return false;
		} else {
			Block block = worldIn.getBlockState(pos).getBlock();
			return block == this.turtle.getLayEggBlock();
		}
	}
}
