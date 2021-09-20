package com.turtywurty.moturtles.common;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.common.entities.CoralTurtleEntity;

import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = MoTurtlesMod.MOD_ID, bus = Bus.FORGE)
public class EventHandler {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onNoAISpawn(LivingSpawnEvent event) {
		if (event.getEntityLiving() instanceof CoralTurtleEntity) {
			if (((CoralTurtleEntity) event.getEntityLiving()).getTransformations().size() < 5) {
				((CoralTurtleEntity) event.getEntityLiving()).onInitialSpawn(event.getWorld(),
						event.getWorld().getDifficultyForLocation(new BlockPos(event.getX(), event.getY(), event.getZ())),
						SpawnReason.COMMAND, (ILivingEntityData) null, (CompoundNBT) null);
			}
		}
	}
}
