package aqoursoro.teleportcraft.server;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import aqoursoro.teleportcraft.util.IProxy;

public final class ServerProxy implements IProxy
{

	@Override
	public String localize(final String unlocalized) 
	{
		// TODO Auto-generated method stub
		return I18n.translateToLocal(unlocalized);
	}

	@Override
	public String lcalizeAndFormat(final String unlocalized, final Object... args) 
	{
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

	@Override
	public Side getPhysicalSide() 
	{
		return Side.SERVER;
	}

}
