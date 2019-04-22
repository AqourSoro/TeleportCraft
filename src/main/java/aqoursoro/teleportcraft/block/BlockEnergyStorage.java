package aqoursoro.teleportcraft.block;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.tileentity.TileEntityEnergyStorage;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import aqoursoro.teleportcraft.util.ModUtil;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyStorage extends Block
{
	public BlockEnergyStorage(@Nonnull final String name)
	{
		super(Material.IRON);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		this.setSoundType(SoundType.STONE);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityEnergyStorage();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(TeleportCraft.instance, ModGuiHandler.ENERGY_STORAGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
}
