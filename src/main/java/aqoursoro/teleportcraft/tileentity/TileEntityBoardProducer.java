package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.api.IElectricConsumerBlock;
import aqoursoro.teleportcraft.block.machine.BlockBoardProducer;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergy;
import aqoursoro.teleportcraft.capability.mythiniumenergy.MythiniumEnergyStorage;
import aqoursoro.teleportcraft.recipes.machine.BoardProducerRecipes;
import aqoursoro.teleportcraft.recipes.machine.ElectricGrinderRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBoardProducer extends TileEntity implements ITickable, IElectricConsumerBlock {

	public static final int SLOT_NUM = 3;
	
	private static final int INPUT_RATE = 100;
	private static final int OUTPUT_RATE = 5;
	private static final int CAPACITY = 1000;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);
	
	private String customName;
	
	private int totalTime = 50, producingTime = 0, energy = storage.getEnergyStored();
	
	private boolean isWorking = false;
	
	@Override
	public void update() {
		
		if(!this.world.isRemote)
		{
			energy = storage.getEnergyStored();
			ItemStack inStack1 = handler.extractItem(0, 1, true);
			ItemStack inStack2 = handler.extractItem(1, 1, true);
			
			
			if(inStack1 != ItemStack.EMPTY && inStack2 != ItemStack.EMPTY)
			{
				ItemStack result = BoardProducerRecipes.instance().getProducingResult(inStack1, inStack2); //may still have problem
				int outputNum = result.getCount();
				if(result != ItemStack.EMPTY && handler.insertItem(2, result, true) == ItemStack.EMPTY)
				{
					
					if(energy >= OUTPUT_RATE && storage.canExtract())
					{
						storage.extractEnergy(OUTPUT_RATE, false);
						if(++ producingTime >= totalTime)
						{
							producingTime = 0;
							inStack1 = handler.extractItem(0, 1, false);
							inStack2 = handler.extractItem(1, 1, false);
							result = BoardProducerRecipes.instance().getProducingResult(inStack1, inStack2).copy();
							handler.insertItem(2, result, false);
							markDirty();
						}
						
					}
				}
					
			}
			else
			{
				producingTime = 0;
			}
			if(world.isBlockPowered(pos)) 
			{
				storage.insertEnergy(INPUT_RATE, true);
				if(storage.canRecive())
				{
					storage.insertEnergy(INPUT_RATE, false);
				}
			}
		}
		
		if(producingTime > 0 && isWorking == false)
		{
			BlockBoardProducer.setState(true, world, pos);
			isWorking = true;
		}
		if(producingTime == 0 && isWorking == true) 
		{
			BlockBoardProducer.setState(false, world, pos);
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
		compound.setInteger("ProducingTime", producingTime);
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
		producingTime = compound.getInteger("ProducingTime");
		energy = compound.getInteger("GuiEnergy");
		if(compound.hasKey("Name"))
		{
			customName = compound.getString("Name");
		}
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.board_producer");
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
			return producingTime;
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
			producingTime = value;
			break;
		case 1:
			energy = value;
		}
	}
	
	public int getTotalEnergy() {
		return this.CAPACITY;
	}
	
	@Override
	public ElectricEnergyStorage getEnergy() 
	{
		return storage;
	}

	@Override
	public BlockPos getPosition() 
	{
		return this.pos;
	}
	
	@Override
	public boolean canTransferEnergyTo(final EnumFacing side, final int energyToTransfer) 
	{
		return false;
	}
	
	@Override
	public boolean canTransferEnergyToNeighbours() 
	{
		return false;
	}


}
