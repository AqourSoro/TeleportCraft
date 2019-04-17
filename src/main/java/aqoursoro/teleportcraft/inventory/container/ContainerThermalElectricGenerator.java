package aqoursoro.teleportcraft.inventory.container;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityThermalElectricGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerThermalElectricGenerator extends Container
{

	public ContainerThermalElectricGenerator(@Nonnull InventoryPlayer player, @Nonnull TileEntityThermalElectricGenerator tileentity)
	{
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 34, 20) 
		{
			@Override
            public boolean isItemValid(ItemStack stack)
            {
				return stack != ItemStack.EMPTY && stack.getItem() == ModItems.BATTERY && super.isItemValid(stack);
            }
			
			@Override
            public int getItemStackLimit(ItemStack stack)
            {
                return 1;
            }
		});
		
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 34, 57) 
		{
			@Override
            public boolean isItemValid(ItemStack stack)
            {
				return TileEntityFurnace.isItemFuel(stack);
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
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return false;
	}

}
