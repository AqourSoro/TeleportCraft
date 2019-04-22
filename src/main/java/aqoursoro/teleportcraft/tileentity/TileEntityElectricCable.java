package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityElectricCable extends TileEntity implements ITickable
{
	
	private final ElectricEnergyStorage energy;
	
	public TileEntityElectricCable() 
	{
		super();
		this.energy = new ElectricEnergyStorage(10000);
	}
	
	@Override
	public void setPos(final BlockPos pos)
	{
		super.setPos(pos);
	}
	
	@Override
	public void onLoad() 
	{
		if (this.world.hasCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null)) 
		{
			if (this.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null) != null) 
			{
				this.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null).addCableLocation(pos);
			}
		}
		super.onLoad();
	}
	
	private void transferElectric()
	{
		System.out.println("Doing it");
	}
	
	private boolean canTransferElectric()
	{
		if(this.getWorld().isRemote)
		{
			return false;
		}
		if(!energy.canExtract())
		{
			return false;
		}
		if(energy.getEnergyStored() <= 0)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		energy.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		energy.readFromNBT(compound);
	}
	
	@Override
	public void update() 
	{
		ElectricEnergyNetManager net = this.world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET,  null);
		
		if(canTransferElectric())
		{
			transferElectric();
		}
		
	}

}
