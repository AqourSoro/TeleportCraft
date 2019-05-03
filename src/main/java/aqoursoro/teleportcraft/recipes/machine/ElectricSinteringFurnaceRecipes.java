package aqoursoro.teleportcraft.recipes.machine;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ElectricSinteringFurnaceRecipes {

private static final ElectricSinteringFurnaceRecipes INSTANCE = new ElectricSinteringFurnaceRecipes();
	
	private final Map<ItemStack, ItemStack> sinteringList = Maps.<ItemStack, ItemStack>newHashMap();
	
	
	public static ElectricSinteringFurnaceRecipes instance()
    {
        return INSTANCE;
    }
	
	private ElectricSinteringFurnaceRecipes()
	{
		this.addSinteringRecipe(ModItems.MYTHINIUM_POWDER, new ItemStack(ModItems.MYTHINIUM_INGOT, 1));
	}
	
	public void addSinteringRecipe(Item input, ItemStack stack)
	{
		addSinteringRecipe(new ItemStack(input, 1, 32767), stack);
	}
	
	public void addSinteringRecipe(Block input, ItemStack stack)
	{
		addSinteringRecipe(Item.getItemFromBlock(input), stack);
	}
	
	public void addSinteringRecipe(ItemStack input, ItemStack stack)
	{
		//Same output for the same input
		if (getSinteringResult(input) != ItemStack.EMPTY)
		{
			return;
		}
		sinteringList.put(input, stack);
	}
	
	public ItemStack getSinteringResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack> entry : this.sinteringList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }
		return ItemStack.EMPTY;
	}
	
	
	//Common
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}
