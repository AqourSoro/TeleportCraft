package aqoursoro.teleportcraft.capability;

public interface IEnergy 
{
	int insertEnergy(int maxInsert, boolean simluate);
	
	int extractEnergy(int maxExtract, boolean simluate);
	
	int getEnergyStored();
	
	int getMaxEnergyStored();
	
	boolean canExtract();
	
	boolean canRecive();
	
}
