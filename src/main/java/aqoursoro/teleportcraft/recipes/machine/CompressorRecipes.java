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

public class CompressorRecipes {
private static final CompressorRecipes INSTANCE = new CompressorRecipes();
	
	private final Map<ItemStack, ItemStack> compressingList = Maps.<ItemStack, ItemStack>newHashMap();
	
	
	public static CompressorRecipes instance()
    {
        return INSTANCE;
    }
	private CompressorRecipes()
	{
		this.addCompressingRecipe(ModItems.MYTHINIUM_INGOT, new ItemStack(ModBlocks.MYTHINIUM_BLOCK, 1));
		
		this.addCompressingRecipe(ModBlocks.MYTHINIUM_BLOCK, new ItemStack(ModBlocks.MYTHINIUM_X_BLOCK, 1));
		
		this.addCompressingRecipe(ModItems.COPPER_INGOT, new ItemStack(ModBlocks.COPPER_ORE, 1));
		
		this.addCompressingRecipe(ModItems.LEAD_INGOT, new ItemStack(ModBlocks.LEAD_ORE, 1));
		
		this.addCompressingRecipe(Items.IRON_INGOT, new ItemStack(Blocks.IRON_ORE, 1));
		
		this.addCompressingRecipe(ModItems.TIN_INGOT, new ItemStack(ModBlocks.TIN_ORE, 1));
		
	}
	
	public void addCompressingRecipe(Item input, ItemStack stack)
	{
		addCompressingRecipe(new ItemStack(input, 1, 32767), stack);
	}
	
	public void addCompressingRecipe(Block input, ItemStack stack)
	{
		addCompressingRecipe(Item.getItemFromBlock(input), stack);
	}
	
	public void addCompressingRecipe(ItemStack input, ItemStack stack)
	{
		//Same output for the same input
		if (getCompressingResult(input) != ItemStack.EMPTY)
		{
			return;
		}
		compressingList.put(input, stack);
	}
	
	public ItemStack getCompressingResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack> entry : this.compressingList.entrySet())
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

