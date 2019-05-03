package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityStamper;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStamper extends BlockMachine{

	public BlockStamper(@Nonnull final String name) {
		super(name, ModGuiHandler.STAMPER);
	}	
	
	@Nullable
	@Override
	public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
	{
		return new TileEntityStamper();
	}
	
	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		
		TileEntityStamper tileentity = (TileEntityStamper)worldIn.getTileEntity(pos);
            
      for(int i = 0; i < TileEntityStamper.SLOT_NUM; i ++)
      {
      	worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.handler.getStackInSlot(i)));
      }
        super.breakBlock(worldIn, pos, state);
	}
	
	public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) 
		{
			worldIn.setBlockState(pos, ModBlocks.STAMPER.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, ModBlocks.STAMPER.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		return new ItemStack(ModBlocks.STAMPER);
    }
}
