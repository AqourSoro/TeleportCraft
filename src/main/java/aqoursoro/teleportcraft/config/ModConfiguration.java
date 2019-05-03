package aqoursoro.teleportcraft.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import aqoursoro.teleportcraft.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public final class ModConfiguration
{

	private static Configuration config = null;

	public static final String CATEGORY_SOUNDS = "sounds";

	public static boolean useDiamonds = true;
	public static int numTeleporters = 1;
	public static boolean teleportPassiveMobs = true;
	public static boolean teleportHostileMobs = true;

	public static String soundEffectTeleporterEnter = Reference.MOD_ID + ":portal_enter";
	public static String soundEffectTeleporterExit = Reference.MOD_ID + ":portal_exit";
	public static String soundEffectTeleporterError = Reference.MOD_ID + ":portal_error";


	public static void preInit()
	{
		File configFile = new File(Loader.instance().getConfigDir(), Reference.MOD_ID + ".cfg");

		config = new Configuration(configFile);

		config.setCategoryRequiresMcRestart(Configuration.CATEGORY_GENERAL, true);
		config.load();


		config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "Vanilla-Inspired Teleporters Version " + Reference.VERSION + " Configuration");
		Property propUseDiamonds = config.get(Configuration.CATEGORY_GENERAL, "useDiamonds", useDiamonds, "If false, removes diamonds from the crafting recipe and replaces them with quartz blocks.\nDefault is true");
		Property propNumTeleporters = config.get(Configuration.CATEGORY_GENERAL, "numTeleporters", numTeleporters, "Specifies the number of teleporters created with a single recipe.\nDefault is 1");
		Property propTeleportPassiveMobs = config.get(Configuration.CATEGORY_GENERAL, "teleportPassiveMobs", teleportPassiveMobs, "Specifies whether or not passive mobs can go through teleporters.\nDefault is true");
		Property propTeleportHostileMobs = config.get(Configuration.CATEGORY_GENERAL, "teleportHostileMobs", teleportHostileMobs, "Specifies whether or not hostile mobs can go through teleporters.\nDefault is true");

		config.addCustomCategoryComment(ModConfiguration.CATEGORY_SOUNDS, "See http://minecraft.gamepedia.com/Sounds.json#Sound_events for a list of vanilla sound effects");
		Property propSoundEffectTeleporterEnter = config.get(ModConfiguration.CATEGORY_SOUNDS, "soundEffectTeleporterEnter", soundEffectTeleporterEnter, "Sound effect to play when an entity enters a teleporter.\nDefault is \"" + Reference.MOD_ID + ":portal_enter\", leave blank for no sound.");
		Property propSoundEffectTeleporterExit = config.get(ModConfiguration.CATEGORY_SOUNDS, "soundEffectTeleporterExit", soundEffectTeleporterExit, "Sound effect to play when an entity exits a teleporter.\nDefault is \"" + Reference.MOD_ID + ":portal_exit\", leave blank for no sound.");
		Property propSoundEffectTeleporterError = config.get(ModConfiguration.CATEGORY_SOUNDS, "soundEffectTeleporterError", soundEffectTeleporterError, "Sound effect to play when a teleporter cannot be used.\nDefault is \"" + Reference.MOD_ID + ":portal_error\", leave blank for no sound.");


		List<String> propOrderGeneral = new ArrayList<String>();
		propOrderGeneral.add(propUseDiamonds.getName());
		propOrderGeneral.add(propNumTeleporters.getName());
		propOrderGeneral.add(propTeleportPassiveMobs.getName());
		propOrderGeneral.add(propTeleportHostileMobs.getName());
		config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propOrderGeneral);

		List<String> propOrderSounds = new ArrayList<String>();
		propOrderSounds.add(propSoundEffectTeleporterEnter.getName());
		propOrderSounds.add(propSoundEffectTeleporterExit.getName());
		propOrderSounds.add(propSoundEffectTeleporterError.getName());
		config.setCategoryPropertyOrder(ModConfiguration.CATEGORY_SOUNDS, propOrderSounds);


		useDiamonds = propUseDiamonds.getBoolean();
		numTeleporters = propNumTeleporters.getInt();
		teleportPassiveMobs = propTeleportPassiveMobs.getBoolean();
		teleportHostileMobs = propTeleportHostileMobs.getBoolean();

		soundEffectTeleporterEnter = propSoundEffectTeleporterEnter.getString();
		soundEffectTeleporterExit = propSoundEffectTeleporterExit.getString();
		soundEffectTeleporterError = propSoundEffectTeleporterError.getString();


		if (config.hasChanged()) config.save();
	}

}