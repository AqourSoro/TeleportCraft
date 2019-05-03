package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.api.IElectricConsumer;
import aqoursoro.teleportcraft.api.IElectricConsumerBlock;
import aqoursoro.teleportcraft.api.ITileSyncable;
import aqoursoro.teleportcraft.block.cable.BlockElectricCable;
import aqoursoro.teleportcraft.block.machine.BlockMachine;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.electricenergy.IElectricEnergy;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityElectricCable extends TileEntity implements ITickable, ITileSyncable, IElectricConsumerBlock
{
	
	private static final int CAPACITY = 100;
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY);
	
	@Override
	public void onLoad() 
	{
		if (this.world.hasCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null)) 
		{
			if (this.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null) != null) 
			{
				this.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null).addCables(pos);
			}
		}
		super.onLoad();
	}
	
	@Override
	public void update() 
	{
		this.handleSync();
	}
	
	@Override
	public boolean canTransferEnergyTo(final EnumFacing side, final int energyToTransfer)
	{
		if (!this.getEnergy().canExtract()) 
		{
			return false;
		}

		if (this.getWorld() == null) 
		{
			return false;
		}

		if (this.getWorld().isRemote) 
		{
			return false;
		}

		if (this.getWorld().getTileEntity(this.getPosition().offset(side)) == null) 
		{
			return false;
		}

		if (this.getWorld().getTileEntity(this.getPosition().offset(side)) instanceof IElectricConsumer) 
		{
			return false;
		}

		final IElectricEnergy storage = this.getWorld().getTileEntity(this.getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

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
	
	@Override
	public boolean canExtractEnergyFrom(final EnumFacing side, final int energyToTransfer)
	{
		if (!this.getEnergy().canRecive()) 
		{
			return false;
		}

		if (this.getWorld() == null) 
		{
			return false;
		}

		if (this.getWorld().isRemote) 
		{
			return false;
		}
		
		if (this.getWorld().getTileEntity(this.getPosition().offset(side)) == null) 
		{
			return false;
		}

		if (this.getWorld().getTileEntity(this.getPosition().offset(side)) instanceof IElectricConsumer) 
		{
			return false;
		}

		final IElectricEnergy storage = this.getWorld().getTileEntity(this.getPosition().offset(side)).getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, side.getOpposite());

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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityElectricEnergy.ELECTRIC_ENERGY) 
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityElectricEnergy.ELECTRIC_ENERGY) 
		{
			return (T) this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public BlockPos getPosition() 
	{
		return this.pos;
	}

	@Override
	public ElectricEnergyStorage getEnergy() 
	{
		return this.storage;
	}
	
	
	@Override
	public void readFromNBT(final NBTTagCompound compound) 
	{
		super.readFromNBT(compound);		
		if (compound.hasKey(ENERGY)) 
		{
			this.getEnergy().setEnergyStored(compound.getInteger(ENERGY), false);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger(ENERGY, this.getEnergy().getEnergyStored());
		return compound;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) 
	{
		this.readFromNBT(pkt.getNbtCompound());
	}
	

	
}
