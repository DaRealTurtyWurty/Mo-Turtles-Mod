package com.turtywurty.moturtles.core.init;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.core.util.TurtleDataSerializers;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DataSerializerInit {

	public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.DATA_SERIALIZERS, MoTurtlesMod.MOD_ID);

	public static final RegistryObject<DataSerializerEntry> LIST_BLOCKSTATE = SERIALIZERS.register("list_blockstate",
			() -> new DataSerializerEntry(TurtleDataSerializers.LIST_BLOCKSTATE));

	public static final RegistryObject<DataSerializerEntry> LIST_TRANSFORMATION = SERIALIZERS.register("list_transformation",
			() -> new DataSerializerEntry(TurtleDataSerializers.LIST_TRANSFORMATION));
}
