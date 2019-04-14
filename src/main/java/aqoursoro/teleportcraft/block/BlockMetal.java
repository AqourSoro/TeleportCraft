package aqoursoro.teleportcraft.block;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockMetal extends Block
{
	public BlockMetal(@Nonnull final String name)
	{
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		this.setSoundType(SoundType.METAL);
		this.setHardness(3.0f);
		this.setHarvestLevel("pickaxe", 2);
		
	}
}
