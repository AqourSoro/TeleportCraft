package aqoursoro.teleportcraft.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChannelMotherBoard extends ItemGeneral
{

	public ItemChannelMotherBoard(String name) 
	{
		super(name);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		tooltip.add("Channel mother board");
	}
}
