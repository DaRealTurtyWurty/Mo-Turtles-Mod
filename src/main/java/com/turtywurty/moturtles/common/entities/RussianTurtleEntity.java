package com.turtywurty.moturtles.common.entities;

import com.turtywurty.moturtles.common.entities.goals.TurtleGoHomeGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleGoToWaterGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleLayEggGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleMateGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtlePanicGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtlePlayerTemptGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleTravelGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleWanderGoal;
import com.turtywurty.moturtles.core.init.EntityInit;
import com.turtywurty.moturtles.core.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.World;

public class RussianTurtleEntity extends BetterTurtleEntity {

	public RussianTurtleEntity(EntityType<? extends BetterTurtleEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new TurtlePanicGoal(this, 1.2D, false));
		this.goalSelector.addGoal(1, new TurtleMateGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new TurtleLayEggGoal(this, 1.0D, Blocks.TURTLE_EGG, TurtleEggBlock.EGGS));
		this.goalSelector.addGoal(2, new TurtlePlayerTemptGoal(this, 1.1D, this.getBreedingItem()));
		this.goalSelector.addGoal(3, new TurtleGoToWaterGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TurtleGoHomeGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new TurtleTravelGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(9, new TurtleWanderGoal(this, 1.0D, 100));
	}

	@Override
	public Block getFluidBlock() {
		return Blocks.WATER;
	}

	@Override
	public Item getBreedingItem() {
		return ItemInit.VODKA.get();
	}

	@Override
	public Tag<Fluid> getFluidTag() {
		return FluidTags.WATER;
	}

	@Override
	public Block getLayEggBlock() {
		return Blocks.SAND;
	}

	@Override
	public Item getGrowupItem() {
		return ItemInit.VODKA.get();
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		return (AgeableEntity) EntityInit.RUSSIAN_TURTLE.get().create(this.world);
	}
}
