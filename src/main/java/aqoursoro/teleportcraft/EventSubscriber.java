package aqoursoro.teleportcraft;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import aqoursoro.teleportcraft.block.BlockHardOre;
import aqoursoro.teleportcraft.block.BlockMetal;
import aqoursoro.teleportcraft.block.BlockMythinium;
import aqoursoro.teleportcraft.block.BlockSoftOre;
import aqoursoro.teleportcraft.block.BlockTeleporter;
import aqoursoro.teleportcraft.block.BlockMythiniumX;
import aqoursoro.teleportcraft.block.cable.BlockElectricCable;
import aqoursoro.teleportcraft.block.machine.BlockBoardProducer;
import aqoursoro.teleportcraft.block.machine.BlockCompressor;
import aqoursoro.teleportcraft.block.machine.BlockCore;
import aqoursoro.teleportcraft.block.machine.BlockElectricGrinder;
import aqoursoro.teleportcraft.block.machine.BlockElectricSinteringFurnace;
import aqoursoro.teleportcraft.block.machine.BlockItemSite;
import aqoursoro.teleportcraft.block.machine.BlockStamper;
import aqoursoro.teleportcraft.block.machine.BlockTemplateProducer;
import aqoursoro.teleportcraft.block.machine.BlockThermalElectricGenerator;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModCapabilities;
import aqoursoro.teleportcraft.init.ModSounds;
import aqoursoro.teleportcraft.item.ItemIDChip;
import aqoursoro.teleportcraft.item.ItemMotherBoard;
import aqoursoro.teleportcraft.item.ItemIDTemplate;
import aqoursoro.teleportcraft.item.ItemChannelChip;
import aqoursoro.teleportcraft.item.ItemChannelTemplate;
import aqoursoro.teleportcraft.item.ItemGeneral;
import aqoursoro.teleportcraft.tileentity.TileEntityBoardProducer;
import aqoursoro.teleportcraft.tileentity.TileEntityCompressor;
import aqoursoro.teleportcraft.tileentity.TileEntityCore;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricCable;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricSinteringFurnace;
import aqoursoro.teleportcraft.tileentity.TileEntityItemSite;
import aqoursoro.teleportcraft.tileentity.TileEntityStamper;
import aqoursoro.teleportcraft.tileentity.TileEntityTeleporter;
import aqoursoro.teleportcraft.tileentity.TileEntityTemplateProducer;
import aqoursoro.teleportcraft.tileentity.TileEntityThermalElectricGenerator;
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
		registry.register(new BlockMythiniumX("mythinium_x_block"));
		registry.register(new BlockHardOre("mythinium_ore"));
		registry.register(new BlockHardOre("lead_ore"));
		registry.register(new BlockMetal("lead_block"));
		registry.register(new BlockSoftOre("copper_ore"));
		registry.register(new BlockMetal("copper_block"));
		registry.register(new BlockSoftOre("tin_ore"));
		registry.register(new BlockMetal("tin_block"));
		registry.register(new BlockElectricGrinder("electric_grinder"));
		registry.register(new BlockThermalElectricGenerator("thermal_electric_generator"));
		registry.register(new BlockElectricCable("iron_cable"));
		registry.register(new BlockElectricCable("copper_cable"));

		registry.register(new BlockStamper("stamper"));
		registry.register(new BlockBoardProducer("board_producer"));
		registry.register(new BlockTemplateProducer("template_producer"));
		registry.register(new BlockCompressor("compressor"));
		registry.register(new BlockElectricSinteringFurnace("electric_sintering_furnace"));
		
		registry.register(new BlockItemSite("item_site"));
		registry.register(new BlockTeleporter("teleporter"));
		registry.register(new BlockCore("core"));
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registered blocks");
		
		registerTileEntities();
		
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registrered tile entities");
		
		ModCapabilities.registerCapabilities();
		ModSounds.registerSounds();
		
		TeleportCraft.TELEPORTCRAFT_LOG.debug("Registered capabilities");
	}
	
	private static void registerTileEntities() 
	{
		registerTileEntity(TileEntityElectricGrinder.class, "electric_grinder");
		registerTileEntity(TileEntityThermalElectricGenerator.class, "thermal_electric_generator");
		registerTileEntity(TileEntityElectricCable.class, "electric_cable");
		registerTileEntity(TileEntityItemSite.class, "item_site");
		registerTileEntity(TileEntityTeleporter.class, "teleporter");
		registerTileEntity(TileEntityStamper.class, "stamper");
		registerTileEntity(TileEntityBoardProducer.class, "board_producer");
		registerTileEntity(TileEntityTemplateProducer.class, "template_producer");
		registerTileEntity(TileEntityCore.class, "core");
		registerTileEntity(TileEntityCompressor.class, "compressor");
		registerTileEntity(TileEntityElectricSinteringFurnace.class, "electric_sintering_furnace");
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
					ModBlocks.MYTHINIUM_X_BLOCK,
					ModBlocks.MYTHINIUM_ORE,
					ModBlocks.LEAD_ORE,
					ModBlocks.LEAD_BLOCK,
					ModBlocks.COPPER_BLOCK,
					ModBlocks.COPPER_ORE,
					ModBlocks.TIN_ORE,
					ModBlocks.TIN_BLOCK,
					ModBlocks.ELECTRIC_GRINDER,
					ModBlocks.THERMAL_ELECTRIC_GENERATOR,
					ModBlocks.TELEPORTER,
					ModBlocks.COPPER_CABLE,
					ModBlocks.IRON_CABLE,
					ModBlocks.ITEM_SITE,
					ModBlocks.BOARD_PRODUCER,
					ModBlocks.TEMPLATE_PRODUCER,
					ModBlocks.STAMPER,
					ModBlocks.CORE,
					ModBlocks.COMPRESSOR,
					ModBlocks.ELECTRIC_SINTERING_FURNACE
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
		
		registry.register(new ItemIDChip("id_chip"));
		registry.register(new ItemChannelChip("channel_chip"));
		registry.register(new ItemMotherBoard("mother_board"));
		registry.register(new ItemIDTemplate("id_template"));
		registry.register(new ItemChannelTemplate("channel_template"));
		
	}
	
	@SubscribeEvent
	public static void onAttachWorldCapabilities(final AttachCapabilitiesEvent<World> event) 
	{
		String nameSpace = org.apache.commons.lang3.StringUtils.isEmpty(Reference.MOD_ID) ? "minecraft" : Reference.MOD_ID.toLowerCase(Locale.ROOT);
		
		event.addCapability(new ResourceLocation(nameSpace, ModUtil.getRegistryNameForClass(CapabilityElectricEnergyNetManager.class, "Capability")), new ICapabilityProvider() 
		{

			private final ElectricEnergyNetManager energyNetworkList = new ElectricEnergyNetManager(event.getObject());

			@Override
			public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
				return capability == CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET;
			}

			@Override
			public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
				if (capability == CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET) {
					return (T) this.energyNetworkList;
				}
				return null;
			}
		});
	}
	
	@SubscribeEvent
	public static void onWorldTick(final WorldTickEvent event) 
	{
		if (event.phase != Phase.END) 
		{
			return;
		}

		final ElectricEnergyNetManager list = event.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null);
		if (list == null) 
		{
			return;
		}
		list.update();
	}

	
}
