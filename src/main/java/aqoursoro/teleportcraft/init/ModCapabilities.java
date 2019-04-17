package aqoursoro.teleportcraft.init;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.IEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapabilities 
{
	@Nonnull
	public static void registerCapabilities()
	{
		CapabilityElectricEnergy.registerElectricEnergy();
	}
	
}
