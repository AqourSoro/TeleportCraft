package aqoursoro.teleportcraft.block.machine;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import aqoursoro.teleportcraft.util.ModUtil;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityCore;



public class BlockCore extends BlockMachine
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	private static final PropertyBool WORKING = PropertyBool.create("working");

	public BlockCore(@Nonnull final String name) 
	{
		super(name, ModGuiHandler.CORE);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
//		this.setDefaultState(blockState.getBaseState().withProperty(WORKING, false));

	}
	
//	
//	@Override
//	public boolean hasTileEntity(IBlockState state) 
//	{
//		return true;
//	}
	
	@Override
	public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state)
	{
		return new TileEntityCore();
	}
	
	
	public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) 
		{
			worldIn.setBlockState(pos, ModBlocks.CORE.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, ModBlocks.CORE.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
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
		return new ItemStack(ModBlocks.CORE);
    }
	
//	@Override
//	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
//	{
//		return Item.getItemFromBlock(ModBlocks.CORE_BLOCK);
//	}
//	
//	@Override
//	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
//	{
//		return new ItemStack(ModBlocks.CORE_BLOCK);
//	}
//	
//	@Override
//	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
//	{
//		if(!worldIn.isRemote)
//		{
//			playerIn.openGui(TeleportCraft.instance, ModGuiHandler.CORE_BLOCK, worldIn, pos.getX(), pos.getY(), pos.getZ());
//		}
//		
//		return true;
//	}
//	
//	
//	@Override
//	public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
//	{
//		return new TileEntityCore();
//	}
//	
//	public static void setState(boolean active, @Nonnull World worldIn, @Nonnull BlockPos pos) 
//	{
//		IBlockState state = worldIn.getBlockState(pos);
//		TileEntity tileentity = worldIn.getTileEntity(pos);
//		
//		/*
//		 * if(active)
//		 * {
//			worldIn.setBlockState(pos, ModBlocks.CORE_BLOCK.getDefaultState().withProperty(WORKING, true), 3);
//		}
//		else 
//		{
//			worldIn.setBlockState(pos, ModBlocks.CORE_BLOCK.getDefaultState().withProperty(WORKING, false), 3);
//		}
//		
//		 */
//		
//		
//		if(tileentity != null) 
//		{
//			tileentity.validate();
//			worldIn.setTileEntity(pos, tileentity);
//		}
//	}
}
