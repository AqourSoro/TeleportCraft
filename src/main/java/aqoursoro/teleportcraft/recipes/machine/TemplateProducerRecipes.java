package aqoursoro.teleportcraft.recipes.machine;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TemplateProducerRecipes {

private static final TemplateProducerRecipes INSTANCE = new TemplateProducerRecipes();
	
	private final Map<ItemStack[], ItemStack> tempProducingList = Maps.newHashMap();
	
	public static TemplateProducerRecipes instance()
    {
        return INSTANCE;
    }
	
	private TemplateProducerRecipes()
	{
		addProducingRecipe(new ItemStack(ModItems.COPPER_INGOT), new ItemStack(ModItems.MYTHINIUM_POWDER), new ItemStack(ModItems.TIN_INGOT), new ItemStack(ModItems.CHANNEL_TEMPLATE));
		addProducingRecipe(new ItemStack(ModItems.COPPER_INGOT), new ItemStack(ModItems.MYTHINIUM_POWDER), new ItemStack(ModItems.LEAD_INGOT), new ItemStack(ModItems.ID_TEMPLATE));
	}
	
	public void addProducingRecipe(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack result) 
	{
		if(getProducingResult(input1, input2, input3) != ItemStack.EMPTY) return;
		ItemStack[] inputs = {input1, input2, input3};
		tempProducingList.put(inputs, result);
	}
	
	public boolean haveProducingResult(ItemStack newStack) {
		for (Entry<ItemStack[], ItemStack> entry : this.tempProducingList.entrySet())//...
        {
			ItemStack[] inputEntry = entry.getKey();
			
			if(this.compareItemStacks(newStack, inputEntry[0]) || this.compareItemStacks(newStack, inputEntry[1]) || this.compareItemStacks(newStack, inputEntry[2])) {
				return true;
			}		
        }
		return false;
	}
	
	
	public ItemStack getProducingResult(ItemStack input1, ItemStack input2, ItemStack input3)
	{
		for (Entry<ItemStack[], ItemStack> entry : this.tempProducingList.entrySet())//...
        {
			
			ItemStack[] inputEntry = entry.getKey();
            if (this.compareItemStacks(input1, inputEntry[0]) && this.compareItemStacks(input2, inputEntry[1]) && this.compareItemStacks(input3, inputEntry[2]))
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
