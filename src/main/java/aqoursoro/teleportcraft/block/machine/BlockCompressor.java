package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityCompressor;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCompressor extends BlockMachine
{
	
	
	public BlockCompressor(@Nonnull final String name)
	{
		super(name, ModGuiHandler.COMPRESSOR);
	}
	 public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	    {
			return new ItemStack(ModBlocks.COMPRESSOR);
	    }
	
	 @Nullable
		@Override
		public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
		{
			return new TileEntityCompressor();
		}
	 
	 @Override
		public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
		{
	        
			TileEntityCompressor tileentity = (TileEntityCompressor)worldIn.getTileEntity(pos);
	        super.breakBlock(worldIn, pos, state);
		}
	 
	 public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
		{
			IBlockState state = worldIn.getBlockState(pos);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if(active) 
			{
				worldIn.setBlockState(pos, ModBlocks.COMPRESSOR.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
			}
			else 
			{
				worldIn.setBlockState(pos, ModBlocks.COMPRESSOR.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
			}
			
			if(tileentity != null) 
			{
				tileentity.validate();
				worldIn.setTileEntity(pos, tileentity);
			}
		}
}