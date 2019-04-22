package aqoursoro.teleportcraft.capability.mythiniumenergy;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityMythiniumEnergy 
{
	@CapabilityInject(IMythiniumEnergy.class)
	public static final Capability<IMythiniumEnergy> MYTINIUM_ENERGY = null;
	
	
	public static void registerMythiniumEnergy()
	{
		CapabilityManager.INSTANCE.register(IMythiniumEnergy.class, new IStorage<IMythiniumEnergy>() 
		{

			@Override
			public NBTBase writeNBT(@Nonnull Capability<IMythiniumEnergy> capability, @Nonnull IMythiniumEnergy instance, @Nonnull EnumFacing side) 
			{
				return new NBTTagInt(instance.getEnergyStored());
			}

			@Override
			public void readNBT(@Nonnull Capability<IMythiniumEnergy> capability, @Nonnull IMythiniumEnergy instance, @Nonnull EnumFacing side, @Nonnull NBTBase nbt) 
			{
				if(!(instance instanceof ElectricEnergyStorage)) 
				{
					throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
				}
				((MythiniumEnergyStorage)instance).Energy = ((NBTTagInt)nbt).getInt();
				
			}
			
		}, 
		new Callable<IMythiniumEnergy>()
		{

			@Override
			public IMythiniumEnergy call() throws Exception 
			{
				return new MythiniumEnergyStorage(1000000);
			}
			
		});
	}
}
