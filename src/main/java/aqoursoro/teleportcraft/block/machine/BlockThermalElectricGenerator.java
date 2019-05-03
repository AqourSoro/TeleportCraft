package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.init.ModBlocks;
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
	
	public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) 
		{
			worldIn.setBlockState(pos, ModBlocks.THERMAL_ELECTRIC_GENERATOR.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, ModBlocks.THERMAL_ELECTRIC_GENERATOR.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
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
