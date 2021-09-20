package com.turtywurty.moturtles.core.util;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

public class TurtleDataSerializers {

	public static final IDataSerializer<List<BlockState>> LIST_BLOCKSTATE = new IDataSerializer<List<BlockState>>() {
		public void write(PacketBuffer buf, List<BlockState> states) {
			buf.writeVarIntArray(NBTUtils.writeBlockStates(states));
		}

		public List<BlockState> read(PacketBuffer buf) {
			return NBTUtils.readBlockStates(buf.readVarIntArray());
		}

		public List<BlockState> copyValue(List<BlockState> value) {
			return value;
		}
	};

	public static final IDataSerializer<List<Transformation>> LIST_TRANSFORMATION = new IDataSerializer<List<Transformation>>() {
		public void write(PacketBuffer buf, List<Transformation> transformations) {
			buf.writeCompoundTag(NBTUtils.writeTransformations(transformations));
		}

		public List<Transformation> read(PacketBuffer buf) {
			return NBTUtils.readTransformations(buf.readCompoundTag());
		}

		public List<Transformation> copyValue(List<Transformation> value) {
			return value;
		}
	};
}
