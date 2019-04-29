package org.giftialab.trm.world;

import java.util.function.Function;

import org.giftialab.trm.R;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class Dims {

	public static final ResourceLocation DIM_HOLYHILL_LOC = new ResourceLocation(R.MODID, "dim_holyhill");
	public static final ResourceLocation DIM_OVERWORLD_LOC = new ResourceLocation("minecraft", "overworld");
	
	public static final ModDimension MODDIM_HOLYHILL = new ModDimension(){
		@Override
		public Function<DimensionType, ? extends Dimension> getFactory() {
			return HolyHillDim::new;
		}
	}.setRegistryName(DIM_HOLYHILL_LOC);
	
}
