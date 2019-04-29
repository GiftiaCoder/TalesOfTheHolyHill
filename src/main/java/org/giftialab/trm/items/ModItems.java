package org.giftialab.trm.items;

import org.giftialab.trm.util.Utils;
import org.giftialab.trm.world.Dims;

import net.minecraft.item.Item;

public class ModItems {

	public static final Item DIMTELE_HOLYHILL = new ItemDimTeleporter(Dims.DIM_HOLYHILL_LOC).setRegistryName(Utils.location("dimtele_holyhill"));
	public static final Item DIMTELE_OVERWORLD = new ItemDimTeleporter(Dims.DIM_OVERWORLD_LOC).setRegistryName(Utils.location("dimtele_overworld"));
	
}
