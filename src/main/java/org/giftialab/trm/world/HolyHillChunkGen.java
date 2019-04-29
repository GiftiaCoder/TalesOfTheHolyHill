package org.giftialab.trm.world;

import java.util.List;

import javax.vecmath.Vector2f;

import org.giftialab.trm.algo.PathNoise;
import org.giftialab.trm.algo.PerlinNoise;
import org.giftialab.trm.blocks.ModBlocks;
import org.giftialab.trm.tileentities.ModTileEntities;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.AbstractChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

public class HolyHillChunkGen extends AbstractChunkGenerator<HolyHillChunkGenSettings> {

	private HolyHillChunkGenSettings settings;
	private static List<SpawnListEntry> EMPTY_SPAWN_LIST = Lists.newArrayList();
	private PerlinNoise perlineNoise;
	private PathNoise pathNoise;

	private static final IBlockState BEDROCK_STATE = Blocks.BEDROCK.getDefaultState();
	private static final IBlockState STONE_STATE = Blocks.STONE.getDefaultState();
	private static final IBlockState COBBLESTONE_STATE = Blocks.COBBLESTONE.getDefaultState();
	private static final IBlockState COBBLESTONE_SLAB_STATE = Blocks.COBBLESTONE_SLAB.getDefaultState();
	private static final IBlockState GRASS_PATH_STATE = Blocks.GRASS_PATH.getDefaultState();
	private static final IBlockState DIRT_STATE = Blocks.DIRT.getDefaultState();
	private static final IBlockState GRASS_BLOCK_STATE = Blocks.GRASS_BLOCK.getDefaultState();
	
	public HolyHillChunkGen(IWorld worldIn, BiomeProvider biomeProviderIn, HolyHillChunkGenSettings settings) {
		super(worldIn, biomeProviderIn);
		this.settings = settings;
		perlineNoise = new PerlinNoise(0.6F, 4, worldIn.getSeed());
		pathNoise = new PathNoise(worldIn.getSeed());
	}
	
	@Override
	public void makeBase(IChunk chunkIn) {
		ChunkPos chunkPos = chunkIn.getPos();
		int x = chunkPos.x << 4, z = chunkPos.z << 4;
		
		Biome[] abiome = this.biomeProvider.getBiomeBlock(x * 16, z * 16, 16, 16);
		chunkIn.setBiomes(abiome);
		
		MutableBlockPos pos = new MutableBlockPos();
		Vector2f pathVec = new Vector2f();
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				int px = x + i, pz = z + j;
				float dist = (float) Math.sqrt(px * px + pz * pz);
				
				chunkIn.setBlockState(pos.setPos(i, 0, j), BEDROCK_STATE, false);
				
				int base_height = (int) (perlineNoise.perlinNoise(px / 128.0F, pz / 128.0F) * 128.0F * 8.0F * 32.0F / (128.0F + dist));
				int height = Math.min(base_height, 72);
				height = fillChunk(i, j, 1, height, STONE_STATE, pos, chunkIn);
				/*if (height >= 68) {
					height = fillChunk(i, j, height, 74, Blocks.STONE_BRICKS.getDefaultState(), pos, chunkIn);
				} else if (height >= 62) {
					height = fillChunk(i, j, height, 68, Blocks.STONE_BRICKS.getDefaultState(), pos, chunkIn);
				} else if (height >= 56) {
					height = fillChunk(i, j, height, 62, Blocks.STONE_BRICKS.getDefaultState(), pos, chunkIn);
				} else {*/
				if (true) {
				//} else if (/*country side wall*/) {
					
				//} else if (/*country side*/) {
					pathNoise.pathNoise(px / 64.F + 0.5F, pz / 64.F + 0.5F, 1.5F, pathVec);
					float absX = Math.abs(pathVec.x), absZ = Math.abs(pathVec.y);
					if (((absX < 0.15F && absZ < 0.8F) ||
							(absX < 0.8F && absZ < 0.15F)) && height < 68) {
						if (absX < 0.1F || absZ < 0.1F) {
							height = fillChunk(i, j, height, height + 2, DIRT_STATE, pos, chunkIn);
							if (world.getRandom().nextFloat() < 0.5F) {
								height = fillChunk(i, j, height, height + 1, GRASS_PATH_STATE, pos, chunkIn);
							} else {
								height = fillChunk(i, j, height, height + 1, Blocks.GRAVEL.getDefaultState(), pos, chunkIn);
							}
						} else {
							height = fillChunk(i, j, height, height + 4, COBBLESTONE_STATE, pos, chunkIn);
							if (world.getRandom().nextFloat() < 0.66F) {
								chunkIn.setBlockState(pos.setPos(i, height, j), COBBLESTONE_SLAB_STATE, false);
							}
							//height = fillChunk(i, j, height, height + 1, COBBLESTONE_SLAB_STATE, pos, chunkIn);
						}
					} else {
						height = fillChunk(i, j, height, height + 3, DIRT_STATE, pos, chunkIn);
						height = fillChunk(i, j, height, height + 1, GRASS_BLOCK_STATE, pos, chunkIn);
					}
				} /*else if (outer city wall) {
					
				} else if (outer city) {
					
				} else if (inner city wall) {
					
				} else if (inner city) {
					
				} else if (palace wall) {
					
				} else if (palace) {
					
				}*/
				
				if (i == 0 || i == 15 || j == 0 || j == 15) {
					chunkIn.setBlockState(pos.setPos(i, height - 1, j), Blocks.GOLD_BLOCK.getDefaultState(), false);
				}
			}
		}
		
		chunkIn.setBlockState(pos.setPos(0, 0, 0), ModBlocks.BLOCK_CHUNKDATA.getDefaultState(), false);
		TileEntity tileEntity = ModTileEntities.TILEENTITYTYPE_CHUNKDATA.create();
		tileEntity.getTileData().setString("dbg_msg", String.format("chunk pos: (%d, %d)", chunkPos.x, chunkPos.z));
		chunkIn.addTileEntity(pos.setPos(x, 0, z), tileEntity);
		
		chunkIn.createHeightMap(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
		chunkIn.setStatus(ChunkStatus.BASE);
	}
	
	private static int fillChunk(int x, int z, int hf, int ht, IBlockState state, MutableBlockPos posTmp, IChunk chunkIn) {
		while (hf < ht) {
			chunkIn.setBlockState(posTmp.setPos(x, hf, z), state, false);
			++hf;
		}
		return hf;
	}

	@Override
	public void spawnMobs(WorldGenRegion region) {}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return EMPTY_SPAWN_LIST;
	}

	@Override
	public int spawnMobs(World worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
		return 0;
	}

	@Override
	public int getGroundHeight() {
		return 1;
	}

	@Override
	public HolyHillChunkGenSettings getSettings() {
		return this.settings;
	}

	@Override
	public double[] generateNoiseRegion(int x, int z) {
		return new double[0];
	}

}
