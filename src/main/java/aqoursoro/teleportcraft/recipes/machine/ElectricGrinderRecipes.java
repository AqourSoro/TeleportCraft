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

public class ElectricGrinderRecipes 
{
	private static final ElectricGrinderRecipes INSTANCE = new ElectricGrinderRecipes();
	
	private final Map<ItemStack, ItemStack> grindingList = Maps.<ItemStack, ItemStack>newHashMap();
	
	
	public static ElectricGrinderRecipes instance()
    {
        return INSTANCE;
    }
	
	private ElectricGrinderRecipes()
	{
		this.addGrindingRecipe(Blocks.IRON_ORE, new ItemStack(ModItems.IRON_ORE_POWDER, 2));
		this.addGrindingRecipe(Items.IRON_INGOT, new ItemStack(ModItems.IRON_POWDER, 1));
		
		this.addGrindingRecipe(ModBlocks.COPPER_ORE, new ItemStack(ModItems.COPPER_ORE_POWDER, 2));
		this.addGrindingRecipe(ModItems.COPPER_INGOT, new ItemStack(ModItems.COPPER_POWDER, 1));
		
		this.addGrindingRecipe(ModBlocks.LEAD_ORE, new ItemStack(ModItems.LEAD_ORE_POWDER, 2));
		this.addGrindingRecipe(ModItems.LEAD_INGOT, new ItemStack(ModItems.LEAD_POWDER, 1));
		
		this.addGrindingRecipe(ModBlocks.TIN_ORE, new ItemStack(ModItems.TIN_ORE_POWDER, 2));
		this.addGrindingRecipe(ModItems.TIN_INGOT, new ItemStack(ModItems.TIN_POWDER, 1));
		
		this.addGrindingRecipe(ModBlocks.MYTHINIUM_ORE, new ItemStack(ModItems.MYTHINIUM_ORE_POWDER, 2));
		this.addGrindingRecipe(ModItems.MYTHINIUM_INGOT, new ItemStack(ModItems.MYTHINIUM_POWDER, 1));
	}
	
	public void addGrindingRecipe(Item input, ItemStack stack)
	{
		addGrindingRecipe(new ItemStack(input, 1, 32767), stack);
	}
	
	public void addGrindingRecipe(Block input, ItemStack stack)
	{
		addGrindingRecipe(Item.getItemFromBlock(input), stack);
	}
	
	public void addGrindingRecipe(ItemStack input, ItemStack stack)
	{
		//Same output for the same input
		if (getGrindingResult(input) != ItemStack.EMPTY)
		{
			return;
		}
		grindingList.put(input, stack);
	}
	
	public ItemStack getGrindingResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack> entry : this.grindingList.entrySet())
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
