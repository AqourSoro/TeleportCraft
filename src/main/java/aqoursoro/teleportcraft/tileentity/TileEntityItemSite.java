package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.api.IElectricConsumerBlock;
import aqoursoro.teleportcraft.api.IMythiniumConsumerBlock;
import aqoursoro.teleportcraft.block.machine.BlockItemSite;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergy;
import aqoursoro.teleportcraft.capability.mythiniumenergy.MythiniumEnergyStorage;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityItemSite extends TileEntity implements ITickable, IElectricConsumerBlock {
	
	public static final int SLOT_NUM = 40;
	
	private static final int INPUT_RATE = 8;
	private static final int OUTPUT_RATE = 5;
	private static final int CAPACITY = 1000;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);
	
	private String customName;
	
	private int teleportTime = 0 ,totalTime = 50, grindingTime = 0;
	public int energy = storage.getEnergyStored();
	
	private boolean isWorking =false;

	@Override
	public void update() {
		
		if(!this.world.isRemote)
		{
			energy = storage.getEnergyStored();
			ItemStack inStack = handler.extractItem(0, 1, true);
			
//			if(!inStack.isEmpty())
//			{
//				ItemStack result = ElectricGrinderRecipes.instance().getGrindingResult(inStack);
//				int outputNum = result.getCount();
//				if((!result.isEmpty()) && handler.insertItem(1, result, true).isEmpty())
//				{
//					if(energy >= OUTPUT_RATE && storage.canExtract())
//					{
//						storage.extractEnergy(OUTPUT_RATE, false);
//						if(++ grindingTime >= totalTime)
//						{
//							grindingTime = 0;
//							inStack = handler.extractItem(0, 1, false);
//							result = ElectricGrinderRecipes.instance().getGrindingResult(inStack).copy();
//							handler.insertItem(1, result, false);
//							markDirty();
//						}
//						
//					}
//				}
//					
//			}
//			else
//			{
//				grindingTime = 0;
//			}
			if(world.isBlockPowered(pos)) 
			{
				storage.insertEnergy(INPUT_RATE, true);
				if(storage.canRecive())
				{
					storage.insertEnergy(INPUT_RATE, false);
				}
			}
		}
		if(grindingTime > 0 && isWorking == false)
		{
			BlockItemSite.setState(true, world, pos);
			isWorking = true;
		}
		if(grindingTime == 0 && isWorking == true)
		{
			BlockItemSite.setState(false, world, pos);
			isWorking = false;
		}
	}
	

	private int getRequiredEnergyPerTick() {
		
		return OUTPUT_RATE;
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("TeleportTime", this.teleportTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", this.getDisplayName().toString());
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.teleportTime = compound.getInteger("TeleportTime");
		this.energy = compound.getInteger("GuiEnergy");
		if(compound.hasKey("Name"))
		{
			this.customName = compound.getString("Name");
		}
		this.storage.readFromNBT(compound);
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
			return (T)this.storage;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.item_site");
	}
	
	public int getEnergyStored()
	{
		return this.energy;
	}
	
	public int getField() 
	{
		return this.energy;
	}
	
	public void setField(int id, int value) 
	{
		switch(id)
		{
		case 0:
			this.energy = value;
		}
		
	}
	
	public int getTotalEnergy() {
		return this.CAPACITY;
	}
	

	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, 
											         		                             (double)this.pos.getY() + 0.5D, 
											         		                             (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
