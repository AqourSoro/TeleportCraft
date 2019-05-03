package aqoursoro.teleportcraft.network;

import java.io.IOException;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.mythiniumenergy.CapabilityMythiniumEnergyNetManager;
import aqoursoro.teleportcraft.capability.mythiniumenergy.MythiniumEnergyNetManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPktSynMythiniumNetList implements IMessage, IMessageHandler<SPktSynMythiniumNetList, IMessage> 
{
	private NBTTagCompound synmTag;
	
	public SPktSynMythiniumNetList()
	{
		
	}
	
	public SPktSynMythiniumNetList(final NBTBase syncTag)
	{
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("synmTag", syncTag);
		this.synmTag = compound;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		final PacketBuffer packet = new PacketBuffer(buf);
		try {
			this.synmTag = packet.readCompoundTag();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		final PacketBuffer packet = new PacketBuffer(buf);
		packet.writeCompoundTag(this.synmTag);

	}

	@Override
	public IMessage onMessage(SPktSynMythiniumNetList message, MessageContext ctx) 
	{
		if ((message.synmTag != null) && message.synmTag.hasKey("synTag")) 
		{
			// WIPTech.info(message.syncTag.getTag("syncTag"));
			Minecraft.getMinecraft().addScheduledTask(() -> 
			{
				if (Minecraft.getMinecraft() == null) 
				{
					return;
				}
				if (Minecraft.getMinecraft().world == null) 
				{
					return;
				}

				final MythiniumEnergyNetManager list = Minecraft.getMinecraft().world.getCapability(CapabilityMythiniumEnergyNetManager.MYTHINIUM_ENERGY_NET, null);
				if (list == null) {
					return;
				}

				CapabilityMythiniumEnergyNetManager.MYTHINIUM_ENERGY_NET.getStorage().readNBT(CapabilityMythiniumEnergyNetManager.MYTHINIUM_ENERGY_NET, list, null, message.synmTag.getTag("synTag"));
			});
		}
		return null;

	}
}
