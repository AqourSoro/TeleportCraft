package aqoursoro.teleportcraft.util;

import aqoursoro.teleportcraft.client.gui.GuiElectricGrinder;
import aqoursoro.teleportcraft.client.gui.GuiThermalElectricGenerator;
import aqoursoro.teleportcraft.inventory.container.ContainerElectricGrinder;
import aqoursoro.teleportcraft.inventory.container.ContainerThermalElectricGenerator;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityThermalElectricGenerator;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ModGuiHandler implements IGuiHandler 
{

	public static final int THERMAL_ELECTRIC_GENERATOR= 0;
	
	public static final int ELECTRIC_GRINDER = 1;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) 
		{
			case THERMAL_ELECTRIC_GENERATOR:
				return new ContainerThermalElectricGenerator(player.inventory, (TileEntityThermalElectricGenerator)world.getTileEntity(pos));
			case ELECTRIC_GRINDER:
				return new ContainerElectricGrinder(player.inventory, (TileEntityElectricGrinder)world.getTileEntity(pos));
			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Gui getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) 
		{
			case THERMAL_ELECTRIC_GENERATOR:
				return new GuiThermalElectricGenerator(player.inventory, (TileEntityThermalElectricGenerator)world.getTileEntity(pos));
			case ELECTRIC_GRINDER:
				return new GuiElectricGrinder(player.inventory, (TileEntityElectricGrinder)world.getTileEntity(pos));
			default:
				return null;
		}
	}

}
