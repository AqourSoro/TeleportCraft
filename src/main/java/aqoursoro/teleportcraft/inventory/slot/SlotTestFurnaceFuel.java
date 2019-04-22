package aqoursoro.teleportcraft.inventory.slot;

import aqoursoro.teleportcraft.tileentity.TileEntityTestFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTestFurnaceFuel extends Slot
{

	public SlotTestFurnaceFuel(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return TileEntityTestFurnace.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
