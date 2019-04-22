package aqoursoro.teleportcraft.capability.electricenergy;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityElectricEnergy 
{
	@CapabilityInject(IElectricEnergy.class)
	public static final Capability<IElectricEnergy> ELECTRIC_ENERGY = null;
	
	
	public static void registerElectricEnergy()
	{
		CapabilityManager.INSTANCE.register(IElectricEnergy.class, new IStorage<IElectricEnergy>() 
		{

			@Override
			public NBTBase writeNBT(@Nonnull Capability<IElectricEnergy> capability, @Nonnull IElectricEnergy instance, @Nonnull EnumFacing side) 
			{
				return new NBTTagInt(instance.getEnergyStored());
			}

			@Override
			public void readNBT(@Nonnull Capability<IElectricEnergy> capability, @Nonnull IElectricEnergy instance, @Nonnull EnumFacing side, @Nonnull NBTBase nbt) 
			{
				if(!(instance instanceof ElectricEnergyStorage)) 
				{
					throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
				}
				((ElectricEnergyStorage)instance).Energy = ((NBTTagInt)nbt).getInt();
				
			}
			
		}, 
		new Callable<IElectricEnergy>()
		{

			@Override
			public IElectricEnergy call() throws Exception 
			{
				return new ElectricEnergyStorage(1000000);
			}
			
		});
	}
}
