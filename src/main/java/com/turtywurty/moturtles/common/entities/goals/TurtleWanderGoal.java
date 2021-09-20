package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.entity.ai.goal.RandomWalkingGoal;

public class TurtleWanderGoal extends RandomWalkingGoal {

	private final BetterTurtleEntity turtle;

	public TurtleWanderGoal(BetterTurtleEntity turtle, double speedIn, int chance) {
		super(turtle, speedIn, chance);
		this.turtle = turtle;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		return !this.creature.isInWater() && !this.creature.isInLava() && !this.turtle.isGoingHome()
				&& !this.turtle.hasEgg() ? super.shouldExecute() : false;
	}
}
