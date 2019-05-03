package aqoursoro.teleportcraft.item;

import aqoursoro.teleportcraft.block.BlockTeleporter;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTeleporter extends ItemBlock
{

	public ItemBlockTeleporter(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	@Override
	public String getTranslationKey(ItemStack stack)
	{
		return "tile." + BlockTeleporter.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
	}
}
