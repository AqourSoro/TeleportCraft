package aqoursoro.teleportcraft.capability.electricenergy;

public interface IElectricEnergy 
{
	int insertEnergy(int maxInsert, boolean simluate);
	
	int extractEnergy(int maxExtract, boolean simluate);
	
	int getEnergyStored();
	
	int getMaxEnergyStored();
	
	boolean canExtract();
	
	boolean canRecive();
	
}
