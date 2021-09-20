package com.turtywurty.moturtles.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;

public abstract class MoTurtlesTurtleRenderer<T extends BetterTurtleEntity, M extends MoTurtlesTurtleModel<T>>
		extends MobRenderer<T, M> {
	public MoTurtlesTurtleRenderer(EntityRendererManager renderManagerIn, M model) {
		super(renderManagerIn, model, 0.7F);
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		if (entityIn.isChild()) {
			this.shadowSize *= 0.5F;
		}

		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}