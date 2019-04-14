package aqoursoro.teleportcraft.inventory.container;

import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElectricGrinder extends Container
{
	
	private final TileEntityElectricGrinder tileEntity;
	
	private int grindingTime, energy;
	
	public ContainerElectricGrinder(InventoryPlayer player, TileEntityElectricGrinder tileentity) 
	{
		tileEntity = tileentity;
		
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		//the first param is the 
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 56, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 112, 30));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 56, 53));
		
		//player's inventory
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		
		return tileEntity.isUsableByPlayer(playerIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(grindingTime != tileEntity.getField(0))
			{
				listener.sendWindowProperty(this, 0, tileEntity.getField(0));
			}
			
			if(energy != tileEntity.getField(1))
			{
				listener.sendWindowProperty(this, 0, tileEntity.getField(1));
			}
		}
		
		grindingTime = tileEntity.getField(0);
		energy = tileEntity.getField(1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		return ItemStack.EMPTY;
	}

}
