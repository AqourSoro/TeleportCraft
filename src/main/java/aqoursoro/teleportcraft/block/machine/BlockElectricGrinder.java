package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockElectricGrinder extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool WORKING = PropertyBool.create("working");
	
	private static boolean hasTileEntity;
	
	public BlockElectricGrinder(@Nonnull final String name)
	{
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setTickRandomly(false);
		this.lightOpacity = 20;
		this.useNeighborBrightness = false;
		
	}
	
	
	@Override
	public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		if(!worldIn.isRemote)
		{
			IBlockState toNorth = worldIn.getBlockState(pos.north());
			IBlockState toSouth = worldIn.getBlockState(pos.south());
			IBlockState toEast = worldIn.getBlockState(pos.east());
			IBlockState toWest = worldIn.getBlockState(pos.west());
			
			EnumFacing facing = (EnumFacing)state.getValue(FACING);
			
			if (facing == EnumFacing.NORTH && toNorth.isFullBlock() && !toSouth.isFullBlock())
            {
                facing = EnumFacing.SOUTH;
            }
            else if (facing == EnumFacing.SOUTH && toSouth.isFullBlock() && !toNorth.isFullBlock())
            {
                facing = EnumFacing.NORTH;
            }
            else if (facing == EnumFacing.WEST && toEast.isFullBlock() && !toWest.isFullBlock())
            {
                facing = EnumFacing.EAST;
            }
            else if (facing == EnumFacing.EAST && toWest.isFullBlock() && !toEast.isFullBlock())
            {
                facing = EnumFacing.WEST;
            }
			
			worldIn.setBlockState(pos, state.withProperty(FACING, facing), 2);
		}
		
	}
	
	//discriminated method
	@Override
	public boolean onBlockActivated(@Nonnull World worldIn,@Nonnull BlockPos pos,@Nonnull IBlockState state, 
			@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, 
			@Nonnull final float hitX,  @Nonnull final float hitY, @Nonnull final float hitZ)
	{
		if(!worldIn.isRemote)
		{
			//openGui here.
		}
		
		return true;
	}
	
	//discriminated method
	@Nullable
	@Override
	public TileEntity createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
	{
		return new TileEntityElectricGrinder();
	}
	
	@Override
    public IBlockState getStateForPlacement(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, 
    		@Nonnull float hitX, @Nonnull float hitY, @Nonnull float hitZ, 
    		@Nonnull int meta, @Nonnull EntityLivingBase placer)
    {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override
	public void onBlockPlacedBy(@Nonnull World worldIn, BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase placer, @Nonnull ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        
    }
	
	//discriminated method
	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		if (!hasTileEntity)
        {
			TileEntityElectricGrinder tileentity = (TileEntityElectricGrinder)worldIn.getTileEntity(pos);
            
            for(int i = 0; i < TileEntityElectricGrinder.SLOT_NUM; i ++)
            {
            	worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileentity.handler.getStackInSlot(i)));
            }
        }

        super.breakBlock(worldIn, pos, state);
	}
	
	//discriminated method
	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		return new ItemStack(ModBlocks.ELECTRIC_GRINDER);
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }
    
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {WORKING, FACING});
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) 
		{
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_GRINDER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(WORKING, true), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_GRINDER.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(WORKING, false), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
}
