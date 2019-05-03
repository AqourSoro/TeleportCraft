package aqoursoro.teleportcraft.inventory.container;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.recipes.machine.ElectricGrinderRecipes;
import aqoursoro.teleportcraft.tileentity.TileEntityItemSite;
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

public class ContainerItemSite extends Container {
	
	private TileEntityItemSite tileentity;
	
	private int energy;
	
	public ContainerItemSite(@Nonnull InventoryPlayer player, @Nonnull TileEntityItemSite tileentity) 
	{
		this.tileentity = tileentity;
				
			IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			
			//chips
			this.addSlotToContainer(new SlotItemHandler(handler, 0, 39, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 1, 39, 69));
			
			//left 4x4
			this.addSlotToContainer(new SlotItemHandler(handler, 2, 63, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 3, 81, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 4, 99, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 5, 117, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 6, 63, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 7, 81, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 8, 99, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 9, 117, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 10, 63, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 11, 81, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 12, 99, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 13, 117, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 14, 63, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 15, 81, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 16, 99, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 17, 117, 69));
			
			//right 4x4
			this.addSlotToContainer(new SlotItemHandler(handler, 18, 141, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 19, 159, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 20, 177, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 21, 195, 15));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 22, 141, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 23, 159, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 24, 177, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 25, 195, 33));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 26, 141, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 27, 159, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 28, 177, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 29, 195, 51));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 30, 141, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 31, 159, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 32, 177, 69));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 33, 195, 69));
			
			//bottom left 1x3
			this.addSlotToContainer(new SlotItemHandler(handler, 34, 26, 108));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 35, 44, 108));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 36, 62, 108));
			
			//bottom right 1x3
			this.addSlotToContainer(new SlotItemHandler(handler, 37, 98, 108));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 38, 116, 108));
			
			this.addSlotToContainer(new SlotItemHandler(handler, 39, 134, 108));
			
		//Player's inventory
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
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
		
		return tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.energy != this.tileentity.getField()) listener.sendWindowProperty(this, 0, this.tileentity.getField());
		}
		
		this.energy = tileentity.getField();
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(@Nonnull final int id, @Nonnull final int data)
	{
		this.tileentity.setField(0 ,data);
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
//	        	if(!ElectricGrinderRecipes.instance().getGrindingResult(newStack).isEmpty())
//	        	{
//	        		if(!this.mergeItemStack(newStack, 0, 2, false)) 
//					{
//						return ItemStack.EMPTY;
//					}
//	        		else if(index >= 3 && index < 30)
//					{
//						if(!this.mergeItemStack(newStack, 30, 39, false)) 
//						{
//							return ItemStack.EMPTY;
//						}
//					}else if(index >= 30 && index < 39 && !this.mergeItemStack(newStack, 3, 30, false))
//					{
//						return ItemStack.EMPTY;
//					}
//	        	}
//	        }
//	        else if(!this.mergeItemStack(newStack, 3, 39, false)) 
//			{
//				return ItemStack.EMPTY;
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

