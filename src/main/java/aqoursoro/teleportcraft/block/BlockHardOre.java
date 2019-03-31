package aqoursoro.teleportcraft.block;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockHardOre extends Block
{
	public BlockHardOre(@Nonnull final String name) 
	{
		super(Material.ROCK);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		this.setHardness(3.0f);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 2);
	}
}
