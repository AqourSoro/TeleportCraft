package aqoursoro.teleportcraft.init;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergy;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergyNetManager;
import aqoursoro.teleportcraft.capability.teleporter.CapabilityTeleportHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapabilities 
{
	@Nonnull
	public static void registerCapabilities()
	{
		CapabilityElectricEnergy.registerElectricEnergy();
		CapabilityMythiniumEnergy.registerMythiniumEnergy();
		CapabilityElectricEnergyNetManager.register();
		CapabilityMythiniumEnergyNetManager.register();

		CapabilityTeleportHandler.registerCapabilities();
	}
	
}
