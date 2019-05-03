package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityItemSite;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockItemSite extends BlockMachine
{

	protected static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	protected static final PropertyBool WORKING = PropertyBool.create("working");
	
	public BlockItemSite(String name) {
		super(name, ModGuiHandler.ITEM_SITE);				
	}
	
	//discriminated method
	
		@Nullable
		@Override
		public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
		{
			return new TileEntityItemSite();
		}
		
		
		//discriminated method
		@Override
		public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
		{
	        
			TileEntityItemSite tileentity = (TileEntityItemSite)worldIn.getTileEntity(pos);
	        super.breakBlock(worldIn, pos, state);
		}
		
		//discriminated method
		@Override
	    @SideOnly(Side.CLIENT)
	    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	    {
			return new ItemStack(ModBlocks.ITEM_SITE);
	    }
		
		public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
		{
			IBlockState state = worldIn.getBlockState(pos);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if(active) 
			{
				worldIn.setBlockState(pos, ModBlocks.ITEM_SITE.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
			}
			else 
			{
				worldIn.setBlockState(pos, ModBlocks.ITEM_SITE.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
			}
			
			if(tileentity != null) 
			{
				tileentity.validate();
				worldIn.setTileEntity(pos, tileentity);
			}
		}
		

}
