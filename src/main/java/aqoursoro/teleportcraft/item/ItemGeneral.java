package aqoursoro.teleportcraft.item;

import java.util.List;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class ItemGeneral extends Item
{
	public ItemGeneral(@Nonnull String name)
	{
		ModUtil.setRegistryNames(this, name);
		ModUtil.setCreativeTab(this);
	}

}
