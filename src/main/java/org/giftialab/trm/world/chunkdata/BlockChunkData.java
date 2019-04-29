package org.giftialab.trm.world.chunkdata;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockChunkData extends Block implements ITileEntityProvider {

	public BlockChunkData() {
		super(Block.Properties.from(net.minecraft.init.Blocks.BEDROCK));
	}

	/*@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new TileEntityChunkData();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}*/

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityChunkData();
	}
	
}
