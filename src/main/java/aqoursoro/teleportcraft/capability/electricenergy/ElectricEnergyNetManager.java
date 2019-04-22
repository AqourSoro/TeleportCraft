package aqoursoro.teleportcraft.capability.electricenergy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.block.cable.BlockElectricCable;
import aqoursoro.teleportcraft.block.machine.BlockMachine;
import aqoursoro.teleportcraft.capability.IEnergyNetManager;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class ElectricEnergyNetManager implements IEnergyNetManager, ITickable
{
	private static final String DATA_NAME = Reference.MOD_ID + "_EnergyNetData";
	
	private World world;
	
	public ElectricEnergyNetManager(@Nonnull World world) 
	{
		this.world = world;
	}

	private HashSet<BlockPos> connectedCables = new HashSet<BlockPos>();
	private HashSet<BlockPos> connectedMachines = new HashSet<BlockPos>();
	
	private Block[] getNeighbourBlocks(BlockPos pos)
	{
		Block upBlock = world.getBlockState(pos.up()).getBlock();
		Block downBlock = world.getBlockState(pos.down()).getBlock();
		Block northBlock = world.getBlockState(pos.north()).getBlock();
		Block southBlock = world.getBlockState(pos.south()).getBlock();
		Block eastBlock = world.getBlockState(pos.east()).getBlock();
		Block westBlock = world.getBlockState(pos.west()).getBlock();
		Block neighbours[]  = {upBlock, downBlock, northBlock, southBlock, eastBlock, westBlock};
		return neighbours;
	}
	
	
	@Override
	public void addCableLocation(BlockPos pos) 
	{
		if(!isInNet(connectedCables,pos))
		{
			connectedCables.add(pos);
		}
		
	}
	
	public void removeCableLocation(BlockPos pos)
	{
		if(isInNet(connectedCables, pos))
		{
			connectedCables.remove(pos);
		}
	}

	@Override
	public boolean isInNet(HashSet set, BlockPos pos) 
	{
		if(set.contains(pos))
		{
			return true;
		}
		return false;
	}

	public ArrayList<BlockPos> getNeighbours(BlockPos pos)
	{
		ArrayList<BlockPos> neighboursPos = new ArrayList<>();
		Block[] neighbours = this.getNeighbourBlocks(pos);
		for(int i = 0; i < neighbours.length; i ++)
		{
			if(neighbours[i] instanceof BlockMachine)
			{
				switch(i)
				{
					case 0:
						neighboursPos.add(pos.up());
					case 1:
						neighboursPos.add(pos.down());
					case 2:
						neighboursPos.add(pos.north());
					case 3:
						neighboursPos.add(pos.south());
					case 4:
						neighboursPos.add(pos.east());
					case 5:
						neighboursPos.add(pos.west());
				}
				
			}
		}
		
		return neighboursPos;
	}
	
	public HashSet<BlockPos> getCableNet()
	{
		return connectedCables;
	}
	
	public HashSet<BlockPos> getMachineConnected()
	{
		return connectedMachines;
	}
	
	@Override
	public boolean hasNeighbour(BlockPos pos) 
	{
		Block[] neighbours = this.getNeighbourBlocks(pos);
		for(Block neighbour : neighbours)
		{
			if(neighbour instanceof BlockElectricCable)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean connectedToMachine(BlockPos pos) 
	{
		Block[] neighbours = this.getNeighbourBlocks(pos);
		for(Block neighbour : neighbours)
		{
			if(neighbour instanceof BlockMachine)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ArrayList<BlockPos> getMachines(BlockPos pos) 
	{
		ArrayList<BlockPos> machinePos = new ArrayList<>();
		Block[] neighbours = this.getNeighbourBlocks(pos);
		for(int i = 0; i < neighbours.length; i ++)
		{
			if(neighbours[i] instanceof BlockMachine)
			{
				switch(i)
				{
					case 0:
						machinePos.add(pos.up());
					case 1:
						machinePos.add(pos.down());
					case 2:
						machinePos.add(pos.north());
					case 3:
						machinePos.add(pos.south());
					case 4:
						machinePos.add(pos.east());
					case 5:
						machinePos.add(pos.west());
				}
				
			}
		}
		
		return machinePos;
	}

	@Override
	public void addMachineLocation(BlockPos pos) 
	{
		if(!isInNet(connectedMachines,pos))
		{
			connectedMachines.add(pos);
		}
	}
	
	@Override
	public int getEnergyInput() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyOutput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() 
	{
		if(!world.isRemote)
		{
			if(!connectedCables.isEmpty())
			{
				for(BlockPos pos : connectedCables)
				{
					if(this.hasNeighbour(pos))
					{
						addCableLocation(pos);
					}
					if(this.connectedToMachine(pos))
					{
						ArrayList<BlockPos> machinePos = getMachines(pos);
						if(!machinePos.isEmpty()) 
						{
							for(BlockPos mpos : machinePos)
							{
								addMachineLocation(mpos);
							}
						}
					}
				}
			}
		}
		//for beginning, onBlockPlaced?
	}

	

}
