package aqoursoro.teleportcraft.recipes.machine;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import aqoursoro.teleportcraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BoardProducerRecipes {

	private static final BoardProducerRecipes INSTANCE = new BoardProducerRecipes();
	
	private final Map<ItemStack[], ItemStack> producingList = Maps.newHashMap();
//	private ItemStack[] inputs = new ItemStack[2];
	
	public static BoardProducerRecipes instance()
    {
        return INSTANCE;
    }
	
	private BoardProducerRecipes() {
		this.addProducingRecipe(ModItems.COPPER_INGOT, ModItems.MYTHINIUM_POWDER, new ItemStack(ModItems.MOTHER_BOARD, 1));
	}
	
	public void addProducingRecipe(Item input1, Item input2,ItemStack stack)
	{
		addProducingRecipe(new ItemStack(input1, 1, 32767), new ItemStack(input2, 1, 32767),stack);
	}
	
//	public void addProducingRecipe(Block input, ItemStack stack)
//	{
//		addProducingRecipe(Item.getItemFromBlock(input), stack);
//	}
	
	public void addProducingRecipe(ItemStack input1, ItemStack input2, ItemStack stack)
	{
		//Same output for the same input
		if (getProducingResult(input1, input2) != ItemStack.EMPTY)
		{
			return;
		}
		ItemStack[] inputs = {input1, input2};
		producingList.put(inputs, stack);//how to add...
	}
	
	public ItemStack getProducingResult(ItemStack stack1, ItemStack stack2) {
		for (Entry<ItemStack[], ItemStack> entry : this.producingList.entrySet())//...
        {
			
			ItemStack[] inputEntry = entry.getKey();
            if (this.compareItemStacks(stack1, inputEntry[0]) && this.compareItemStacks(stack2, inputEntry[1]))
            {
                return entry.getValue();
            }
        }
		return ItemStack.EMPTY;
	}
	
	public boolean haveProducingResult(ItemStack stack)
	{
		for (Entry<ItemStack[], ItemStack> entry : this.producingList.entrySet())//...
        {
			ItemStack[] inputEntry = entry.getKey();
			
			if(this.compareItemStacks(stack, inputEntry[0]) || this.compareItemStacks(stack, inputEntry[1])) {
				return true;
			}		
        }
		return false;
	}
	
	
	//Common
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}
