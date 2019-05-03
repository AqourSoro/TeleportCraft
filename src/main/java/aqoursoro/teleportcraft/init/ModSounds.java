package aqoursoro.teleportcraft.init;

import aqoursoro.teleportcraft.config.ModConfiguration;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSounds
{
	private static int size = 0;

	public static SoundEvent PORTAL_ENTER;
	public static SoundEvent PORTAL_EXIT;
	public static SoundEvent PORTAL_ERROR;


	public static void registerSounds()
	{
		size = SoundEvent.REGISTRY.getKeys().size();

		PORTAL_ENTER = getOrRegisterSound(ModConfiguration.soundEffectTeleporterEnter, "portal_enter");
		PORTAL_EXIT = getOrRegisterSound(ModConfiguration.soundEffectTeleporterExit, "portal_exit");
		PORTAL_ERROR = getOrRegisterSound(ModConfiguration.soundEffectTeleporterError, "portal_error");
	}


	private static SoundEvent getOrRegisterSound(String name, String defaultName)
	{
		if (name.equals(Reference.MOD_ID + ":" + defaultName))
		{
			return registerSound(defaultName);
		}
		else
		{
			return (SoundEvent)SoundEvent.REGISTRY.getObject(new ResourceLocation(name));
		}
	}

	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent event = new SoundEvent(location).setRegistryName(location);

		ForgeRegistries.SOUND_EVENTS.register(event);
//		SoundEvent.REGISTRY.register(size, location, event);
		size++;

		return event;
	}

}
