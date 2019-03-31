package aqoursoro.teleportcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import aqoursoro.teleportcraft.block.BlockHardOre;
import aqoursoro.teleportcraft.block.BlockMythinium;
import aqoursoro.teleportcraft.block.BlockSoftOre;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.util.ModUtil;
import aqoursoro.teleportcraft.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventSubscriber 
{
	
	private static int entityId = 0;
	
	@SubscribeEvent
	public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event)
	{
		final IForgeRegistry<Block> registry = event.getRegistry();
		
		//Registry blocks here;
		registry.register(new BlockMythinium("mythinium_block"));
		registry.register(new BlockHardOre("mythinium_ore"));
		registry.register(new BlockSoftOre("copper_ore"));
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registered blocks");
		
		registerTileEntities();
		
		//Registry tileEntities here;
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registrered tile entities");
	}
	
	private static void registerTileEntities()
	{
		//implement here
	}
	
	@SubscribeEvent
	public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event)
	{
		final IForgeRegistry<Item> registry = event.getRegistry();
		//implement here
		Arrays.stream(new Block[] 
				{
					ModBlocks.MYTHINIUM_BLOCK,
					ModBlocks.MYTHINIUM_ORE,
					ModBlocks.COPPER_ORE
				}).forEach(block -> {
					Preconditions.checkNotNull(block.getRegistryName(), "Registry name cannot be null!");
					registry.register(
							ModUtil.setCreativeTab( // set it's creative tab to our creativetab (Optional)
									ModUtil.setRegistryNames( //set its name
											new ItemBlock(block), //make the itemblock
											block.getRegistryName())
							)
					);
				});
	}
}
