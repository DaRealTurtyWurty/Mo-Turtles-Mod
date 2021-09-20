package com.turtywurty.moturtles.common.entities.goals;

import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.block.Block;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TurtleNavigator extends SwimmerPathNavigator {
	private Block fluid;

	public TurtleNavigator(BetterTurtleEntity turtle, World worldIn, Block fluidIn) {
		super(turtle, worldIn);
		this.fluid = fluidIn;
	}

	/**
	 * If on ground or swimming and can swim
	 */
	protected boolean canNavigate() {
		return true;
	}

	protected PathFinder getPathFinder(int searchDepth) {
		this.nodeProcessor = new WalkAndSwimNodeProcessor();
		return new PathFinder(this.nodeProcessor, searchDepth);
	}

	@SuppressWarnings("deprecation")
	public boolean canEntityStandOnPos(BlockPos pos) {
		if (this.entity instanceof BetterTurtleEntity) {
			BetterTurtleEntity turtleentity = (BetterTurtleEntity) this.entity;
			if (turtleentity.isTravelling()) {
				return this.world.getBlockState(pos).getBlock() == this.fluid;
			}
		}

		return !this.world.getBlockState(pos.down()).isAir();
	}
}
