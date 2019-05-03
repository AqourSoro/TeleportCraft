package aqoursoro.teleportcraft.capability.mythiniumenergy;

import java.util.HashSet;

import aqoursoro.teleportcraft.api.IMythiniumConsumer;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MythiniumNet 
{
	private final HashSet<BlockPos>	cables;
	private final World				world;
	
	public MythiniumNet(final World world) 
	{
		this.cables = new HashSet<>(0);
		this.world = world;
	}	
	
	public HashSet<BlockPos> getCables() 
	{
		return this.cables;
	}
	
	public MythiniumNet add(final BlockPos connection) 
	{
		this.getCables().add(connection);
		return this;
	}

	public MythiniumNet remove(final BlockPos connection) 
	{
		this.getCables().remove(connection);
		return this;
	}

	@Override
	public boolean equals(final Object obj) 
	{
		return (obj instanceof MythiniumNet) && ((MythiniumNet) obj).getCables().equals(this.getCables());
	}
	
	public int getNetEnergy() 
	{
		int networkEnergy = 0;
		for (final BlockPos pos : this.getCables()) 
		{
			try 
			{
				final TileEntity tile = this.world.getTileEntity(pos);
				final MythiniumEnergyStorage energy = ((IMythiniumConsumer) tile).getEnergy();
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
	{
		
	}
	
	public void consumeMythinium(int toConsume, int consumeSize)
	{
		
	}
	
	public void requestMythinium(int toRequest, int requestSize)
	{
		
	}
}
