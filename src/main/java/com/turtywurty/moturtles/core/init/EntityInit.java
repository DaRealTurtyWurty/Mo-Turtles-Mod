package com.turtywurty.moturtles.core.init;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.common.entities.CoralTurtleEntity;
import com.turtywurty.moturtles.common.entities.RussianTurtleEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			MoTurtlesMod.MOD_ID);

	// public static final RegistryObject<EntityType<LavaTurtleEntity>> LAVA_TURTLE
	// =
	// TURTLES.register("lava_turtle", () ->
	// EntityType.Builder.create(LavaTurtleEntity::new,
	// EntityClassification.WATER_CREATURE));

	public static final RegistryObject<EntityType<?>> CORAL_TURTLE = ENTITIES.register("coral_turtle",
			() -> EntityType.Builder.create(CoralTurtleEntity::new, EntityClassification.CREATURE).size(1.2F, 0.4F)
					.build(new ResourceLocation(MoTurtlesMod.MOD_ID, "coral_turtle").toString()));

	public static final RegistryObject<EntityType<?>> RUSSIAN_TURTLE = ENTITIES.register("russian_turtle",
			() -> EntityType.Builder.create(RussianTurtleEntity::new, EntityClassification.CREATURE).size(1.2F, 0.4F)
					.build(new ResourceLocation(MoTurtlesMod.MOD_ID, "russian_turtle").toString()));

//	public static final RegistryObject<EntityType<?>> SNOW_TURTLE = TURTLES.register("snow_turtle",
//			() -> EntityType.Builder.create(SnowTurtleEntity::new, EntityClassification.CREATURE).size(1.2F, 0.4F)
//					.build(new ResourceLocation(MoTurtlesMod.MOD_ID, "snow_turtle").toString()));
}