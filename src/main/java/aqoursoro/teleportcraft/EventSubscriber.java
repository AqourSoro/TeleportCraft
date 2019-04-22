package aqoursoro.teleportcraft;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import aqoursoro.teleportcraft.block.BlockHardOre;
import aqoursoro.teleportcraft.block.BlockMythinium;
import aqoursoro.teleportcraft.block.BlockSoftOre;
import aqoursoro.teleportcraft.block.cable.BlockElectricCable;
import aqoursoro.teleportcraft.block.machine.BlockElectricGrinder;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModCapabilities;
import aqoursoro.teleportcraft.item.ItemGeneral;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricCable;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
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
		registry.register(new BlockHardOre("lead_ore"));
		registry.register(new BlockSoftOre("copper_ore"));
		registry.register(new BlockSoftOre("tin_ore"));
		registry.register(new BlockElectricGrinder("electric_grinder"));
		registry.register(new BlockElectricCable("iron_cable"));
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registered blocks");
		
		registerTileEntities();
		
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registrered tile entities");
		
		ModCapabilities.registerCapabilities();
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registered capabilities");
	}
	
	private static void registerTileEntities() 
	{
		registerTileEntity(TileEntityElectricGrinder.class, "electric_grinder");
		registerTileEntity(TileEntityElectricCable.class, "iron_cable");
	}
	
	private static void registerTileEntity(@Nonnull final Class<? extends TileEntity> clazz, String name)
	{
		try 
		{
			GameRegistry.registerTileEntity(clazz, new ResourceLocation(Reference.MOD_ID, name));
		} 
		catch (final Exception exception) 
		{
			CrashReport crashReport = new CrashReport("Error registering Tile Entity " + clazz.getSimpleName(), exception);
			crashReport.makeCategory("Registering Tile Entity");
			throw new ReportedException(crashReport);
		}
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
					ModBlocks.LEAD_ORE,
					ModBlocks.COPPER_ORE,
					ModBlocks.TIN_ORE,
					ModBlocks.ELECTRIC_GRINDER,
					ModBlocks.IRON_CABLE
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
		registry.register(new ItemGeneral("mythinium_ingot"));
		registry.register(new ItemGeneral("mythinium_ore_powder"));
		registry.register(new ItemGeneral("mythinium_powder"));
		
		registry.register(new ItemGeneral("copper_ingot"));
		registry.register(new ItemGeneral("copper_ore_powder"));
		registry.register(new ItemGeneral("copper_powder"));
		
		registry.register(new ItemGeneral("lead_ingot"));
		registry.register(new ItemGeneral("lead_ore_powder"));
		registry.register(new ItemGeneral("lead_powder"));
		
		registry.register(new ItemGeneral("tin_ingot"));
		registry.register(new ItemGeneral("tin_ore_powder"));
		registry.register(new ItemGeneral("tin_powder"));
	}
	
	
	
}
