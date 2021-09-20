package com.turtywurty.moturtles.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTUtils {

	public static CompoundNBT writeVector3f(@Nonnull final CompoundNBT nbt, @Nullable Vector3f vector) {
		if (vector != null) {
			nbt.putFloat("X", vector.getX());
			nbt.putFloat("Y", vector.getY());
			nbt.putFloat("Z", vector.getZ());
		} else {
			nbt.putFloat("X", 0);
			nbt.putFloat("Y", 0);
			nbt.putFloat("Z", 0);
		}
		return nbt;
	}

	public static CompoundNBT writeVector3d(@Nonnull final CompoundNBT nbt, @Nullable Vector3d vector) {
		if (vector != null) {
			nbt.putDouble("X", vector.x);
			nbt.putDouble("Y", vector.y);
			nbt.putDouble("Z", vector.z);
		} else {
			nbt.putDouble("X", 0);
			nbt.putDouble("Y", 0);
			nbt.putDouble("Z", 0);
		}
		return nbt;
	}

	public static Vector3f readVector3f(@Nonnull final CompoundNBT nbt) {
		return new Vector3f(nbt.contains("X", NBT.TAG_FLOAT) ? nbt.getFloat("X") : 0f,
				nbt.contains("Y", NBT.TAG_FLOAT) ? nbt.getFloat("Y") : 0f,
				nbt.contains("Z", NBT.TAG_FLOAT) ? nbt.getFloat("Z") : 0f);
	}

	public static Vector3d readVector3d(@Nonnull final CompoundNBT nbt) {
		return new Vector3d(nbt.contains("X", NBT.TAG_DOUBLE) ? nbt.getDouble("X") : 0,
				nbt.contains("Y", NBT.TAG_DOUBLE) ? nbt.getDouble("Y") : 0,
				nbt.contains("Z", NBT.TAG_DOUBLE) ? nbt.getDouble("Z") : 0);
	}

	public static CompoundNBT writeTransformations(@Nonnull final List<Transformation> transformations) {
		CompoundNBT transformListCompound = new CompoundNBT();
		ListNBT transformListNBT = new ListNBT();
		transformListCompound.put("Transformations", transformListNBT);
		int compoundIndex = -1;
		for (Transformation t : transformations) {
			compoundIndex++;
			transformListNBT.add(compoundIndex, new CompoundNBT());
			
			CompoundNBT transformCompound = transformListNBT.getCompound(compoundIndex);
			transformCompound.put("Translation", NBTUtils.writeVector3d(new CompoundNBT(), t.getTranslation()));
			transformCompound.put("TypeX", NBTUtils.writeVector3f(new CompoundNBT(), t.getTypeX()));
			transformCompound.put("TypeY", NBTUtils.writeVector3f(new CompoundNBT(), t.getTypeY()));
			transformCompound.put("TypeZ", NBTUtils.writeVector3f(new CompoundNBT(), t.getTypeZ()));
			transformCompound.put("RotVals", NBTUtils.writeVector3d(new CompoundNBT(), t.getRotation()));
		}
		return transformListCompound;
	}

	public static List<Transformation> readTransformations(@Nonnull final CompoundNBT nbt) {
		List<Transformation> transformations = new ArrayList<Transformation>();
		ListNBT transformListNBT = nbt.getList("Transformations", NBT.TAG_COMPOUND);
		transformListNBT.forEach(tag -> {
			CompoundNBT transformCompound = transformListNBT.getCompound(transformListNBT.indexOf(tag));
			Vector3d translation = NBTUtils.readVector3d(transformCompound.getCompound("Translation"));
			Vector3f typeX = NBTUtils.readVector3f(transformCompound.getCompound("TypeX"));
			Vector3f typeY = NBTUtils.readVector3f(transformCompound.getCompound("TypeY"));
			Vector3f typeZ = NBTUtils.readVector3f(transformCompound.getCompound("TypeZ"));
			Vector3d rotVals = NBTUtils.readVector3d(transformCompound.getCompound("RotVals"));

			transformations.add(new Transformation(translation, rotVals, typeX, typeY, typeZ));
		});
		return transformations;
	}

	public static int[] writeBlockStates(@Nonnull final List<BlockState> states) {
		int[] stateIDs = new int[states.size()];
		for (int stateIndex = 0; stateIndex < states.size(); stateIndex++) {
			if (states.get(stateIndex) != null) {
				stateIDs[stateIndex] = Block.getStateId(states.get(stateIndex));
			} else {
				stateIDs[stateIndex] = 0;
			}
		}
		return stateIDs;
	}

	public static List<BlockState> readBlockStates(@Nonnull final int[] stateIDs) {
		List<BlockState> states = new ArrayList<BlockState>();
		for (int stateIndex = 0; stateIndex < stateIDs.length; stateIndex++) {
			states.add(Block.getStateById(stateIDs[stateIndex]));
		}
		return states;
	}
}
