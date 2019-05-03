package aqoursoro.teleportcraft.command;

import java.util.ArrayList;
import java.util.List;

import aqoursoro.teleportcraft.capability.teleporter.CapabilityTeleportHandler;
import aqoursoro.teleportcraft.capability.teleporter.EnumTeleportStatus;
import aqoursoro.teleportcraft.capability.teleporter.ITeleportHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandTeleportReset extends CommandBase
{

	private final List<String> aliases;

	public CommandTeleportReset()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("teleport_reset");
	}

	@Override
	public String getName()
	{
		return "teleport_reset";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return null;
	}

	@Override
	public List<String> getAliases()
	{
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP entity = getCommandSenderAsPlayer(sender);

		if (entity.hasCapability(CapabilityTeleportHandler.TELEPORT_CAPABILITY, null))
		{
			ITeleportHandler handler = ((ITeleportHandler)entity.getCapability(CapabilityTeleportHandler.TELEPORT_CAPABILITY, null));
			handler.setOnTeleporter(false);
			handler.setTeleportStatus(EnumTeleportStatus.INACTIVE);
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender.canUseCommand(2, this.getName());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		return null;
	}

}
