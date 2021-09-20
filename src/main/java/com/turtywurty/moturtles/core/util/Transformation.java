package com.turtywurty.moturtles.core.util;

import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.Vector3f;

public class Transformation {

	private Vector3d rotation, translation;
	private Vector3f typeX, typeY, typeZ;
	public static final Transformation NONE;
	public static final Vector3f FLOAT_ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
	public static final Vector3d DOUBLE_ZERO = new Vector3d(0.0, 0.0, 0.0);

	static {
		NONE = new Transformation(Transformation.DOUBLE_ZERO, Transformation.DOUBLE_ZERO, Transformation.FLOAT_ZERO,
				Transformation.FLOAT_ZERO, Transformation.FLOAT_ZERO);
	}

	public Transformation(Vector3d translationIn, Vector3d rotationIn, Vector3f typeXIn, Vector3f typeYIn,
			Vector3f typeZIn) {
		this.rotation = rotationIn;
		this.translation = translationIn;
		this.typeX = typeXIn;
		this.typeY = typeYIn;
		this.typeZ = typeZIn;
	}

	public Vector3d getRotation() {
		return this.rotation;
	}

	public Vector3d getTranslation() {
		return this.translation;
	}

	public Vector3f getTypeX() {
		return this.typeX;
	}

	public Vector3f getTypeY() {
		return this.typeY;
	}

	public Vector3f getTypeZ() {
		return this.typeZ;
	}

	@Override
	public String toString() {
		return "Translation: " + ("(X: "
				+ this.getTranslation().x + ", Y: " + this.getTranslation().y + ", Z: " + this.getTranslation().z + ")")
				+ ", Rot:"
				+ (this.getRotation() != null
						? ("(X: " + this.getRotation().x + ", Y: " + this.getRotation().y + ", Z: " + this.getRotation().z
								+ ")")
						: "null") + ", Type X: "
								+ ("(X: " + this.getTypeX().getX() + ", Y: " + this.getTypeX().getY() + ", Z: "
										+ this.getTypeX().getZ() + ")")
								+ ", Type Y: "
								+ ("(X: " + this.getTypeY().getX() + ", Y: " + this.getTypeY().getY() + ", Z: "
										+ this.getTypeY().getZ() + ")")
								+ ", Type Z: " + ("(X: " + this.getTypeZ().getX() + ", Y: " + this.getTypeZ().getY()
										+ ", Z: " + this.getTypeZ().getZ() + ")");
	}
}
