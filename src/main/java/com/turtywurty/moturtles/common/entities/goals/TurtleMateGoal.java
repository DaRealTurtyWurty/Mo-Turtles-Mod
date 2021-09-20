package com.turtywurty.moturtles.common.entities.goals;

import java.util.Random;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.GameRules;

public class TurtleMateGoal extends BreedGoal {

	private final BetterTurtleEntity turtle;

	public TurtleMateGoal(BetterTurtleEntity turtle, double speedIn) {
		super(turtle, speedIn);
		this.turtle = turtle;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		return super.shouldExecute() && !this.turtle.hasEgg();
	}

	/**
	 * Spawns a baby animal of the same type.
	 */
	protected void spawnBaby() {
		ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
		if (serverplayerentity == null && this.targetMate.getLoveCause() != null) {
			serverplayerentity = this.targetMate.getLoveCause();
		}

		if (serverplayerentity != null) {
			serverplayerentity.addStat(Stats.ANIMALS_BRED);
			CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.targetMate,
					(AgeableEntity) null);
		}

		this.turtle.setHasEgg(true);
		this.animal.resetInLove();
		this.targetMate.resetInLove();
		Random random = this.animal.getRNG();
		if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(),
					this.animal.getPosZ(), random.nextInt(7) + 1));
		}
	}
}
