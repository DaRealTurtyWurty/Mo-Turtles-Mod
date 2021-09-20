package com.turtywurty.moturtles.core.init;

import com.turtywurty.moturtles.MoTurtlesMod;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoTurtlesMod.MOD_ID);

	public static final RegistryObject<Item> VODKA = ITEMS.register("vodka",
			() -> new Item(new Item.Properties().maxStackSize(16)
					.food(new Food.Builder().hunger(2).saturation(2f).setAlwaysEdible()
							.effect(() -> new EffectInstance(Effects.SPEED, 7000, 4, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.HASTE, 3000, 3, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.JUMP_BOOST, 3000, 6, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.GLOWING, 3000, 20, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.LUCK, 6000, 15, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.SLOW_FALLING, 2000, 2, false, false), 1f)
							.effect(() -> new EffectInstance(Effects.SATURATION, 3000, 10, false, false), 0.05f)
							.effect(() -> new EffectInstance(Effects.NAUSEA, 12000, 4, false, false), 1f).build())));
}
