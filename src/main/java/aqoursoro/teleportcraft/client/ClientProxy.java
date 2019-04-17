package aqoursoro.teleportcraft.client;

import aqoursoro.teleportcraft.util.IProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.resources.I18n;

public final class ClientProxy implements IProxy
{

	@Override
	public String localize(final String unlocalized) 
	{
		return this.lcalizeAndFormat(unlocalized);
	}

	@Override
	public String lcalizeAndFormat(final String unlocalized, final Object... args) 
	{
		// TODO Auto-generated method stub
		return I18n.format(unlocalized, args);
	}

	@Override
	public Side getPhysicalSide() 
	{
		return Side.CLIENT;
	}

}
