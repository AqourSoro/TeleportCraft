package aqoursoro.teleportcraft.capability.mythiniumenergy;

import java.util.HashSet;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.api.IElectricConsumer;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricNet;
import aqoursoro.teleportcraft.network.ModNetworkManager;
import aqoursoro.teleportcraft.network.SPktSynElectricNetList;
import aqoursoro.teleportcraft.network.SPktSynMythiniumNetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MythiniumEnergyNetManager implements ITickable
{
private World world;
	
	public MythiniumEnergyNetManager(@Nonnull World world) 
	{
		this.connectedCables = new HashSet<>(0);
		this.nets = new HashSet<>(0);
		this.world = world;
	}

	private HashSet<BlockPos> connectedCables = new HashSet<BlockPos>();
	private HashSet<MythiniumNet> nets = new HashSet<MythiniumNet>();

	public HashSet<BlockPos> getCables()
	{
		return connectedCables;
	}
	
	public HashSet<MythiniumNet> getNets()
	{
		return nets;
	}
	
	public World getWorld() 
	{
		return world;
	}
	
	public void addCables(final BlockPos pos) 
	{
		this.getCables().add(pos);
		this.onChange(pos);
	}
	
	public void removeCables(final BlockPos pos)
	{
		this.getCables().remove(pos);
		this.onChange(pos);
	}
	
	private boolean shouldRefreshCables() 
	{
		final int check = (int) (this.getCables().size() / 1000f);
		if (check == 0) {
			return true;
		}
		return (this.world.getTotalWorldTime() % check) == 0;
	}

	private boolean shouldRefreshNets() 
	{
		final int check = (int) (this.getCables().size() / 100f);
		if (check == 0) 
		{
			return true;
		}
		return (this.world.getTotalWorldTime() % check) == 0;
	}

	private boolean shouldOutputEnergy() 
	{
		final int check = (int) (this.getCables().size() / 10f);
		if (check == 0) 
		{
			return true;
		}
		return (this.world.getTotalWorldTime() % check) == 0;
	}
	
	public void refreshNets()
	{
		this.nets.clear();
		final HashSet<BlockPos> done = new HashSet<>(0);

		for (final BlockPos pos : this.getCables()) 
		{
			if (!done.contains(pos)) {
				done.add(pos);

				final MythiniumNet network = new MythiniumNet(this.world);
				this.generateNet(network, pos);
				done.addAll(network.getCables());
				this.nets.add(network);

			}
		}
	}
	
	private void generateNet(final MythiniumNet network, final BlockPos pos) 
	{
		if (network.getCables().size() > 200) 
		{

			return;
		}
		
		final TileEntity tile = this.world.getTileEntity(pos);
		if (tile == null) 
		{
			return;
		}
		if(!(tile instanceof IElectricConsumer))
		{
			return;
		}
		network.add(pos);
		for(EnumFacing direction : EnumFacing.VALUES)
		{
			final BlockPos neighbour = pos.offset(direction);
			if(!network.getCables().contains(neighbour))
			{
				this.generateNet(network, neighbour);
			}
		}
		
	}
	
	public void refreshCables()
	{
		for (final BlockPos pos : this.getCables())
		{
			final TileEntity tile = world.getTileEntity(pos);
			if(tile == null)
			{
				this.removeCables(pos);
				continue;
			}
			if(!(tile instanceof IElectricConsumer))
			{
				this.removeCables(pos);
				continue;
			}
		}
	}
	
	@Override
	public void update() 
	{
		if (this.world.isRemote) 
		{
			return;
		}

		if (this.shouldRefreshCables()) 
		{
			this.refreshCables();
		}
		if (this.shouldRefreshNets()) 
		{
			this.refreshNets();
		}
		if (this.shouldOutputEnergy()) 
		{
			for (final MythiniumNet net : this.nets) 
			{
				net.outputEnergy();
			}
		}
		
	}
	
	public void onChange(final BlockPos pos) 
	{
		if (!this.world.isRemote) 
		{
			final NBTTagList syncTag = (NBTTagList) CapabilityMythiniumEnergyNetManager.MYTHINIUM_ENERGY_NET.writeNBT(this, null);
			for (final EntityPlayer player : this.world.playerEntities) 
			{
				if (player instanceof EntityPlayerMP) 
				{
					ModNetworkManager.NETWORK.sendTo(new SPktSynMythiniumNetList(syncTag), (EntityPlayerMP) player);
				}
			}
		} 
		else 
		{

		}
	}
}
