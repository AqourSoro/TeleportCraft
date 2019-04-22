package aqoursoro.teleportcraft.util;

import aqoursoro.teleportcraft.client.gui.GuiElectricGrinder;
import aqoursoro.teleportcraft.client.gui.GuiElectricSinteringFurnace;
import aqoursoro.teleportcraft.client.gui.GuiEnergyStorage;
import aqoursoro.teleportcraft.client.gui.GuiTestFurnace;
import aqoursoro.teleportcraft.client.gui.GuiThermalElectricGenerator;
import aqoursoro.teleportcraft.inventory.container.ContainerElectricGrinder;
import aqoursoro.teleportcraft.inventory.container.ContainerElectricSinteringFurnace;
import aqoursoro.teleportcraft.inventory.container.ContainerEnergyStorage;
import aqoursoro.teleportcraft.inventory.container.ContainerTestFurnace;
import aqoursoro.teleportcraft.inventory.container.ContainerThermalElectricGenerator;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricSinteringFurnace;
import aqoursoro.teleportcraft.tileentity.TileEntityEnergyStorage;
import aqoursoro.teleportcraft.tileentity.TileEntityTestFurnace;
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
	
	public static final int ENERGY_STORAGE = 2;
	
	public static final int ELECTRIC_SINTERING_FURNACE = 3;
	
	public static final int TEST_FURNACE = 4;
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
			case ENERGY_STORAGE:
				return new ContainerEnergyStorage(player.inventory, (TileEntityEnergyStorage)world.getTileEntity(pos));
			case ELECTRIC_SINTERING_FURNACE:
				return new ContainerElectricSinteringFurnace(player.inventory, (TileEntityElectricSinteringFurnace)world.getTileEntity(pos));
			case TEST_FURNACE:
				return new ContainerTestFurnace(player.inventory, (TileEntityTestFurnace)world.getTileEntity(pos));
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
			case ENERGY_STORAGE:
				return new GuiEnergyStorage(player.inventory, (TileEntityEnergyStorage)world.getTileEntity(pos));
			case ELECTRIC_SINTERING_FURNACE:
				return new GuiElectricSinteringFurnace(player.inventory, (TileEntityElectricSinteringFurnace)world.getTileEntity(pos));
			case TEST_FURNACE:
				return new GuiTestFurnace(player.inventory, (TileEntityTestFurnace)world.getTileEntity(pos));
			default:
				return null;
		}
	}

}
