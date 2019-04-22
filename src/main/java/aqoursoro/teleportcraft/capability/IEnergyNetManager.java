package aqoursoro.teleportcraft.capability;


import java.util.ArrayList;
import java.util.HashSet;

import aqoursoro.teleportcraft.block.machine.BlockMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public interface IEnergyNetManager 
{
	void addCableLocation(BlockPos pos);
	
	void addMachineLocation(BlockPos pos);
	
	boolean isInNet(HashSet set, BlockPos pos);
	
	/*Check if the cable on a specific position has a connected neighbour.*/
	boolean hasNeighbour(BlockPos pos);
	
	boolean connectedToMachine(BlockPos pos);
	
	/*Get machines if the cable on a specific position connect to machines.*/
	ArrayList<BlockPos> getMachines(BlockPos pos);
	
	/*Get total input that connects to the EnergyNet.*/
	int getEnergyInput();
	
	int getEnergyOutput();
}
