package com.turtywurty.moturtles.client.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.turtywurty.moturtles.common.entities.BetterTurtleEntity;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoTurtlesTurtleModel<T extends BetterTurtleEntity> extends QuadrupedModel<T> {
	private final ModelRenderer pregnant;

	public MoTurtlesTurtleModel(float delta) {
		super(12, delta, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.headModel = new ModelRenderer(this, 3, 0);
		this.headModel.addBox(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
		this.headModel.setRotationPoint(0.0F, 19.0F, -10.0F);
		this.body = new ModelRenderer(this);
		this.body.setTextureOffset(7, 37).addBox(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
		this.body.setTextureOffset(31, 1).addBox(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
		this.body.setRotationPoint(0.0F, 11.0F, -10.0F);
		this.pregnant = new ModelRenderer(this);
		this.pregnant.setTextureOffset(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
		this.pregnant.setRotationPoint(0.0F, 11.0F, -10.0F);
		this.legBackRight = new ModelRenderer(this, 1, 23);
		this.legBackRight.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
		this.legBackRight.setRotationPoint(-3.5F, 22.0F, 11.0F);
		this.legBackLeft = new ModelRenderer(this, 1, 12);
		this.legBackLeft.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
		this.legBackLeft.setRotationPoint(3.5F, 22.0F, 11.0F);
		this.legFrontRight = new ModelRenderer(this, 27, 30);
		this.legFrontRight.addBox(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
		this.legFrontRight.setRotationPoint(-5.0F, 21.0F, -4.0F);
		this.legFrontLeft = new ModelRenderer(this, 27, 24);
		this.legFrontLeft.addBox(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
		this.legFrontLeft.setRotationPoint(5.0F, 21.0F, -4.0F);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.pregnant));
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
		this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float) Math.PI) * 0.5F
				* limbSwingAmount;
		this.legFrontRight.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float) Math.PI) * 0.5F
				* limbSwingAmount;
		this.legFrontLeft.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
		this.legFrontRight.rotateAngleX = 0.0F;
		this.legFrontLeft.rotateAngleX = 0.0F;
		this.legFrontRight.rotateAngleY = 0.0F;
		this.legFrontLeft.rotateAngleY = 0.0F;
		this.legBackRight.rotateAngleY = 0.0F;
		this.legBackLeft.rotateAngleY = 0.0F;
		this.pregnant.rotateAngleX = ((float) Math.PI / 2F);
		if (!entityIn.isInWater() && entityIn.onGround) {
			float f = entityIn.isDigging() ? 4.0F : 1.0F;
			float f1 = entityIn.isDigging() ? 2.0F : 1.0F;
			this.legFrontRight.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F + (float) Math.PI) * 8.0F
					* limbSwingAmount * f1;
			this.legFrontRight.rotateAngleZ = 0.0F;
			this.legFrontLeft.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F) * 8.0F * limbSwingAmount * f1;
			this.legFrontLeft.rotateAngleZ = 0.0F;
			this.legBackRight.rotateAngleY = MathHelper.cos(limbSwing * 5.0F + (float) Math.PI) * 3.0F
					* limbSwingAmount;
			this.legBackRight.rotateAngleX = 0.0F;
			this.legBackLeft.rotateAngleY = MathHelper.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount;
			this.legBackLeft.rotateAngleX = 0.0F;
		}

		this.pregnant.showModel = !this.isChild && entityIn.hasEgg();
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		boolean showsPregnant = this.pregnant.showModel;
		if (showsPregnant) {
			matrixStackIn.push();
			matrixStackIn.translate(0.0D, (double) -0.08F, 0.0D);
		}

		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		if (showsPregnant) {
			matrixStackIn.pop();
		}
	}
}