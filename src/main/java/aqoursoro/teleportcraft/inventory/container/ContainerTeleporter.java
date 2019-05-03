 package aqoursoro.teleportcraft.inventory.container;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.tileentity.TileEntityTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


public class ContainerTeleporter extends Container
{

	private final TileEntityTeleporter tileEntity;

	private int energy;

	public ContainerTeleporter(InventoryPlayer playerInventory, TileEntityTeleporter tileEntity)
	{
		this.tileEntity = tileEntity;
		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		// container slot
		this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 76, 19)
		{
			@Override
            public boolean isItemValid(ItemStack stack)
            {
				boolean flag = false;
				if(stack != ItemStack.EMPTY && super.isItemValid(stack))
				{   
					for(int i = 0; i < TeleportCraft.ID_LIST.size(); i++) 
					{
						if(stack.getItem() == TeleportCraft.ID_LIST.get(i))
						{
							flag = true;
						}
						else continue;
					}					
				}
				
				return flag;
            }
			
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});
		
		this.addSlotToContainer(new SlotItemHandler(itemHandler, 1, 76, 54)
		{
			@Override
            public boolean isItemValid(ItemStack stack)
            {
				boolean flag = false;
				if(stack != ItemStack.EMPTY && super.isItemValid(stack))
				{   
					for(int i = 0; i < TeleportCraft.CHANNEL_LIST.size(); i++) 
					{
						if(stack.getItem() == TeleportCraft.CHANNEL_LIST.get(i))
						{
							flag = true;
						}
						else continue;
					}					
				}
				
				return flag;
            }
			
			@Override
            public int getItemStackLimit(ItemStack stack)
            {	
                return 1;
            }
		});

		// player inventory slots
		int offsetX = 8;
		int offsetY = 85;
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				int index = col + (row * 9) + 9;
				int xPosition = offsetX + (col * 18);
				int yPosition = offsetY + (row * 18);
				this.addSlotToContainer(new Slot(playerInventory, index, xPosition, yPosition));
			}
		}

		// player hotbar slots
		offsetY = 143;
		for (int col = 0; col < 9; ++col)
		{
			int index = col;
			int xPosition = offsetX + (col * 18);
			int yPosition = offsetY;
			this.addSlotToContainer(new Slot(playerInventory, index, xPosition, yPosition));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.tileEntity.canInteractWith(player);
	}

	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(energy != tileEntity.getField(0))
			{
				listener.sendWindowProperty(this, 0, tileEntity.getField(0));
			}
		}

		energy = tileEntity.getField(0);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index <= 1)
			{
				if (!this.mergeItemStack(itemstack1, 0, 1, true))
				{
					return ItemStack.EMPTY;
				}
				else if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 1, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

}
