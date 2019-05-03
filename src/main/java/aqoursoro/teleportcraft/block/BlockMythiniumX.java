package aqoursoro.teleportcraft.block;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs.CustomCreativeTab;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMythiniumX extends Block
{

	public BlockMythiniumX(@Nonnull final String name) 
	{
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		this.setSoundType(SoundType.METAL);
		this.setHardness(10.0f);
		this.setResistance(90.0f);
		this.setHarvestLevel("pickaxe", 3);
		this.setLightLevel(1.0f);
	}

}
