package aqoursoro.teleportcraft.inventory.container;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.recipes.machine.ElectricGrinderRecipes;
import aqoursoro.teleportcraft.tileentity.TileEntityCore;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;



public class ContainerCore extends Container
{
	
	private TileEntityCore tileentity;
	
	private int grindingTime, energy;
	
	public static int correctEnter1 = 0;
	public static int correctEnter2 = 0;
	public static int correctEnter3 = 0;
	public static int correctEnter4 = 0;
	
	public ContainerCore(@Nonnull InventoryPlayer player, @Nonnull TileEntityCore tileentity) 
	{
		this.tileentity = tileentity;
		
		IItemHandler handler = (tileentity).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 63, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 81, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 99, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 117, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 135, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 153, 15));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 63, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 81, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 99, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 9, 117, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 10, 135, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 11, 153, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 12, 63, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 13, 81, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 14, 99, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 15, 117, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 16, 135, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 17, 153, 51));
		this.addSlotToContainer(new SlotItemHandler(handler, 18, 63, 69));
		this.addSlotToContainer(new SlotItemHandler(handler, 19, 81, 69));
		this.addSlotToContainer(new SlotItemHandler(handler, 20, 99, 69));
		this.addSlotToContainer(new SlotItemHandler(handler, 21, 117, 69));
		this.addSlotToContainer(new SlotItemHandler(handler, 22, 135, 69));
		this.addSlotToContainer(new SlotItemHandler(handler, 23, 153, 69));
		
		this.addSlotToContainer(new SlotItemHandler(handler, 24, 31, 114)
		{		
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});
				
		this.addSlotToContainer(new SlotItemHandler(handler, 25, 63, 114)
		{
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});
		this.addSlotToContainer(new SlotItemHandler(handler, 26, 95, 114)
		{
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});
		this.addSlotToContainer(new SlotItemHandler(handler, 27, 127, 114)
		{
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});
		
		//player's inventory
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 140 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 198));
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
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.energy != this.tileentity.getField()) listener.sendWindowProperty(this, 0, this.tileentity.getField());
		}
		
		this.energy = tileentity.getField();
	}
	
	@Override
	public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, @Nonnull final int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack())
        {
			ItemStack newStack = slot.getStack(); 
	        stack = newStack.copy();
	        
//	        if(index == 1)
//	        {
//	        	if(!this.mergeItemStack(newStack, 3, 39, true)) 
//	        	{
//	        		return ItemStack.EMPTY;
//	        	}
//	        	slot.onSlotChange(newStack, stack);
//	        }
//	        else if(index != 2 && index != 1 && index != 0) 
//	        {
//        		if(!this.mergeItemStack(newStack, 0, 2, false)) 
//				{
//					return ItemStack.EMPTY;
//				}
//        		else if(index >= 3 && index < 30)
//				{
//					if(!this.mergeItemStack(newStack, 30, 39, false)) 
//					{
//						return ItemStack.EMPTY;
//					}
//				}
//        		else if(index >= 30 && index < 39 && !this.mergeItemStack(newStack, 3, 30, false))
//				{
//					return ItemStack.EMPTY;
//				}
//	        }
//	        else if(!this.mergeItemStack(newStack, 3, 39, false)) 
//			{
//				return ItemStack.EMPTY;
//			}
//	        if(newStack.isEmpty())
//			{
//				slot.putStack(ItemStack.EMPTY);
//			}
//			else
//			{
//				slot.onSlotChanged();
//			}
	        
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
