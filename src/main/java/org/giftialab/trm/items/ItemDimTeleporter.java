package org.giftialab.trm.items;

import org.giftialab.trm.DimTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ItemDimTeleporter extends Item {

	private ResourceLocation dimension;
	
	public ItemDimTeleporter(ResourceLocation dimension) {
		super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
		this.dimension = dimension;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (playerIn instanceof EntityPlayerMP) {
			playerIn.getServer().getPlayerList().changePlayerDimension((EntityPlayerMP) playerIn,
					DimensionType.byName(dimension),
					new DimTeleporter(((EntityPlayerMP) playerIn).getServerWorld()));
			playerIn.setPositionAndUpdate(0, 128.0F, 0);
			
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
}
