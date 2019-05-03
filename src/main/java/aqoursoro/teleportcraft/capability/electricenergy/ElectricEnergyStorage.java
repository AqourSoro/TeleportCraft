package aqoursoro.teleportcraft.capability.electricenergy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class ElectricEnergyStorage implements IElectricEnergy
{
	protected int Energy, Capacity, MaxInsert, MaxExtract;
	
	public ElectricEnergyStorage(int capacity)
	{
		Capacity = MaxInsert = MaxExtract = capacity;
	}
	
	public ElectricEnergyStorage(int capacity, int maxInsert, int maxExtract)
	{
		Capacity = capacity;
		MaxInsert = maxInsert;
		MaxExtract = maxExtract;
	}
	
	@Override
	public int insertEnergy(int maxInsert, boolean simluate) 
	{
		if(!canRecive())
		{
			return 0;
		}
		int recived = Math.min(Capacity - Energy, Math.min(MaxInsert, maxInsert));
		if(!simluate)
		{
			Energy += recived;
		}
		return recived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simluate) 
	{
		if(!canExtract())
		{
			return 0;
		}
		int extracted = Math.min(Energy, Math.min(MaxExtract, maxExtract));
		if(!simluate)
		{
			Energy -= extracted;
		}
		return extracted;
	}
	
	public int insertEnergy(int receiveEnergy, boolean simulate, EnumFacing side) 
	{
		return this.insertEnergy(receiveEnergy, simulate);
	}

	@Override
	public int getEnergyStored() 
	{
		return Energy;
	}

	@Override
	public int getMaxEnergyStored() 
	{
		return Capacity;
	}

	public int setEnergyStored(final int energy, final boolean simulate) 
	{
		final int toSet = Math.min(energy, this.MaxInsert);
		if (!simulate) 
		{
			Energy = toSet;
		}
		return toSet;
	}
	
	@Override
	public boolean canExtract() 
	{
		return this.MaxExtract > 0 && Energy > 0;
	}

	@Override
	public boolean canRecive() 
	{
		return this.MaxInsert > 0 && Energy < Capacity;
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		Energy = nbt.getInteger("Energy");
		Capacity = nbt.getInteger("Capacity");
		MaxInsert = nbt.getInteger("MaxInserted");
		MaxExtract = nbt.getInteger("MaxExtracted");
		
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("Energy", Energy);
		nbt.setInteger("Capacity", Capacity);
		nbt.setInteger("MaxInserted", MaxInsert);
		nbt.setInteger("MaxExtracted", MaxExtract);
	}

	@Override
	public boolean canReceiveOutside() 
	{
		return true;
	}

	@Override
	public boolean canOutPut() 
	{
		return false;
	}
}
