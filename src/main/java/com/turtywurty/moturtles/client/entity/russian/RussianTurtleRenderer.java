package com.turtywurty.moturtles.client.entity.russian;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleModel;
import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleRenderer;
import com.turtywurty.moturtles.common.entities.RussianTurtleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RussianTurtleRenderer<T extends RussianTurtleEntity, M extends MoTurtlesTurtleModel<T>>
		extends MoTurtlesTurtleRenderer<T, MoTurtlesTurtleModel<T>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MoTurtlesMod.MOD_ID,
			"textures/entity/russian_turtle.png");

	public RussianTurtleRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new MoTurtlesTurtleModel<T>(0.0f));
		//this.addLayer(new VodkaBottleLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return TEXTURE;
	}
}
