package com.turtywurty.moturtles.common.entities;

import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.turtywurty.moturtles.common.entities.goals.TurtleMoveHelperController;
import com.turtywurty.moturtles.common.entities.goals.TurtleNavigator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class BetterTurtleEntity extends AnimalEntity {

	private static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<BlockPos> TRAVEL_POS = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BLOCK_POS);
	private static final DataParameter<Boolean> GOING_HOME = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> TRAVELLING = EntityDataManager.createKey(BetterTurtleEntity.class,
			DataSerializers.BOOLEAN);
	public int isDigging;
	public static final Predicate<LivingEntity> TARGET_DRY_BABY = (living) -> {
		return living.isChild() && !living.isInWater();
	};

	public BetterTurtleEntity(EntityType<? extends BetterTurtleEntity> type, World worldIn) {
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
		this.moveController = new TurtleMoveHelperController(this);
		this.stepHeight = 1.0F;
	}

	public void setHome(BlockPos position) {
		this.dataManager.set(HOME_POS, position);
	}

	public BlockPos getHome() {
		return this.dataManager.get(HOME_POS);
	}

	public void setTravelPos(BlockPos position) {
		this.dataManager.set(TRAVEL_POS, position);
	}

	public BlockPos getTravelPos() {
		return this.dataManager.get(TRAVEL_POS);
	}

	public boolean hasEgg() {
		return this.dataManager.get(HAS_EGG);
	}

	public void setHasEgg(boolean hasEgg) {
		this.dataManager.set(HAS_EGG, hasEgg);
	}

	public boolean isDigging() {
		return this.dataManager.get(IS_DIGGING);
	}

	public void setDigging(boolean isDigging) {
		this.isDigging = isDigging ? 1 : 0;
		this.dataManager.set(IS_DIGGING, isDigging);
	}

	public boolean isGoingHome() {
		return this.dataManager.get(GOING_HOME);
	}

	public void setGoingHome(boolean isGoingHome) {
		this.dataManager.set(GOING_HOME, isGoingHome);
	}

	public boolean isTravelling() {
		return this.dataManager.get(TRAVELLING);
	}

	public void setTravelling(boolean isTravelling) {
		this.dataManager.set(TRAVELLING, isTravelling);
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(HOME_POS, BlockPos.ZERO);
		this.dataManager.register(HAS_EGG, false);
		this.dataManager.register(TRAVEL_POS, BlockPos.ZERO);
		this.dataManager.register(GOING_HOME, false);
		this.dataManager.register(TRAVELLING, false);
		this.dataManager.register(IS_DIGGING, false);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("HomePosX", this.getHome().getX());
		compound.putInt("HomePosY", this.getHome().getY());
		compound.putInt("HomePosZ", this.getHome().getZ());
		compound.putBoolean("HasEgg", this.hasEgg());
		compound.putInt("TravelPosX", this.getTravelPos().getX());
		compound.putInt("TravelPosY", this.getTravelPos().getY());
		compound.putInt("TravelPosZ", this.getTravelPos().getZ());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditional(CompoundNBT compound) {
		int i = compound.getInt("HomePosX");
		int j = compound.getInt("HomePosY");
		int k = compound.getInt("HomePosZ");
		this.setHome(new BlockPos(i, j, k));
		super.readAdditional(compound);
		this.setHasEgg(compound.getBoolean("HasEgg"));
		int l = compound.getInt("TravelPosX");
		int i1 = compound.getInt("TravelPosY");
		int j1 = compound.getInt("TravelPosZ");
		this.setTravelPos(new BlockPos(l, i1, j1));
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		this.setHome(new BlockPos(this));
		this.setTravelPos(BlockPos.ZERO);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public boolean canSpawn(EntityType<BetterTurtleEntity> entity, IWorld world, SpawnReason reason, BlockPos pos,
			Random rand) {
		return pos.getY() < world.getSeaLevel() + 4
				&& world.getBlockState(pos.down()).getBlock() == this.getLayEggBlock()
				&& world.getLightSubtracted(pos, 0) > 8;
	}

	protected abstract void registerGoals();

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	public boolean isPushedByWater() {
		return false;
	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.WATER;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	public int getTalkInterval() {
		return 200;
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return !this.isInWater() && this.onGround && !this.isChild() ? SoundEvents.ENTITY_TURTLE_AMBIENT_LAND
				: super.getAmbientSound();
	}

	protected void playSwimSound(float volume) {
		super.playSwimSound(volume * 1.5F);
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.ENTITY_TURTLE_SWIM;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return this.isChild() ? SoundEvents.ENTITY_TURTLE_HURT_BABY : SoundEvents.ENTITY_TURTLE_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return this.isChild() ? SoundEvents.ENTITY_TURTLE_DEATH_BABY : SoundEvents.ENTITY_TURTLE_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		SoundEvent soundevent = this.isChild() ? SoundEvents.ENTITY_TURTLE_SHAMBLE_BABY
				: SoundEvents.ENTITY_TURTLE_SHAMBLE;
		this.playSound(soundevent, 0.15F, 1.0F);
	}

	public boolean canBreed() {
		return super.canBreed() && !this.hasEgg();
	}

	protected float determineNextStepDistance() {
		return this.distanceWalkedOnStepModified + 0.15F;
	}

	public float getRenderScale() {
		return this.isChild() ? 0.3F : 1.0F;
	}

	public abstract Block getFluidBlock();

	/**
	 * Returns new PathNavigateGround instance
	 */
	protected PathNavigator createNavigator(World worldIn) {
		return new TurtleNavigator(this, worldIn, this.getFluidBlock());
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it
	 * (wheat, carrots or seeds depending on the animal type)
	 */
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == this.getBreedingItem();
	}

	public abstract Item getBreedingItem();

	public abstract Tag<Fluid> getFluidTag();

	@SuppressWarnings("deprecation")
	public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
		if (!this.isGoingHome() && worldIn.getFluidState(pos).isTagged(this.getFluidTag())) {
			return 10.0F;
		} else {
			return worldIn.getBlockState(pos.down()).getBlock() == this.getLayEggBlock() ? 10.0F
					: worldIn.getBrightness(pos) - 0.5F;
		}
	}

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	public void livingTick() {
		super.livingTick();
		if (this.isAlive() && this.isDigging() && this.isDigging >= 1 && this.isDigging % 5 == 0) {
			BlockPos blockpos = new BlockPos(this);
			if (this.world.getBlockState(blockpos.down()).getBlock() == this.getLayEggBlock()) {
				this.world.playEvent(2001, blockpos, Block.getStateId(this.getLayEggBlock().getDefaultState()));
			}
		}
	}

	public abstract Block getLayEggBlock();

	/**
	 * This is called when Entity's growing age timer reaches 0 (negative values are
	 * considered as a child, positive as an adult)
	 */
	protected void onGrowingAdult() {
		super.onGrowingAdult();
		if (!this.isChild() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			this.entityDropItem(this.getGrowupItem(), 1);
		}
	}

	public abstract Item getGrowupItem();

	public void travel(Vec3d positionIn) {
		if (this.isServerWorld() && this.isInWater()) {
			this.moveRelative(0.1F, positionIn);
			this.move(MoverType.SELF, this.getMotion());
			this.setMotion(this.getMotion().scale(0.9D));
			if (this.getAttackTarget() == null
					&& (!this.isGoingHome() || !this.getHome().withinDistance(this.getPositionVec(), 20.0D))) {
				this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(positionIn);
		}

	}

	public boolean canBeLeashedTo(PlayerEntity player) {
		return false;
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(LightningBoltEntity lightningBolt) {
		this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
	}
}
