package aqoursoro.teleportcraft.util;

@SuppressWarnings("WeakerAccess")
public class Reference 
{
	public static final String MOD_ID = "tpcraft";
	
	public static final String MOD_NAME = "Teleport Craft";
	
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	
	public static final String DEPENDENCIES = "" +
			"required-after:minecraft;" +
			"required-after:forge@[14.23.5.2768,);" +
			"";
	
	public static final String VERSION = "@VERSION@";
	
	public static final String CERTIFICATE_FINGERPRINT = "@FINGERPRINT@";
	
	public static final String CLIENT_PROXY_CLASS = "aqoursoro.teleportcraft.client.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "aqoursoro.teleportcraft.server.ServerProxy";

}
