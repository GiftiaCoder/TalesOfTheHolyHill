package org.giftialab.trm;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DimTeleporter extends Teleporter {

	public DimTeleporter(WorldServer worldIn) {
		super(worldIn);
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		entityIn.motionX = 0f;
		entityIn.motionY = 0f;
		entityIn.motionZ = 0f;
	}
	
}