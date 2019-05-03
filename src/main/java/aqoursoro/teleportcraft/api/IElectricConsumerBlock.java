package aqoursoro.teleportcraft.api;

import java.util.ArrayList;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.IElectricEnergy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IElectricConsumerBlock extends IElectricConsumer
{
	BlockPos getPosition();

	World getWorld();
	
	default boolean ConnectsTo(final EnumFacing side) 
	{
		if (getWorld() == null) 
		{
			return false;
		}

		final TileEntity tile = this.getWorld().getTileEntity(getPosition().offset(side));

		if (tile == null) 
		{
			return false;
		}

		return tile.getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite()) != null;
	}
	
	default ArrayList<EnumFacing> getConnectedSides() 
	{
		final ArrayList<EnumFacing> connectedSides = new ArrayList<>();

		for (final EnumFacing side : EnumFacing.VALUES) 
		{
			if (ConnectsTo(side)) 
			{
				connectedSides.add(side);
			}
		}

		return connectedSides;
	}
	
	default boolean canTransferEnergyTo(final EnumFacing side, final int energyToTransfer)
	{
		if (!getEnergy().canExtract()) 
		{
			return false;
		}

		if (getWorld() == null) 
		{
			return false;
		}

		if (getWorld().isRemote) 
		{
			return false;
		}

		if (getWorld().getTileEntity(getPosition().offset(side)) == null) 
		{
			return false;
		}

		final IElectricEnergy storage = getWorld().getTileEntity(getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

		if (storage == null)
		{
			return false;
		}

		if (!storage.canRecive())
		{
			return false;
		}

		return true;
	}
	
	default boolean canExtractEnergyFrom(final EnumFacing side, final int energyToTransfer)
	{
		if (!getEnergy().canRecive()) 
		{
			return false;
		}

		if (getWorld() == null) 
		{
			return false;
		}

		if (getWorld().isRemote) 
		{
			return false;
		}

		if (getWorld().getTileEntity(getPosition().offset(side)) == null) 
		{
			return false;
		}

		final IElectricEnergy storage = getWorld().getTileEntity(getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

		if (storage == null)
		{
			return false;
		}

		if (!storage.canExtract())
		{
			return false;
		}

		return true;
	}
	
	default int transferEnergyTo(final EnumFacing side, final int energyToTransfer, final boolean simulate) 
	{
		if (!canTransferEnergyTo(side, energyToTransfer)) 
		{
			return 0;
		}
		final IElectricEnergy storage = getWorld().getTileEntity(getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

		return getEnergy().extractEnergy(storage.insertEnergy(energyToTransfer, simulate), simulate);
	}
	
	default int extractEnergyfrom(final EnumFacing side, final int energyToTransfer, final boolean simulate) 
	{
		if (!canExtractEnergyFrom(side, energyToTransfer)) 
		{
			return 0;
		}
		final IElectricEnergy storage = getWorld().getTileEntity(getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

		if(storage == null)
		{
			System.out.println("Failed! null capability");
			return 0;
		}
		
		if (!storage.canExtract())
		{
			System.out.println("Failed! No Power");
			return 0;
		}
		
		return storage.extractEnergy(getEnergy().insertEnergy(energyToTransfer, simulate), simulate);
	}
	
	default boolean canTransferEnergyToNeighbours() 
	{
		if (getWorld().isRemote)
		{
			return false;
		}

		if (!getEnergy().canExtract()) 
		{
			return false;
		}

		if (getEnergy().getEnergyStored() <= 0)
		{
			return false;
		}
		return true;
	}
	
	default boolean canExtractEnergyFromNeighbours() 
	{
		if (getWorld().isRemote)
		{
			return false;
		}

		if (!getEnergy().canRecive()) 
		{
			return false;
		}

		return true;
	}
	
	default void transferEnergyToNeighbours() 
	{
		if (!canTransferEnergyToNeighbours()) 
		{
			return;
		}

		getConnectedSides().forEach(side -> 
		{
			transferEnergyTo(side, (int) ((int) getEnergy().getEnergyStored() / (int) getConnectedSides().size()), false);
		});

	}
	
	default void extractEnergyFromNeighbours() 
	{
		if (!canExtractEnergyFromNeighbours()) 
		{
			return;
		}

		getConnectedSides().forEach(side -> 
		{
			extractEnergyfrom(side, (int) ((int) getEnergy().getEnergyStored() / (int) getConnectedSides().size()), false);
		});

	}
}
