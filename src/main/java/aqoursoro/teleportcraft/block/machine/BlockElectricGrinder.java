package aqoursoro.teleportcraft.block.machine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.util.ModGuiHandler;
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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockElectricGrinder extends BlockMachine
{
	
	
	public BlockElectricGrinder(@Nonnull final String name)
	{
		super(name, ModGuiHandler.ELECTRIC_GRINDER);
	}
	
	
	//discriminated method
	@Nullable
	@Override
	public TileEntityElectricGrinder createTileEntity(@Nonnull final World world, @Nonnull final IBlockState state) 
	{
		return new TileEntityElectricGrinder();
	}
	

	
	//discriminated method
	@Override
	public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
        
		TileEntityElectricGrinder tileentity = (TileEntityElectricGrinder)worldIn.getTileEntity(pos);
            
      for(int i = 0; i < TileEntityElectricGrinder.SLOT_NUM; i ++)
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
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_GRINDER.getDefaultState().withProperty(WORKING, true).withProperty(FACING, state.getValue(FACING)), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_GRINDER.getDefaultState().withProperty(WORKING, false).withProperty(FACING, state.getValue(FACING)), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	//discriminated method
	@Override
    @SideOnly(Side.CLIENT)
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		return new ItemStack(ModBlocks.ELECTRIC_GRINDER);
    }
	
}
