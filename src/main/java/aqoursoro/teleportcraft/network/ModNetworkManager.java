package aqoursoro.teleportcraft.network;

import static aqoursoro.teleportcraft.util.Reference.MOD_ID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public final class ModNetworkManager 
{
	//Used for MC Forge network channel, each channel is used for a single mod, 
	public static final String CHANNEL = StringUtils.substring(MOD_ID, 0, 20);
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
	private static final Logger LOGGER = LogManager.getLogger();
	
	static
	{
		if (CHANNEL.length() != MOD_ID.length()) 
		{
			// Network Channel CAN'T be longer than 20 characters due to Minecraft & Forge's packet system
			LOGGER.error("Network Channel was clamped to 20 characters! {}, {}", CHANNEL, MOD_ID);
		}
	}
	
	public ModNetworkManager()
	{
		int networkIds = 0;

		// Client -> Server
		//NETWORK.registerMessage(CPacket___.class, CPacket___.class, networkIds++, Side.SERVER);
		//Server -> Client 
		//NETWORK.registerMessage(SPacket___.class, SPacket___.class, networkIds++, Side.CLIENT);

	}
}
