package org.giftialab.trm.world;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

public class HolyHillDim extends Dimension {

	public static final Biome HOLY_HILL_BIOME = new HolyHillBiome();
	private DimensionType type;
	
	public HolyHillDim(DimensionType type) {
		this.type = type;
		this.hasSkyLight = true;
	}
	
	@Override
	protected void init() {
	}

	@Override
	public IChunkGenerator<?> createChunkGenerator() {
		//return new ChunkGeneratorOverworld(getWorld(),
		//		new SingleBiomeProvider(new SingleBiomeProviderSettings().setBiome(Biomes.DESERT)),
		//		new OverworldGenSettings());
		return new HolyHillChunkGen(world,
				new SingleBiomeProvider(new SingleBiomeProviderSettings().setBiome(Biomes.THE_VOID)),
				new HolyHillChunkGenSettings());
	}

	@Override
	public BlockPos findSpawn(ChunkPos p_206920_1_, boolean checkValid) {
		return null;
	}

	@Override
	public BlockPos findSpawn(int p_206921_1_, int p_206921_2_, boolean checkValid) {
		return null;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		int i = (int)(worldTime % 24000L);
		float f = ((float)i + partialTicks) / 24000.0F - 0.25F;
		if (f < 0.0F) {
			++f;
		}
		
		if (f > 1.0F) {
			--f;
		}
		
		float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
		f = f + (f1 - f) / 3.0F;
		return f;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		//return new Vec3d(0.0, 0.0, 0.0);
		float f = MathHelper.cos(p_76562_1_ * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		float f1 = 0.7529412F;
		float f2 = 0.84705883F;
		float f3 = 1.0F;
		f1 = f1 * (f * 0.94F + 0.06F);
		f2 = f2 * (f * 0.94F + 0.06F);
		f3 = f3 * (f * 0.91F + 0.09F);
		return new Vec3d((double)f1, (double)f2, (double)f3);
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public DimensionType getType() {
		return type;
	}

}
