package aqoursoro.teleportcraft.capability.mythiniumenergy;

import java.util.HashSet;

import aqoursoro.teleportcraft.capability.electricenergy.ElectricEnergyNetManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityMythiniumEnergyNetManager 
{
	@CapabilityInject(MythiniumEnergyNetManager.class)
	public static Capability<MythiniumEnergyNetManager> MYTHINIUM_ENERGY_NET = null;
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(MythiniumEnergyNetManager.class, new IStorage<MythiniumEnergyNetManager>() 
		{

			@Override
			public NBTTagList writeNBT(Capability<MythiniumEnergyNetManager> capability, MythiniumEnergyNetManager instance, EnumFacing side) 
			{
				final NBTTagList nbtTagList = new NBTTagList();
				for(final BlockPos pos : instance.getCables())
				{
					final NBTTagCompound compound = new NBTTagCompound();
					compound.setLong("pos", pos.toLong());
					nbtTagList.appendTag(compound);
				}
				
				return nbtTagList;
			}

			@Override
			public void readNBT(Capability<MythiniumEnergyNetManager> capability, MythiniumEnergyNetManager instance, EnumFacing side, NBTBase nbt) 
			{
				final MythiniumEnergyNetManager net = instance;
				
				final NBTTagList tagList = (NBTTagList) nbt;
				
				final HashSet<BlockPos> connectedCables = new HashSet<>();
				
				for (int i = 0; i < tagList.tagCount(); i++) 
				{
					final NBTTagCompound compound = tagList.getCompoundTagAt(i);

					final long posLong = compound.getLong("pos");
					final BlockPos pos = BlockPos.fromLong(posLong);

					connectedCables.add(pos);
				}
				net.getCables().clear();
				net.getCables().addAll(connectedCables);
				
				net.refreshCables();
				
			}
			

			
			
		}, () -> new MythiniumEnergyNetManager(null));
		
	}
}
