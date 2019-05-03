package aqoursoro.teleportcraft.inventory.container;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.recipes.machine.StamperRecipes;
import aqoursoro.teleportcraft.tileentity.TileEntityStamper;
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

public class ContainerStamper extends Container {

	private TileEntityStamper tileentity;
	
	private int stampingList, energy;
	
	//protected SlotItemHandler input, output, battery;
	
	public ContainerStamper(@Nonnull InventoryPlayer player, @Nonnull TileEntityStamper tileentity) 
	{
		this.tileentity = tileentity;
		
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 20, 6)); 
		
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 63, 36));
		
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 20, 64));
		
		
		//output slot
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 133, 36) 
		{
			@Override
            public boolean isItemValid(ItemStack stack)
            {
				return false;
            }
		});
		
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
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn) 
	{
		
		return tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(@Nonnull final int id, @Nonnull final int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(stampingList != tileentity.getField(0))
			{
				listener.sendWindowProperty(this, 0, tileentity.getField(0));
			}
			
			if(energy != tileentity.getField(1))
			{
				listener.sendWindowProperty(this, 1, tileentity.getField(1));
			}
		}
		
		stampingList = tileentity.getField(0);
		energy = tileentity.getField(1);
	}
	
	@Override
	public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, @Nonnull final int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack newStack = slot.getStack();
			stack = newStack.copy();
			
			if(index == 3) //output
			{
				if(!this.mergeItemStack(newStack, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(newStack, stack);
			}
			else if(index != 3 && index != 2 && index != 1 && index != 0) 
			{		
				if(StamperRecipes.instance().haveProducingResult(newStack))
	        	{
	        		if(!this.mergeItemStack(newStack, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
	        		else if(index >= 3 && index < 30)
					{
						if(!this.mergeItemStack(newStack, 30, 39, false)) 
						{
							return ItemStack.EMPTY;
						}
					}else if(index >= 30 && index < 39 && !this.mergeItemStack(newStack, 3, 30, false))
					{
						return ItemStack.EMPTY;
					}
	        	}
			} 
			else if(!this.mergeItemStack(newStack, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
	        if(newStack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
	        
	        if(newStack.getCount() == stack.getCount())
	        {
	        	return ItemStack.EMPTY;
	        }
	        slot.onTake(playerIn, newStack);
		}
		return stack;
	}

}
