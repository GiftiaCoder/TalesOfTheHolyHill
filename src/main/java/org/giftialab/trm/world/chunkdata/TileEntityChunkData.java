package org.giftialab.trm.world.chunkdata;

import org.giftialab.trm.tileentities.ModTileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityChunkData extends TileEntity {
	
	private ChunkData chunkData = new ChunkData();
	
	public TileEntityChunkData() {
		super(ModTileEntities.TILEENTITYTYPE_CHUNKDATA);
	}
	
	@Override
	public void setWorld(World worldIn) {
		super.setWorld(worldIn);
		this.chunkData.setWorld(worldIn);
	}
	@Override
	public void setPos(BlockPos posIn) {
		super.setPos(posIn);
		this.chunkData.setPos(posIn.getX() >> 4, posIn.getZ() >> 4);
	}

	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
		chunkData.read(compound);
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		chunkData.write(compound);
		return super.write(compound);
	}
	
	public ChunkData getChunkData() {
		return chunkData;
	}
	
}
