package org.giftialab.trm.tileentities;

import org.giftialab.trm.util.Utils;
import org.giftialab.trm.world.chunkdata.TileEntityChunkData;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;

public class ModTileEntities {

	public static final TileEntityType<TileEntityChunkData> TILEENTITYTYPE_CHUNKDATA = register("chunk_data", TileEntityType.Builder.create(TileEntityChunkData::new));
	
	public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder) {
		Type<?> type = null;
		
		try {
			type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1517)).getChoiceType(TypeReferences.BLOCK_ENTITY, id);
		} catch (IllegalArgumentException illegalstateexception) {
			if (SharedConstants.developmentMode) {
				throw illegalstateexception;
			}
		}
		
		TileEntityType<T> tileentitytype = builder.build(type);
		tileentitytype.setRegistryName(Utils.location(id));
		return tileentitytype;
	}

}
