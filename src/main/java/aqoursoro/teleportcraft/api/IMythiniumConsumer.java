package aqoursoro.teleportcraft.api;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.mythiniumenergy.MythiniumEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IMythiniumConsumer extends ICapabilitySerializable<NBTTagCompound>
{
	String ENERGY = "mythinium";
	
	@Nonnull
	MythiniumEnergyStorage getEnergy();
	
	@Override
	default NBTTagCompound serializeNBT() 
	{
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger(ENERGY, getEnergy().getEnergyStored());
		return compound;
	}

	@Override
	default void deserializeNBT(final NBTTagCompound compound) 
	{
		if (compound.hasKey(ENERGY)) 
		{
			this.getEnergy().setEnergyStored(compound.getInteger(ENERGY), false);
		}
	}
}
