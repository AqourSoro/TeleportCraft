package aqoursoro.teleportcraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import aqoursoro.teleportcraft.network.ModNetworkManager;
import aqoursoro.teleportcraft.recipes.smelting.SmeltingRecipes;
import aqoursoro.teleportcraft.util.IProxy;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import aqoursoro.teleportcraft.util.Reference;
import aqoursoro.teleportcraft.world.gen.ModWorldGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
		dependencies = Reference.DEPENDENCIES,
		certificateFingerprint = Reference.CERTIFICATE_FINGERPRINT
)
public class TeleportCraft 
{
	public static final Logger TELEPORTCRAFT_LOG = LogManager.getLogger(Reference.MOD_ID);
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Instance(Reference.MOD_ID)
	public static TeleportCraft instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void onPreInit(final FMLPreInitializationEvent event) 
	{
		LOGGER.debug("preInit");
		proxy.logPhysicalSide(TELEPORTCRAFT_LOG);
		//second arg here represents the priority of generator
		GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 3);
		new ModNetworkManager();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
		//Registry Capability here!

	}
	
	@EventHandler
	public void onInit(final FMLInitializationEvent event) 
	{
		LOGGER.debug("init");
	}
	
	@EventHandler
	public void onPostInit(final FMLPostInitializationEvent event) 
	{
		LOGGER.debug("postInit");
		SmeltingRecipes.initSmelting();
	}
	
	//validation of author
	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) 
	{
		TELEPORTCRAFT_LOG.warn("Invalid fingerprint detected! The file " + event.getSource().getName() 
				+ " may have been tampered with. This version will NOT be supported by the author!");
	}
}
