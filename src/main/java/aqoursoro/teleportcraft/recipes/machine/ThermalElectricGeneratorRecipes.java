package aqoursoro.teleportcraft.recipes.machine;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ThermalElectricGeneratorRecipes 
{
	private static final ThermalElectricGeneratorRecipes INSTANCE = new ThermalElectricGeneratorRecipes();
	
	private final Map<ItemStack, Integer>energyList = Maps.<ItemStack, Integer>newHashMap();
	
	public static ThermalElectricGeneratorRecipes instance()
    {
        return INSTANCE;
    }
	
	private ThermalElectricGeneratorRecipes()
	{
		
	}
	
	public void addGrindingRecipe(Item input, int energy)
	{
		addGrindingRecipe(new ItemStack(input, 1, 32767), energy);
	}
	
	public void addGrindingRecipe(ItemStack input, int energy)
	{
		//Same output for the same input
		if (getBurningElectricEnergy(input) != 0)
		{
			return;
		}
		energyList.put(input, energy);
	}
	
	public int getBurningElectricEnergy(ItemStack stack)
	{
		for (Entry<ItemStack, Integer> entry : this.energyList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }
		return 0;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}
