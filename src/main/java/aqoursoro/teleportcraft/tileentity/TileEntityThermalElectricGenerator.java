package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.block.machine.BlockThermalElectricGenerator;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityThermalElectricGenerator extends TileEntity implements ITickable
{

	public static final int SLOT_NUM = 2;
	
	private static final int INPUT_RATE = 100;
	private static final int OUTPUT_RATE = 100;
	private static final int CAPACITY = 5000;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);
	
	private String customName;
	
	private int totalTime = 0, burningTime = 0, energy = storage.getEnergyStored();
	
	private boolean isWorking = false;
	
	@Override
	public void update() 
	{
		if(!this.world.isRemote)
		{
			ItemStack inStack = handler.extractItem(1, 1, true);
			
			ItemStack battery = handler.getStackInSlot(0);
			
			if(battery != ItemStack.EMPTY)
			{
				this.insertItemEnergy(battery);
			}
			
			if(inStack != ItemStack.EMPTY && TileEntityFurnace.isItemFuel(inStack))
			{
				inStack = handler.extractItem(1, 1, false);
				totalTime = TileEntityFurnace.getItemBurnTime(inStack);
				
				if(isWorking == false)
				{
					BlockThermalElectricGenerator.setState(true, world, pos);
					isWorking = true;
				}
			}
			
			if(isWorking)
			{
				if(storage.canRecive())
				{
					storage.insertEnergy(INPUT_RATE, false);
				}
				if(++ burningTime >= totalTime)
				{
					totalTime = burningTime = 0;
					markDirty();
					BlockThermalElectricGenerator.setState(false, world, pos);
					isWorking = false;
				}
			}
		}
		
	}
	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityElectricEnergy.ELECTRIC_ENERGY) 
		{
			return true;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) 
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
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("BurningTime", burningTime);
		compound.setInteger("GuiEnergy", energy);
		storage.writeToNBT(compound);
		compound.setString("Name", getDisplayName().toString());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		storage.readFromNBT(compound);
		burningTime = compound.getInteger("BurningTime");
		energy = compound.getInteger("GuiEnergy");
		if(compound.hasKey("Name"))
		{
			customName = compound.getString("Name");
		}
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.thermal_electric_generator");
	}
	
	public int getEnergyStored()
	{
		return energy;
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, 
											         		                             (double)this.pos.getY() + 0.5D, 
											         		                             (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	private void insertItemEnergy(ItemStack battery) 
	{
		// TODO Auto-generated method stub
		
	}
	
	private int getRequiredEnergyPerTick()
	{
		return OUTPUT_RATE;
	}
	
	public int getTotalTime()
	{
		return totalTime;
	}
	
	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return burningTime;
		case 1:
			return energy;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			burningTime = value;
			break;
		case 1:
			energy = value;
		}
	}
}
