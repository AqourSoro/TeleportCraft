package aqoursoro.teleportcraft.item;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.item.Item;

public class ItemGeneral extends Item
{
	public ItemGeneral(@Nonnull String name)
	{
		ModUtil.setRegistryNames(this, name);
		ModUtil.setCreativeTab(this);
	}
}
