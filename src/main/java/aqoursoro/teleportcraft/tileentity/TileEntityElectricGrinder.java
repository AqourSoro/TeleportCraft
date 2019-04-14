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
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityElectricGrinder extends TileEntity implements ITickable
{
	public static final int SLOT_NUM = 3;
	
	public ItemStackHandler handler = new ItemStackHandler(SLOT_NUM);
	
	private ElectricEnergyStorage storage = new ElectricEnergyStorage(75000, 20, 0);
	
	private String customName;
	
	public int grindingTime, energy = storage.getEnergyStored();
	
	private ItemStack grinding = ItemStack.EMPTY;
	
	@Override
	public void update() 
	{
		
		if(world.isBlockPowered(pos)) 
		{
			energy += 100;
		}
		
		ItemStack input = handler.getStackInSlot(0);
		
		ItemStack battery = handler.getStackInSlot(2);
		
		if(battery != ItemStack.EMPTY)
		{
			this.getItemEnergy(battery);
		}
		
		if(energy > 20)
		{
			if(grindingTime > 0) 
			{
				energy -= 20;
				grindingTime++;
				BlockElectricGrinder.setState(true, world, pos);
				if(grindingTime == 100)
				{
					if(handler.getStackInSlot(1).getCount() > 0)
					{
						handler.getStackInSlot(1).grow(1);
					}
					else
					{
						handler.insertItem(1, grinding, false);
					}
					grinding = ItemStack.EMPTY;
					grindingTime = 0;
					return;
				}
				
			}
			else
			{
				if(!input.isEmpty())
				{
					ItemStack output = ElectricGrinderRecipes.instance().getGrindingResult(input);
					if(!output.isEmpty())
					{
						grinding = output;
						grindingTime ++;
						input.shrink(1);
						handler.setStackInSlot(0, input);
						energy -= 20;
					}
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
