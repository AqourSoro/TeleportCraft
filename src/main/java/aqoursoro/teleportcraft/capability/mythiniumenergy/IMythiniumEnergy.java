package aqoursoro.teleportcraft.capability.mythiniumenergy;

public interface IMythiniumEnergy 
{
	int insertEnergy(int maxInsert, boolean simluate);
	
	int extractEnergy(int maxExtract, boolean simluate);
	
	int getEnergyStored();
	
	int getMaxEnergyStored();
	
	boolean canExtract();
	
	boolean canRecive();
}
