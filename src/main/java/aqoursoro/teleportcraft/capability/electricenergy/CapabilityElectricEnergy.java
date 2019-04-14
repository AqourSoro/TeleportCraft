package aqoursoro.teleportcraft.capability.electricenergy;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.capability.IEnergy;
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
	@CapabilityInject(IEnergy.class)
	public static final Capability<IEnergy> ELECTRIC_ENERGY = null;
	
	
	public static void registerElectricEnergy()
	{
		CapabilityManager.INSTANCE.register(IEnergy.class, new IStorage<IEnergy>() 
		{

			@Override
			public NBTBase writeNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side) 
			{
				return new NBTTagInt(instance.getEnergyStored());
			}

			@Override
			public void readNBT(Capability<IEnergy> capability, IEnergy instance, EnumFacing side, NBTBase nbt) 
			{
				if(!(instance instanceof ElectricEnergyStorage)) 
				{
					throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
				}
				((ElectricEnergyStorage)instance).Energy = ((NBTTagInt)nbt).getInt();
				
			}
			
		}, 
		new Callable<IEnergy>()
		{

			@Override
			public IEnergy call() throws Exception 
			{
				return new ElectricEnergyStorage(1000000);
			}
			
		});
	}
}
