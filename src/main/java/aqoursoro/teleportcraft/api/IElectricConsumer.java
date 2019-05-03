package aqoursoro.teleportcraft.api;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IElectricConsumer extends ICapabilitySerializable<NBTTagCompound>
{
	String ENERGY = "electric";
	
	@Nonnull
	ElectricEnergyStorage getEnergy();
	
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
