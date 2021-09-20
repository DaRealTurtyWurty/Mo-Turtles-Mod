package com.turtywurty.moturtles.common.entities.goals;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TurtlePlayerTemptGoal extends Goal {

	private static final EntityPredicate playerPredicate = (new EntityPredicate()).setDistance(10.0D)
			.allowFriendlyFire().allowInvulnerable();
	private final BetterTurtleEntity turtle;
	private final double speed;
	private PlayerEntity tempter;
	private int cooldown;
	private final Set<Item> temptItems;

	public TurtlePlayerTemptGoal(BetterTurtleEntity turtle, double speedIn, Item temptItem) {
		this.turtle = turtle;
		this.speed = speedIn;
		this.temptItems = Sets.newHashSet(temptItem);
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		if (this.cooldown > 0) {
			--this.cooldown;
			return false;
		} else {
			this.tempter = this.turtle.world.getClosestPlayer(playerPredicate, this.turtle);
			if (this.tempter == null) {
				return false;
			} else {
				return this.isTemptedBy(this.tempter.getHeldItemMainhand())
						|| this.isTemptedBy(this.tempter.getHeldItemOffhand());
			}
		}
	}

	private boolean isTemptedBy(ItemStack stack) {
		return this.temptItems.contains(stack.getItem());
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		this.tempter = null;
		this.turtle.getNavigator().clearPath();
		this.cooldown = 100;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		this.turtle.getLookController().setLookPositionWithEntity(this.tempter,
				(float) (this.turtle.getHorizontalFaceSpeed() + 20), (float) this.turtle.getVerticalFaceSpeed());
		if (this.turtle.getDistanceSq(this.tempter) < 6.25D) {
			this.turtle.getNavigator().clearPath();
		} else {
			this.turtle.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
		}
	}
}
