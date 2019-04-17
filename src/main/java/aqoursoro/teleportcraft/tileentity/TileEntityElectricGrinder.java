package aqoursoro.teleportcraft.tileentity;


import aqoursoro.teleportcraft.block.machine.BlockElectricGrinder;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.recipes.machine.ElectricGrinderRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityElectricGrinder extends TileEntity implements ITickable
{
	public static final int SLOT_NUM = 3;
	
	private static final int INPUT_RATE = 100;
	private static final int OUTPUT_RATE = 5;
	private static final int CAPACITY = 1000;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);
	
	private String customName;
	
	private int totalTime = 50, grindingTime = 0, energy = storage.getEnergyStored();
	
	private boolean isWorking = false;
	
	@Override
	public void update() 
	{
		
		
		if(world.isBlockPowered(pos) && storage.canRecive()) 
		{
			storage.insertEnergy(INPUT_RATE, false);
			energy += INPUT_RATE;
		}
		
		
		if(!this.world.isRemote)
		{
			ItemStack inStack = handler.extractItem(0, 1, true);
			
			ItemStack battery = handler.getStackInSlot(2);
			
			if(battery != ItemStack.EMPTY)
			{
				this.getItemEnergy(battery);
			}
			
			if(inStack != ItemStack.EMPTY)
			{
				ItemStack result = ElectricGrinderRecipes.instance().getGrindingResult(inStack);
				int outputNum = result.getCount();
				if(result != ItemStack.EMPTY && handler.insertItem(1, result, true) == ItemStack.EMPTY)
				{
					int EEPerTick = getRequiredEnergyPerTick();
					if(energy >= EEPerTick && storage.canExtract())
					{
						storage.extractEnergy(EEPerTick, false);
						energy -= EEPerTick;
						if(++ grindingTime >= totalTime)
						{
							grindingTime = 0;
							inStack = handler.extractItem(0, 1, false);
							result = ElectricGrinderRecipes.instance().getGrindingResult(inStack).copy();
							handler.insertItem(1, result, false);
							markDirty();
						}
						
					}
				}
					
			}
			else
			{
				grindingTime = 0;
			}
		}
		
		if(grindingTime > 0 && isWorking == false)
		{
			BlockElectricGrinder.setState(true, world, pos);
			isWorking = true;
		}
		if(grindingTime == 0 && isWorking == true) 
		{
			BlockElectricGrinder.setState(false, world, pos);
			isWorking = false;
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
		compound.setInteger("GrindingTime", grindingTime);
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
		grindingTime = compound.getInteger("GrindingTime");
		energy = compound.getInteger("GuiEnergy");
		if(compound.hasKey("Name"))
		{
			customName = compound.getString("Name");
		}
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.electric_grinder");
	}
	
	public int getEnergyStored()
	{
		return energy;
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public static int getItemEnergy(ItemStack battery)
	{
		//get battery energy here!
		//battery.comusingEnergy(int chargeRate);
		//energy += chargeRate;
		return 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, 
											         		                             (double)this.pos.getY() + 0.5D, 
											         		                             (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
			return grindingTime;
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
			grindingTime = value;
			break;
		case 1:
			energy = value;
		}
	}
	
}
