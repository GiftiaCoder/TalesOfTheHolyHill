package org.giftialab.trm;

import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.giftialab.trm.blocks.ModBlocks;
import org.giftialab.trm.items.ModItems;
import org.giftialab.trm.tileentities.ModTileEntities;
import org.giftialab.trm.util.Utils;
import org.giftialab.trm.world.Dims;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(R.MODID)
public class HolyHillMod {
	
	public static HolyHillMod instance;
	public static final Logger logger = LogManager.getLogger(R.MODID);
	
	public HolyHillMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistry);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void setup(final FMLCommonSetupEvent event) {
		DimensionManager.registerDimension(Dims.DIM_HOLYHILL_LOC, Dims.MODDIM_HOLYHILL, null);
	}

	public void clientRegistry(final FMLClientSetupEvent event) {
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class ModRegistryEvents {
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			event.getRegistry().register(ModItems.DIMTELE_HOLYHILL);
			event.getRegistry().register(ModItems.DIMTELE_OVERWORLD);
			event.getRegistry().register(getBlockItem(ModBlocks.BLOCK_CHUNKDATA, ItemGroup.TOOLS));
			
			event.getRegistry().register(new Item(new Item.Properties()) {
				public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
					if (worldIn.isRemote) {
						for (Entry<BlockPos, TileEntity> entry : worldIn.getChunk(playerIn.getPosition()).getTileEntityMap().entrySet()) {
							NBTTagCompound nbt = entry.getValue().getTileData();
							String dbgMsg = nbt.hasKey("dbg_msg") ? nbt.getString("dbg_msg") : "(null)";
							playerIn.sendMessage(new TextComponentString(String.format("%s,%s,%s", entry.getKey(), entry.getValue().getClass().getName(), dbgMsg)));
						}
					}
					return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
				}
			}.setRegistryName(Utils.location("chunk_data_debugger")));
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			event.getRegistry().register(ModBlocks.BLOCK_CHUNKDATA);
		}
		
		@SubscribeEvent
		public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(ModTileEntities.TILEENTITYTYPE_CHUNKDATA);
		}

		@SubscribeEvent
		public static void registerDimension(final RegistryEvent.Register<ModDimension> event) {
			event.getRegistry().register(Dims.MODDIM_HOLYHILL);
		}
	}
	
	public static Item getBlockItem(Block block, ItemGroup group) {
		return new ItemBlock(block, new Item.Properties().group(group)).setRegistryName(block.getRegistryName());
	}
	
}
