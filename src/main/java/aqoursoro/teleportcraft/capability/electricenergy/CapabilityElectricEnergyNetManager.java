package aqoursoro.teleportcraft.capability.electricenergy;

import java.util.HashSet;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityElectricEnergyNetManager 
{
	@CapabilityInject(ElectricEnergyNetManager.class)
	public static Capability<ElectricEnergyNetManager> ELECTRIC_ENERGY_NET = null;
	
	public static void register()
	{
		CapabilityManager.INSTANCE.register(ElectricEnergyNetManager.class, new IStorage<ElectricEnergyNetManager>() 
		{

			@Override
			public NBTTagList writeNBT(Capability<ElectricEnergyNetManager> capability, ElectricEnergyNetManager instance, EnumFacing side) 
			{
				final NBTTagList nbtTagList = new NBTTagList();
				for(final BlockPos pos : instance.getCableNet())
				{
					final NBTTagCompound compound = new NBTTagCompound();
					compound.setLong("pos", pos.toLong());
					nbtTagList.appendTag(compound);
				}
				
				return nbtTagList;
			}

			@Override
			public void readNBT(Capability<ElectricEnergyNetManager> capability, ElectricEnergyNetManager instance, EnumFacing side, NBTBase nbt) 
			{
				final ElectricEnergyNetManager net = instance;
				
				final NBTTagList tagList = (NBTTagList) nbt;
				
				final HashSet<BlockPos> connectedCables = new HashSet<>();
				
				for (int i = 0; i < tagList.tagCount(); i++) 
				{
					final NBTTagCompound compound = tagList.getCompoundTagAt(i);

					final long posLong = compound.getLong("pos");
					final BlockPos pos = BlockPos.fromLong(posLong);

					connectedCables.add(pos);
				}
				net.getCableNet().addAll(connectedCables);
				
			}
			

			
			
		}, () -> new ElectricEnergyNetManager(null));
		
	}
}
