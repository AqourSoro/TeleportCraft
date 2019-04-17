package aqoursoro.teleportcraft.recipes.smelting;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipes
{
	public static void initSmelting()
	{
		GameRegistry.addSmelting(ModBlocks.MYTHINIUM_ORE, new ItemStack(ModItems.MYTHINIUM_INGOT, 1), 1.2f);
		GameRegistry.addSmelting(ModItems.MYTHINIUM_ORE_POWDER, new ItemStack(ModItems.MYTHINIUM_INGOT, 1), 1f);
		GameRegistry.addSmelting(ModItems.MYTHINIUM_POWDER, new ItemStack(ModItems.MYTHINIUM_INGOT, 1), 0.5f);
		
		GameRegistry.addSmelting(ModBlocks.COPPER_ORE, new ItemStack(ModItems.COPPER_INGOT, 1), 0.7f);
		GameRegistry.addSmelting(ModItems.COPPER_ORE_POWDER, new ItemStack(ModItems.COPPER_INGOT, 1), 0.5f);
		GameRegistry.addSmelting(ModItems.COPPER_POWDER, new ItemStack(ModItems.COPPER_INGOT, 1), 0.3f);
		
		GameRegistry.addSmelting(ModBlocks.TIN_ORE, new ItemStack(ModItems.TIN_INGOT, 1), 0.7f);
		GameRegistry.addSmelting(ModItems.TIN_ORE_POWDER, new ItemStack(ModItems.TIN_INGOT, 1), 0.5f);
		GameRegistry.addSmelting(ModItems.TIN_POWDER, new ItemStack(ModItems.TIN_INGOT, 1), 0.3f);
		
		GameRegistry.addSmelting(ModBlocks.LEAD_ORE, new ItemStack(ModItems.LEAD_INGOT, 1), 0.9f);
		GameRegistry.addSmelting(ModItems.LEAD_ORE_POWDER, new ItemStack(ModItems.LEAD_INGOT, 1), 0.7f);
		GameRegistry.addSmelting(ModItems.LEAD_POWDER, new ItemStack(ModItems.LEAD_INGOT, 1), 0.4f);
		
		
	}
	
}
