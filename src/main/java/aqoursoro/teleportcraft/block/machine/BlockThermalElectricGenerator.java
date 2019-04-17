package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityThermalElectricGenerator;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockThermalElectricGenerator extends BlockMachine
{
	public BlockThermalElectricGenerator(@Nonnull final String name)
	{
		super(name, ModGuiHandler.THERMAL_ELECTRIC_GENERATOR);
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
	{
		return new TileEntityThermalElectricGenerator();
	}
	
	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
        
		TileEntityThermalElectricGenerator tileentity = (TileEntityThermalElectricGenerator)worldIn.getTileEntity(pos);
        super.breakBlock(worldIn, pos, state);
	}
	
}
