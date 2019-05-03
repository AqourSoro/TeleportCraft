package aqoursoro.teleportcraft.block.cable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricCable;
import aqoursoro.teleportcraft.tileentity.TileEntityMythiniumCable;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockMythiniumCable extends BlockCable
{
	public BlockMythiniumCable(@Nonnull final String name) 
	{
		super(Material.PISTON);
		this.setSoundType(SoundType.GLASS);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
	}
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, CONNECTED_PROPERTIES.toArray(new IProperty[CONNECTED_PROPERTIES.size()]));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(final int meta) 
	{
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(final IBlockState state) 
	{
		return 0;
	}
	
	@Override
	public boolean hasTileEntity() 
	{
		return true;
	}
	
	@Override
	public boolean hasTileEntity(@Nonnull IBlockState state) 
	{
	 return true;
	}
	
	@Nullable
	@Override
	public TileEntityMythiniumCable createTileEntity(final World world, final IBlockState state) 
	{
		return new TileEntityMythiniumCable();
	}
	
	@Override
	public void breakBlock(final World world, final BlockPos pos, final IBlockState state) 
	{
		if (world != null) 
		{
			final TileEntity tile = world.getTileEntity(pos);
			if (tile != null) 
			{
				if (tile instanceof TileEntityElectricCable) 
				{
					final TileEntityElectricCable wire = (TileEntityElectricCable) tile;
					world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null).removeCables(tile.getPos());
				}
			}
		}

		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void onBlockExploded(final World world, final BlockPos pos, final Explosion explosion) 
	{
		if (world != null)
		{
			final TileEntity tile = world.getTileEntity(pos);
			if (tile != null)
			{
				if (tile instanceof TileEntityElectricCable)
				{
					final TileEntityElectricCable wire = (TileEntityElectricCable) tile;
					world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null).removeCables(tile.getPos());
				}
			}
		}
		super.onBlockExploded(world, pos, explosion);
	}
}
