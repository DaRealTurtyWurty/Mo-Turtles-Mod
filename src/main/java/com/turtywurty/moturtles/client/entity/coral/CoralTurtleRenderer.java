package com.turtywurty.moturtles.client.entity.coral;

import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleModel;
import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleRenderer;
import com.turtywurty.moturtles.common.entities.CoralTurtleEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CoralTurtleRenderer<T extends CoralTurtleEntity, M extends MoTurtlesTurtleModel<T>>
		extends MoTurtlesTurtleRenderer<T, M> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MoTurtlesMod.MOD_ID,
			"textures/entity/coral_turtle.png");
	private static final ResourceLocation DEAD_TEXTURE = new ResourceLocation(MoTurtlesMod.MOD_ID,
			"textures/entity/coral_dead_turtle.png");

	public CoralTurtleRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, (M) new KingCoralTurtleModel(0.0f));
		this.addLayer(new CoralRenderLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return entity.isCoralDead() ? DEAD_TEXTURE : TEXTURE;
	}
}
