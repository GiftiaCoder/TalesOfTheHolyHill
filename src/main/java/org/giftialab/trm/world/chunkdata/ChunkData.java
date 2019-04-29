package org.giftialab.trm.world.chunkdata;

import org.giftialab.trm.util.ChunkUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ChunkData {

	public static enum NeighbourIdx {
		NORTH_WEST(-1, 1), NORTH(0, 1), NORTH_EAST(1, 1),
		WEST(-1, 0), EAST(1, 0),
		SOUTH_WEST(-1, -1), SOUTH(0, -1), SOUTH_EAST(1, -1);
		
		private int offsetX, offsetZ;
		
		private NeighbourIdx(int offsetX, int offsetZ) {
			this.offsetX = offsetX;
			this.offsetZ = offsetZ;
		}
		
		public int getOffsetX() {
			return offsetX;
		}
		public int getOffsetZ() {
			return offsetZ;
		}
		public NeighbourIdx getOpposite() {
			return NeighbourIdx.values()[NeighbourIdx.values().length - ordinal()];
		}
	}
	
	private ChunkData[] neighbours = new ChunkData[NeighbourIdx.values().length];
	
	// set by TileEntityChunkData
	private World world;
	private int chunkX, chunkZ;
	
	public ChunkData getNeighbour(NeighbourIdx idx) {
		ChunkData data = this.neighbours[idx.ordinal()];
		if (data == null) {
			data = ChunkUtils.getChunkData(world, this.chunkX + idx.getOffsetX(), this.chunkZ + idx.getOffsetZ());
			if (data != null) {
				this.neighbours[idx.ordinal()] = data;
				data.neighbours[idx.getOpposite().ordinal()] = this;
			}
		}
		return data;
	}
	
	public void read(NBTTagCompound compound) {
		// TODO
	}
	
	public void write(NBTTagCompound compound) {
		// TODO
	}

	// just called by TileEntityChunkData
	public void setWorld(World world) {
		this.world = world;
	}
	public void setPos(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}
}
