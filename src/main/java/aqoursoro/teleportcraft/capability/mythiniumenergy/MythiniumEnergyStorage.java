package aqoursoro.teleportcraft.capability.mythiniumenergy;

import net.minecraft.nbt.NBTTagCompound;

public class MythiniumEnergyStorage implements IMythiniumEnergy
{

	protected int Energy, Capacity, MaxInsert, MaxExtract;
	
	public MythiniumEnergyStorage(int capacity)
	{
		Capacity = MaxInsert = MaxExtract = capacity;
	}
	
	public MythiniumEnergyStorage(int capacity, int maxInsert, int maxExtract)
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

	@Override
	public boolean canExtract() 
	{
		return this.MaxExtract > 0;
	}

	@Override
	public boolean canRecive() 
	{
		return this.MaxInsert > 0;
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
