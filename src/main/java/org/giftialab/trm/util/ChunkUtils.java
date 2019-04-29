package org.giftialab.trm.util;

import org.giftialab.trm.world.chunkdata.ChunkData;
import org.giftialab.trm.world.chunkdata.TileEntityChunkData;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkUtils {
	
	public static ChunkData getChunkData(World world, int x, int z) {
		if (world.isChunkLoaded(x, z, false)) {
			Chunk chunk = world.getChunk(x, z);
			TileEntity tileEntity = chunk.getTileEntity(new BlockPos(x << 4, 0, z << 4));
			if (tileEntity != null && tileEntity instanceof TileEntityChunkData) {
				return ((TileEntityChunkData) tileEntity).getChunkData();
			}
		}
		return null;
	}
	
	public static ChunkData getChunkData(World world, BlockPos pos) {
		return getChunkData(world, pos.getX() >> 4, pos.getZ() >> 4);
	}
	
}
