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

public class ItemChannelChip extends Item
{
	private int channel;
	
	public ItemChannelChip(@Nonnull final String name)
	{
		ModUtil.setRegistryNames(this, name);
		ModUtil.setCreativeTab(this);
		TeleportCraft.CHANNEL_LIST.add(this);
		
		this.channel = 0;
		
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
				if(nbt == null || !nbt.hasKey("CHANNEL"))
				{
					playerIn.sendMessage(new TextComponentString("Channel chip: null"));
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("Channel chip: " + nbt.getInteger("CHANNEL")));
					System.out.println("System Channel chip: " + nbt.getInteger("CHANNEL"));
				}
			}
			else
			{				
				if(nbt == null)
				{
					nbt = new NBTTagCompound();
				}
				
				if(nbt.getInteger("CHANNEL") >= 10)
				{
					this.channel = 0;
				}
				
				this.channel ++;
				
				nbt.setInteger("CHANNEL", this.channel);
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
			if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("CHANNEL"))
			{
				tooltip.add("Channel chip : " + stack.getTagCompound().getInteger("CHANNEL"));
			}
			else
			{
				tooltip.add("Channel chip : ?");
			}	
		}
	}
}
