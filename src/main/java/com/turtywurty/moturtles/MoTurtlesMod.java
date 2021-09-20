package com.turtywurty.moturtles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.turtywurty.moturtles.core.init.DataSerializerInit;
import com.turtywurty.moturtles.core.init.EntityInit;
import com.turtywurty.moturtles.core.init.ItemInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoTurtlesMod.MOD_ID)
public class MoTurtlesMod {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "moturtles";

	public MoTurtlesMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		DataSerializerInit.SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		EntityInit.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {

	}
}
