package com.turtywurty.moturtles.client.entity.coral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.turtywurty.moturtles.MoTurtlesMod;
import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleModel;
import com.turtywurty.moturtles.common.entities.CoralTurtleEntity;
import com.turtywurty.moturtles.core.util.Transformation;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;

public class CoralRenderLayer<T extends CoralTurtleEntity> extends LayerRenderer<T, MoTurtlesTurtleModel<T>> {

	public CoralRenderLayer(IEntityRenderer<T, MoTurtlesTurtleModel<T>> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T turtle, float limbSwing,
			float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		int combinedOverlay = LivingRenderer.getPackedOverlay(turtle, 0.0F);
		List<BlockState> corals = turtle.getCorals();
		List<Transformation> transformations = new ArrayList<Transformation>(turtle.getTransformations());
		for (BlockState coral : corals) {
			Transformation transformation = transformations.get(0);
			Vector3d translation = transformation.getTranslation();
			if (translation == null) {

			} else {
				if (transformation.getRotation() == null) {

				} else {
					Vector3d rotation = transformation.getRotation();
					transformations.remove(transformation);
					matrixStackIn.push();
					matrixStackIn.translate(translation.x, translation.y, translation.z);
					if (transformation.getTypeX() != null && transformation.getTypeZ() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeX().rotationDegrees((float) rotation.x));
					}

					if (transformation.getTypeY() != null && transformation.getTypeZ() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeY().rotationDegrees((float) rotation.y));
					}

					if (transformation.getTypeZ() != null && transformation.getTypeZ() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeZ().rotationDegrees((float) rotation.z));
					}
					matrixStackIn.scale(0.75f, 0.75f, 0.75f);
					this.renderBlockState(coral, matrixStackIn, bufferIn, packedLightIn, combinedOverlay);
					matrixStackIn.pop();
				}
			}
		}

		List<BlockState> fans = turtle.getFans();
		List<Transformation> fanTransformations = new ArrayList<Transformation>(turtle.getFanTransformations());
		for (BlockState fan : fans) {
			Transformation transformation = fanTransformations.get(0);
			Vector3d translation = transformation.getTranslation();
			if (translation != null) {
				if (transformation.getRotation() == null) {

				} else {
					Vector3d rotation = transformation.getRotation();
					fanTransformations.remove(transformation);
					matrixStackIn.push();
					matrixStackIn.translate(translation.x, translation.y, translation.z);
					if (transformation.getTypeX() != null && transformation.getTypeX() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeX().rotationDegrees((float) rotation.x));
					}

					if (transformation.getTypeY() != null && transformation.getTypeY() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeY().rotationDegrees((float) rotation.y));
					}

					if (transformation.getTypeZ() != null && transformation.getTypeZ() != Transformation.FLOAT_ZERO) {
						matrixStackIn.rotate(transformation.getTypeZ().rotationDegrees((float) rotation.z));
					}
					matrixStackIn.scale(0.75f, 0.75f, 0.75f);
					this.renderBlockState(fan, matrixStackIn, bufferIn, packedLightIn, combinedOverlay);
					matrixStackIn.pop();
				}
			}
		}

		IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(MoTurtlesMod.MOD_ID,
				turtle.isCoralDead() ? "block/dead_orange_coral_lumps" : "block/orange_coral_lumps"));

		matrixStackIn.push();
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180f));
		matrixStackIn.translate(-0.6, -0.95, -0.375);
		matrixStackIn.scale(1.2f, 1.8f, 1.2f);
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(turtle.getEntityWorld(),
				model, Blocks.BUBBLE_CORAL_BLOCK.getDefaultState(), turtle.getPosition(), matrixStackIn, bufferIn.getBuffer(RenderType.getSolid()),
				true, turtle.getRNG(), turtle.getEntityWorld().getSeed(), combinedOverlay, EmptyModelData.INSTANCE);
		matrixStackIn.pop();
	}

	public static <K, V> HashMap<K, V> copy(HashMap<K, V> original) {
		HashMap<K, V> copy = new HashMap<K, V>();
		for (Map.Entry<K, V> entry : original.entrySet()) {
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}

	private void renderBlockState(BlockState state, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight,
			int combinedOverlay) {
		Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, stack, buffer, combinedLight,
				combinedOverlay, EmptyModelData.INSTANCE);
	}
}
