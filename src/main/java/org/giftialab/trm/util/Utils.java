package org.giftialab.trm.util;

import org.giftialab.trm.R;

import net.minecraft.util.ResourceLocation;

public class Utils {

	public static ResourceLocation location(String name) {
		return new ResourceLocation(R.MODID, name);
	}
	
}
