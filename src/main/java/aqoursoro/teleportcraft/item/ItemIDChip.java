package aqoursoro.teleportcraft.item;

import java.util.List;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIDChip extends Item
{
	private int identification;
	
	public ItemIDChip(@Nonnull final String name)
	{
		ModUtil.setRegistryNames(this, name);
		ModUtil.setCreativeTab(this);
		TeleportCraft.ID_LIST.add(this);
		
		this.identification = 0;
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		if(!worldIn.isRemote)
		{
			ItemStack stack = playerIn.getHeldItem(handIn);
			NBTTagCompound nbt = stack.getTagCompound();
			
			if(playerIn.isSneaking())
			{
				if(nbt == null || !nbt.hasKey("ID"))
				{
					playerIn.sendMessage(new TextComponentString("ID chip: null"));
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("ID chip: " + nbt.getInteger("ID")));
					System.out.println("System ID chip: " + nbt.getInteger("ID"));
				}
			}
			else
			{				
				if(nbt == null)
				{
					nbt = new NBTTagCompound();
				}
				
				if(nbt.getInteger("ID") >= 10)
				{
					this.identification = 0;
				}
				
				this.identification ++;
				
				nbt.setInteger("ID", this.identification);
				stack.setTagCompound(nbt);
			}
		}
		
		return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) 
	{
		if(stack.hasTagCompound())
		{
			if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("ID"))
			{
				tooltip.add("ID chip : " + stack.getTagCompound().getInteger("ID"));
			}
			else
			{
				tooltip.add("ID chip : ?");
			}	
		}
	}
}
