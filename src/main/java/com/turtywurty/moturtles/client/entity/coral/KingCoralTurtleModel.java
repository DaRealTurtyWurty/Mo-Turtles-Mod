package com.turtywurty.moturtles.client.entity.coral;

import com.turtywurty.moturtles.client.entity.MoTurtlesTurtleModel;

public class KingCoralTurtleModel extends MoTurtlesTurtleModel {

	public KingCoralTurtleModel(float delta) {
		super(delta);
		this.headModel.setTextureOffset(116, 62).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 2.0F, 0.0F, 0.0F, false);
		this.headModel.setTextureOffset(116, 58).addBox(-3.0F, -3.0F, 2.0F, 6.0F, 2.0F, 0.0F, 0.0F, false);
		this.headModel.setTextureOffset(118, 55).addBox(3.0F, -3.0F, -3.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);
		this.headModel.setTextureOffset(118, 51).addBox(-3.0F, -3.0F, -3.0F, 0.0F, 2.0F, 5.0F, 0.0F, false);
	}
}