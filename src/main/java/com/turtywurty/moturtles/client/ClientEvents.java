package com.turtywurty.moturtles.client;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.client.entity.coral.CoralTurtleRenderer;
import com.turtywurty.moturtles.client.entity.russian.RussianTurtleRenderer;
import com.turtywurty.moturtles.core.init.EntityInit;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MoTurtlesMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent event) {
		bindRenderer(EntityInit.CORAL_TURTLE, CoralTurtleRenderer::new);
		bindRenderer(EntityInit.RUSSIAN_TURTLE, RussianTurtleRenderer::new);

		ModelLoader.addSpecialModel(new ResourceLocation(MoTurtlesMod.MOD_ID, "block/orange_coral_lumps"));
		ModelLoader.addSpecialModel(new ResourceLocation(MoTurtlesMod.MOD_ID, "block/dead_orange_coral_lumps"));
	}

	private static void bindRenderer(RegistryObject<EntityType<?>> entity, IRenderFactory renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entity.get(), renderFactory);
	}
}
