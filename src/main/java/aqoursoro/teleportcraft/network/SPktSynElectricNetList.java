package aqoursoro.teleportcraft.network;

import java.io.IOException;

import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergyNetManager;
import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPktSynElectricNetList implements IMessage, IMessageHandler<SPktSynElectricNetList, IMessage> {

	private NBTTagCompound synTag;
	
	public SPktSynElectricNetList()
	{
		
	}
	
	public SPktSynElectricNetList(final NBTBase syncTag)
	{
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("synTag", syncTag);
		this.synTag = compound;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		final PacketBuffer packet = new PacketBuffer(buf);
		try {
			this.synTag = packet.readCompoundTag();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		final PacketBuffer packet = new PacketBuffer(buf);
		packet.writeCompoundTag(this.synTag);

	}

	@Override
	public IMessage onMessage(SPktSynElectricNetList message, MessageContext ctx) 
	{
		if ((message.synTag != null) && message.synTag.hasKey("synTag")) 
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

				final ElectricEnergyNetManager list = Minecraft.getMinecraft().world.getCapability(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, null);
				if (list == null) {
					return;
				}

				CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET.getStorage().readNBT(CapabilityElectricEnergyNetManager.ELECTRIC_ENERGY_NET, list, null, message.synTag.getTag("synTag"));
			});
		}
		return null;

	}


}
