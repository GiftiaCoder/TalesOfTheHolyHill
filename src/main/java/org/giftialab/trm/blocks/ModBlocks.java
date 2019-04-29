package org.giftialab.trm.blocks;

import org.giftialab.trm.util.Utils;
import org.giftialab.trm.world.chunkdata.BlockChunkData;

import net.minecraft.block.Block;

public class ModBlocks {

	public static final Block BLOCK_CHUNKDATA = new BlockChunkData().setRegistryName(Utils.location("chunk_data"));
	
}
