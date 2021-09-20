package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.util.math.BlockPos;

public class TurtlePanicGoal extends PanicGoal {
	private boolean likesFire;

	public TurtlePanicGoal(BetterTurtleEntity turtle, double speedIn, boolean likesFireIn) {
		super(turtle, speedIn);
		this.likesFire = likesFireIn;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		if (this.creature.getRevengeTarget() == null) {
			if (this.likesFire) {
				return false;
			} else if (this.creature.isBurning()) {
				return true;
			}
			return false;
		} else {
			BlockPos blockpos = this.getRandPos(this.creature.world, this.creature, 7, 4);
			if (blockpos != null) {
				this.randPosX = (double) blockpos.getX();
				this.randPosY = (double) blockpos.getY();
				this.randPosZ = (double) blockpos.getZ();
				return true;
			} else {
				return this.findRandomPosition();
			}
		}
	}
}
