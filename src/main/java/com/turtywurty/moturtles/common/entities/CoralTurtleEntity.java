package com.turtywurty.moturtles.common.entities;

import java.util.ArrayList;
import java.util.List;

import com.turtywurty.moturtles.common.entities.goals.TurtleGoHomeGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleGoToWaterGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleLayEggGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleMateGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtlePanicGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtlePlayerTemptGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleTravelGoal;
import com.turtywurty.moturtles.common.entities.goals.TurtleWanderGoal;
import com.turtywurty.moturtles.core.init.BlockTags;
import com.turtywurty.moturtles.core.init.EntityInit;
import com.turtywurty.moturtles.core.util.NBTUtils;
import com.turtywurty.moturtles.core.util.Transformation;
import com.turtywurty.moturtles.core.util.TurtleDataSerializers;

import net.minecraft.block.AbstractCoralPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.CoralFanBlock;
import net.minecraft.block.CoralFinBlock;
import net.minecraft.block.CoralPlantBlock;
import net.minecraft.block.CoralWallFanBlock;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CoralTurtleEntity extends BetterTurtleEntity {

	private static final DataParameter<Boolean> CORAL_DEAD = EntityDataManager.createKey(CoralTurtleEntity.class,
			DataSerializers.BOOLEAN);

	private static final DataParameter<List<BlockState>> CORALS = EntityDataManager.createKey(CoralTurtleEntity.class,
			TurtleDataSerializers.LIST_BLOCKSTATE);
	private static final DataParameter<List<BlockState>> FAN_CORALS = EntityDataManager.createKey(CoralTurtleEntity.class,
			TurtleDataSerializers.LIST_BLOCKSTATE);

	private static final DataParameter<List<Transformation>> CORAL_TRANSFORMATIONS = EntityDataManager
			.createKey(CoralTurtleEntity.class, TurtleDataSerializers.LIST_TRANSFORMATION);
	private static final DataParameter<List<Transformation>> FAN_CORAL_TRANSFORMATIONS = EntityDataManager
			.createKey(CoralTurtleEntity.class, TurtleDataSerializers.LIST_TRANSFORMATION);

	private static final List<Transformation> TRANSFORMATIONS = new ArrayList<Transformation>();
	private static final List<Transformation> FAN_TRANSFORMATIONS = new ArrayList<Transformation>();

	static {
		TRANSFORMATIONS.add(new Transformation(new Vector3d(-0.5, 0.5, 0.0), Transformation.DOUBLE_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		TRANSFORMATIONS.add(new Transformation(new Vector3d(-0.15, 0.5, -0.6), Transformation.DOUBLE_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		TRANSFORMATIONS.add(new Transformation(new Vector3d(0.0, 0.4, 0.0), Transformation.DOUBLE_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		TRANSFORMATIONS.add(new Transformation(new Vector3d(-0.4, 0.4, -0.6), Transformation.DOUBLE_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		TRANSFORMATIONS.add(new Transformation(new Vector3d(-0.8, 0.3, 0.2), Transformation.DOUBLE_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));

		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(1.3, 0.75, -0.6), new Vector3d(-90, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(-1.25, 0.75, 0.2), new Vector3d(90, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(-0.35, 0.25, -0.85), new Vector3d(-35, 0, 0), Vector3f.XP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(1.3, 0.75, 0.15), new Vector3d(-90, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(0, 0.75, 1.5), new Vector3d(180, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(0.75, 0.75, 1.5), new Vector3d(180, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
		FAN_TRANSFORMATIONS.add(new Transformation(new Vector3d(-1.3, 0.75, 0.6), new Vector3d(90, 0, 0), Vector3f.YP,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO));
	}

	private int coralTimer = 0, growthTimer = 0;

	public CoralTurtleEntity(EntityType<? extends BetterTurtleEntity> type, World worldIn) {
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
	protected void registerData() {
		super.registerData();
		this.dataManager.register(CORAL_DEAD, false);
		this.dataManager.register(CORALS, new ArrayList<BlockState>());
		this.dataManager.register(FAN_CORALS, new ArrayList<BlockState>());
		List<Transformation> transformations = new ArrayList<Transformation>();
		for (int i = 0; i < 5; i++) {
			transformations.add(Transformation.NONE);
		}
		this.dataManager.register(CORAL_TRANSFORMATIONS, transformations);
		List<Transformation> fanTransformations = new ArrayList<Transformation>();
		for (int i = 0; i < 7; i++) {
			fanTransformations.add(Transformation.NONE);
		}
		this.dataManager.register(FAN_CORAL_TRANSFORMATIONS, fanTransformations);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		List<BlockState> corals = new ArrayList<BlockState>();
		for (int i = 0; i < 5; i++) {
			corals.add(net.minecraft.tags.BlockTags.CORAL_PLANTS.getRandomElement(this.getRNG()).getDefaultState());
		}
		this.dataManager.set(CORALS, corals);

		List<BlockState> fanCorals = new ArrayList<BlockState>();
		for (int i = 0; i < 7; i++) {
			fanCorals.add(BlockTags.CORAL_WALL_FANS.getRandomElement(this.getRNG()).getDefaultState());
		}
		this.dataManager.set(FAN_CORALS, fanCorals);

		List<Transformation> transformationCopy = new ArrayList<Transformation>(TRANSFORMATIONS);
		List<Transformation> newTransformations = new ArrayList<Transformation>();
		for (int i = 0; i < 5; i++) {
			int rngIndex = this.getRNG().nextInt(transformationCopy.size());
			newTransformations.add(transformationCopy.get(rngIndex));
			transformationCopy.remove(rngIndex);
		}
		this.dataManager.set(CORAL_TRANSFORMATIONS, newTransformations);

		List<Transformation> fanTransformationCopy = new ArrayList<Transformation>(FAN_TRANSFORMATIONS);
		List<Transformation> newFanTransformations = new ArrayList<Transformation>();
		for (int i = 0; i < 7; i++) {
			int rngIndex = this.getRNG().nextInt(fanTransformationCopy.size());
			newFanTransformations.add(fanTransformationCopy.get(rngIndex));
			fanTransformationCopy.remove(rngIndex);
		}
		this.dataManager.set(FAN_CORAL_TRANSFORMATIONS, newFanTransformations);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public List<Transformation> getTransformations() {
		return this.dataManager.get(CORAL_TRANSFORMATIONS);
	}

	public List<Transformation> getFanTransformations() {
		return this.dataManager.get(FAN_CORAL_TRANSFORMATIONS);
	}

	public void setFanTransformations(List<Transformation> transformations) {
		this.dataManager.set(FAN_CORAL_TRANSFORMATIONS, transformations);
	}

	public void setTransformations(List<Transformation> transformations) {
		this.dataManager.set(CORAL_TRANSFORMATIONS, transformations);
	}

	public void setCorals(List<BlockState> corals) {
		this.dataManager.set(CORALS, corals);
	}

	public void setFanCorals(List<BlockState> corals) {
		this.dataManager.set(FAN_CORALS, corals);
	}

	@Override
	public void livingTick() {
		super.livingTick();
		if (!this.isInWater() && !this.getEntityWorld().isRainingAt(this.getPosition())) {
			this.coralTimer++;
		} else {
			this.coralTimer = 0;
		}

		System.out.println(this.growthTimer + " " + this.getDisplayName().getFormattedText());

		if (this.growthTimer >= 999999 && !this.hasCorals() && !this.hasFans()) {
			this.growthTimer = 0;
			if (!this.isCoralDead()) {
				List<BlockState> corals = new ArrayList<BlockState>();
				for (int i = 0; i < 5; i++) {
					corals.add(net.minecraft.tags.BlockTags.CORAL_PLANTS.getRandomElement(this.getRNG()).getDefaultState());
				}
				this.dataManager.set(CORALS, corals);

				List<BlockState> fans = new ArrayList<BlockState>();
				for (int i = 0; i < 7; i++) {
					fans.add(BlockTags.CORAL_WALL_FANS.getRandomElement(this.getRNG()).getDefaultState());
				}
				this.dataManager.set(FAN_CORALS, fans);
			} else {
				List<BlockState> corals = new ArrayList<BlockState>();
				for (int i = 0; i < 5; i++) {
					corals.add(BlockTags.DEAD_CORAL_PLANTS.getRandomElement(this.getRNG()).getDefaultState());
				}
				this.dataManager.set(CORALS, corals);

				List<BlockState> fans = new ArrayList<BlockState>();
				for (int i = 0; i < 7; i++) {
					fans.add(BlockTags.DEAD_CORAL_WALL_FANS.getRandomElement(this.getRNG()).getDefaultState());
				}
				this.dataManager.set(FAN_CORALS, fans);
			}
		} else if (this.growthTimer < 999999 && !this.hasCorals() && !this.hasFans()) {
			this.growthTimer++;
		}

		if ((float) this.coralTimer > 999999 && !this.dataManager.get(CORAL_DEAD)) {
			this.dataManager.set(CORAL_DEAD, true);
			List<BlockState> deadCorals = new ArrayList<BlockState>();
			for (BlockState coral : this.getCorals()) {
				Block coralBlock = coral.getBlock();
				if (coralBlock instanceof CoralFinBlock) {
					deadCorals.add(((CoralFinBlock) coralBlock).deadBlock.getDefaultState());
				} else if (coralBlock instanceof CoralPlantBlock) {
					deadCorals.add(((CoralPlantBlock) coralBlock).deadBlock.getDefaultState());
				} else if (coralBlock instanceof CoralWallFanBlock) {
					deadCorals.add(((CoralWallFanBlock) coralBlock).deadBlock.getDefaultState());
				} else if (coralBlock instanceof CoralBlock) {
					deadCorals.add(((CoralBlock) coralBlock).deadBlock.getDefaultState());
				}
			}
			this.dataManager.set(CORALS, deadCorals);

			List<BlockState> deadFans = new ArrayList<BlockState>();
			for (BlockState fan : this.getFans()) {
				Block fanBlock = fan.getBlock();
				if (fanBlock instanceof CoralFinBlock) {
					deadCorals.add(((CoralFinBlock) fanBlock).deadBlock.getDefaultState());
				} else if (fanBlock instanceof CoralPlantBlock) {
					deadCorals.add(((CoralPlantBlock) fanBlock).deadBlock.getDefaultState());
				} else if (fanBlock instanceof CoralWallFanBlock) {
					deadCorals.add(((CoralWallFanBlock) fanBlock).deadBlock.getDefaultState());
				} else if (fanBlock instanceof CoralBlock) {
					deadCorals.add(((CoralBlock) fanBlock).deadBlock.getDefaultState());
				}
			}
			this.dataManager.set(FAN_CORALS, deadFans);
		}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.coralTimer = compound.getInt("CoralTimer");
		this.growthTimer = compound.getInt("GrowthTimer");
		this.dataManager.set(CORAL_DEAD, compound.getBoolean("CoralDead"));
		this.setTransformations(NBTUtils.readTransformations(compound.getCompound("Transformations")));
		this.setFanTransformations(NBTUtils.readTransformations(compound.getCompound("FanTransformations")));
		this.setCorals(NBTUtils.readBlockStates(compound.getIntArray("Corals")));
		this.setFanCorals(NBTUtils.readBlockStates(compound.getIntArray("Fans")));
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("CoralTimer", this.coralTimer);
		compound.putInt("GrowthTimer", this.growthTimer);
		compound.putBoolean("CoralDead", this.isCoralDead());
		compound.put("Transformations", NBTUtils.writeTransformations(this.getTransformations()));
		compound.put("FanTransformations", NBTUtils.writeTransformations(this.getFanTransformations()));
		compound.putIntArray("Corals", NBTUtils.writeBlockStates(this.getCorals()));
		compound.putIntArray("Fans", NBTUtils.writeBlockStates(this.getFans()));
	}

	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {
		if (player.getHeldItem(hand).getItem() instanceof ShearsItem) {
			if (this.hasCorals()) {
				for (BlockState coralState : this.getCorals()) {
					AbstractCoralPlantBlock coral = (AbstractCoralPlantBlock) coralState.getBlock();
					this.entityDropItem(coral);
				}
				this.dataManager.set(CORALS, new ArrayList<BlockState>());
				player.getHeldItem(hand).damageItem(1, player, p -> player.sendBreakAnimation(hand));
				this.growthTimer++;
			}

			if (this.hasFans()) {
				for (BlockState coralState : this.getFans()) {
					AbstractCoralPlantBlock coral = (AbstractCoralPlantBlock) coralState.getBlock();
					this.entityDropItem(coral);
				}
				this.dataManager.set(FAN_CORALS, new ArrayList<BlockState>());
				player.getHeldItem(hand).damageItem(1, player, p -> player.sendBreakAnimation(hand));
				this.growthTimer++;
			}
		}
		return super.processInteract(player, hand);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem) {
			if (((BlockItem) stack.getItem()).getBlock() instanceof AbstractCoralPlantBlock) {
				return true;
			}
		}
		return false;
	}

	public boolean hasCorals() {
		for (BlockState coral : this.dataManager.get(CORALS)) {
			if (coral.getBlock() instanceof AbstractCoralPlantBlock) {
				return true;
			}
		}
		return false;
	}

	public boolean hasFans() {
		for (BlockState fan : this.dataManager.get(FAN_CORALS)) {
			if (fan.getBlock() instanceof CoralFanBlock || fan.getBlock() instanceof CoralWallFanBlock) {
				return true;
			}
		}
		return false;
	}

	public List<BlockState> getCorals() {
		List<BlockState> corals = new ArrayList<BlockState>();
		for (BlockState coral : this.dataManager.get(CORALS)) {
			if (coral != null) {
				if (coral.getBlock() instanceof AbstractCoralPlantBlock) {
					corals.add(coral);
				}
			}
		}
		return corals;
	}

	public List<BlockState> getFans() {
		List<BlockState> fans = new ArrayList<BlockState>();
		for (BlockState fan : this.dataManager.get(FAN_CORALS)) {
			if (fan != null) {
				if (fan.getBlock() instanceof CoralFanBlock || fan.getBlock() instanceof CoralWallFanBlock) {
					fans.add(fan);
				}
			}
		}
		return fans;
	}

	public boolean isCoralDead() {
		return this.dataManager.get(CORAL_DEAD);
	}

	@Override
	public Block getFluidBlock() {
		return Blocks.WATER;
	}

	@Override
	public Item getBreedingItem() {
		return Blocks.BUBBLE_CORAL_FAN.asItem();
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
		return Blocks.BUBBLE_CORAL_FAN.asItem();
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		return (AgeableEntity) EntityInit.CORAL_TURTLE.get().create(this.world);
	}
}
