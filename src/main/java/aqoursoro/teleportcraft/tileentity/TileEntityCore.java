package aqoursoro.teleportcraft.tileentity;

import aqoursoro.teleportcraft.api.IElectricConsumerBlock;
import aqoursoro.teleportcraft.api.IMythiniumConsumerBlock;
import aqoursoro.teleportcraft.block.machine.BlockCore;
import aqoursoro.teleportcraft.block.machine.BlockElectricGrinder;
import aqoursoro.teleportcraft.block.machine.BlockThermalElectricGenerator;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyStorage;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergy;
import aqoursoro.teleportcraft.capability.mythiniumenergy.MythiniumEnergyStorage;
import aqoursoro.teleportcraft.inventory.container.ContainerCore;
import aqoursoro.teleportcraft.recipes.machine.ElectricGrinderRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCore extends TileEntity implements ITickable, IElectricConsumerBlock
{
	
	public static final int SLOT_NUM = 40;
	
	private static final int INPUT_RATE = 10;
	private static final int OUTPUT_RATE = 5;
	private static final int CAPACITY = 1000;
	
	private static final int POWERDOWN = 5;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
//	private MythiniumEnergyStorage storage = new MythiniumEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(CAPACITY, INPUT_RATE, OUTPUT_RATE);

	
	private String customName;
	
	private int totalEnergy = 5, teleportTime = 0;
	private int totalTime = 200, burningTime = 0, grindingTime = 0;
	public int energy = storage.getEnergyStored();
	
	private boolean isWorking =false;

	@Override
	public void update() 
	{		
//		if(world.isBlockPowered(pos)) energy += 10;
//		if(energy >= 1000) 
//		{
//			energy = 1000;
//		}
//		energy -= POWERDOWN;
//		if(energy <= 0) 
//		{
//			energy = 0;
//		}
//		
//		if(energy > 0 && isWorking == false)
//		{
//			BlockCore.setState(true, world, pos);
//			isWorking = true;
//		}
//		if(energy == 0 && isWorking == true) 
//		{
//			BlockCore.setState(false, world, pos);
//			isWorking = false;
//		}
		
		if(!this.world.isRemote)
		{
			energy = storage.getEnergyStored();
			ItemStack inStack = handler.extractItem(0, 1, true);
			if(energy >= 1000) 
			{
				energy = 1000;
			}
			if(energy <= 0) 
			{
				energy = 0;
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
		if(energy > 0  && isWorking == false)
		{
			BlockCore.setState(true, world, pos);
			isWorking = true;
		}
		if(energy == 0 && isWorking == true)
		{
			BlockCore.setState(false, world, pos);
			isWorking = false;
		}
	
	
		
		
		if(handler.getStackInSlot(24).isEmpty()) {
			ContainerCore.correctEnter1 = 0;
		}else {
			ContainerCore.correctEnter1 = 1;
		}
		if(handler.getStackInSlot(25).isEmpty()) {
			ContainerCore.correctEnter2 = 0;
		}else {
			ContainerCore.correctEnter2 = 1;
		}
		if(handler.getStackInSlot(26).isEmpty()) {
			ContainerCore.correctEnter3 = 0;
		}else {
			ContainerCore.correctEnter3 = 1;
		}
		if(handler.getStackInSlot(27).isEmpty()) {
			ContainerCore.correctEnter4 = 0;
		}else {
			ContainerCore.correctEnter4 = 1;
		}
			
	}
	

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("TeleportTime", this.teleportTime);
		compound.setInteger("GuiEnergy", this.energy);
		storage.writeToNBT(compound);
		compound.setString("Name", this.getDisplayName().toString());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		storage.readFromNBT(compound);
		teleportTime = compound.getInteger("TeleportTime");
		energy = compound.getInteger("GuiEnergy");
		if(compound.hasKey("Name"))
		{
			customName = compound.getString("Name");
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
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.core");
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
//		energy = value;
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
	public ElectricEnergyStorage getEnergy() {
		return storage;
	}


	@Override
	public BlockPos getPosition() {
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
	
	
	
	
	
	
//	public int getSlotName()
//	{
//		return SLOT_NUM;
//		
//	}
	
	
//	private ElectricEnergyStorage storage = new ElectricEnergyStorage(200000);
//	public int energy = storage.getEnergyStored();
//	private String customName;
//	
//	@Override
//	public void update() 
//	{
//		if(world.isBlockPowered(pos)) energy += 100;
//	}
//	
//	@Override
//	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
//	{
//		if(capability == CapabilityEnergy.ENERGY) return true;
//		return super.hasCapability(capability, facing);
//	}
//	
//	@Override
//	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
//	{
//		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
//		return super.getCapability(capability, facing);
//	}
//	
//	@Override
//	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
//	{
//		super.writeToNBT(compound);
//		compound.setInteger("GuiEnergy", this.energy);
//		compound.setString("Name", this.getDisplayName().toString());
//		this.storage.writeToNBT(compound);
//		return compound;
//	}
//	
//	@Override
//	public void readFromNBT(NBTTagCompound compound) 
//	{
//		super.readFromNBT(compound);
//		this.energy = compound.getInteger("GuiEnergy");
//		this.customName = compound.getString("Name");
//		this.storage.readFromNBT(compound);
//	}
//	
//	@Override
//	public ITextComponent getDisplayName() 
//	{
//		return new TextComponentTranslation("container.energy_storage");
//	}
//
//	public int getEnergyStored() 
//	{
//		return energy;
//	}
//	
//	public int getMaxEnergyStored() 
//	{
//		return this.storage.getMaxEnergyStored();
//	}
//	
//	public int getField(int id)
//	{
//		switch(id)
//		{
//		case 0:
//			return this.energy;
//		default:
//			return 0;
//		}
//	}
//	
//	public void setField(int id, int value)
//	{
//		switch(id)
//		{
//		case 0:
//			this.energy = value;
//		}
//	}
//	
//	public boolean isUsableByPlayer(EntityPlayer player)
//	{
//		return this.world.getTileEntity(pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64D;
//	}


	
}
