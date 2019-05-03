package aqoursoro.teleportcraft.capability.electricenergy;

import java.util.Arrays;
import java.util.HashSet;

import aqoursoro.teleportcraft.api.IElectricConsumer;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricNet 
{
	private final HashSet<BlockPos>	cables;
	private final World	world;
	
	public ElectricNet(final World world) 
	{
		this.cables = new HashSet<>(0);
		this.world = world;
	}
	
	public HashSet<BlockPos> getCables() 
	{
		return this.cables;
	}
	
	public ElectricNet add(final BlockPos connection) 
	{
		this.getCables().add(connection);
		return this;
	}

	public ElectricNet remove(final BlockPos connection) 
	{
		this.getCables().remove(connection);
		return this;
	}

	@Override
	public boolean equals(final Object obj) 
	{
		return (obj instanceof ElectricNet) && ((ElectricNet) obj).getCables().equals(this.getCables());
	}
	
	public int getNetEnergy() 
	{
		int networkEnergy = 0;
		for (final BlockPos pos : this.getCables()) 
		{
			try 
			{
				final TileEntity tile = this.world.getTileEntity(pos);
				final ElectricEnergyStorage energy = ((IElectricConsumer) tile).getEnergy();
				networkEnergy += energy.getEnergyStored();
			} 
			catch (final Exception e) 
			{
				e.printStackTrace();
			}
		}
		return networkEnergy;
	}
	
	public void outputEnergy(final BlockPos... dontDistribute) 
	{//Can refactor to improve cable capacity.
		int networkEnergy = this.getNetEnergy();
		int energyOutput = 0;
		int energyCanExtract = 0;
		int outputSize = 0;
		int inputSize = 0;
		for (final BlockPos pos : this.getCables()) 
		{
			try 
			{
				final TileEntity tile = this.world.getTileEntity(pos);
				final ElectricEnergyStorage energy = ((IElectricConsumer) tile).getEnergy();
				if(energy.canReceiveOutside() && energy.canRecive())
				{
					inputSize ++;
					int gotIn = energy.getMaxEnergyStored() - energy.getEnergyStored();
					energyOutput += gotIn;
					energy.insertEnergy(gotIn, true);
				}
				if(energy.canOutPut() && energy.canExtract())
				{
					outputSize ++;
					energyCanExtract += energy.getEnergyStored();
				}
			} 
			catch (final Exception e) 
			{
				e.printStackTrace();
			}
		}
		if(energyOutput >= networkEnergy)
		{
			energyOutput = networkEnergy;
		}
		else if(energyOutput >= energyCanExtract)
		{
			energyOutput = energyCanExtract;
		}
		if(energyCanExtract > 0)
		{
			requestElectric(energyOutput, outputSize);
			consumeElectric(energyOutput, inputSize);

		}
	}
	
	public void consumeElectric(int toConsume, int consumeSize)
	{
		for (final BlockPos pos : this.getCables()) 
		{
			if(toConsume <= 0)
			{
				return;
			}
			int distribute = ModUtil.safeDivision(toConsume, consumeSize);
			if(distribute == 0)
			{
				return;
			}
			try
			{
				final TileEntity tile = this.world.getTileEntity(pos);
				final ElectricEnergyStorage energy = ((IElectricConsumer) tile).getEnergy();
				if(energy.canReceiveOutside() && energy.canRecive())
				{
					int gotIn = energy.getMaxEnergyStored() - energy.getEnergyStored();
					if(gotIn <distribute)
					{
						energy.insertEnergy(gotIn, false);
						toConsume -= gotIn;
						consumeSize --;
					}
					else 
					{
						energy.insertEnergy(distribute, false);
						toConsume -= distribute;
						consumeSize --;
					}

				}
			}
			catch (final Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void requestElectric(int toRequest, int requestSize)
	{
		for (final BlockPos pos : this.getCables()) 
		{
			if(toRequest <= 0)
			{
				return;
			}
			int distribute = ModUtil.safeDivision(toRequest, requestSize);
			if(distribute == 0)
			{
				return;
			}
			try 
			{
				final TileEntity tile = this.world.getTileEntity(pos);
				final ElectricEnergyStorage energy = ((IElectricConsumer) tile).getEnergy();
				if(energy.canOutPut() && energy.canExtract())
				{
					int allGot = energy.getEnergyStored();
					if(allGot > distribute)
					{
						energy.extractEnergy(distribute, false);
						toRequest -= distribute;
						requestSize --;
					}
					else
					{
						energy.extractEnergy(allGot, false);
						toRequest -= allGot;
						requestSize --;
					}
				}
				
			} 
			catch (final Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
