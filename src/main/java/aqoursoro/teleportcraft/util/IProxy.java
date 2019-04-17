package aqoursoro.teleportcraft.util;

import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

public interface IProxy 
{
	String localize(String unlocalized);
	
	String lcalizeAndFormat(String unlocalized, Object... args);
	
	default void logPhysicalSide(Logger logger) 
	{
		logger.debug("Physical Side: " + getPhysicalSide());
	}
	
	Side getPhysicalSide();
}
