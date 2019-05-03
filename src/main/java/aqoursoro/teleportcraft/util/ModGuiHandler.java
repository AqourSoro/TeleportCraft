package aqoursoro.teleportcraft.util;

import aqoursoro.teleportcraft.client.gui.GuiBoardProducer;
import aqoursoro.teleportcraft.client.gui.GuiCompressor;
import aqoursoro.teleportcraft.client.gui.GuiCore;
import aqoursoro.teleportcraft.client.gui.GuiElectricGrinder;
import aqoursoro.teleportcraft.client.gui.GuiElectricSinteringFurnace;
import aqoursoro.teleportcraft.client.gui.GuiItemSite;
import aqoursoro.teleportcraft.client.gui.GuiStamper;
import aqoursoro.teleportcraft.client.gui.GuiTeleporter;
import aqoursoro.teleportcraft.client.gui.GuiTemplateProducer;
import aqoursoro.teleportcraft.client.gui.GuiThermalElectricGenerator;
import aqoursoro.teleportcraft.inventory.container.ContainerBoardProducer;
import aqoursoro.teleportcraft.inventory.container.ContainerCompressor;
import aqoursoro.teleportcraft.inventory.container.ContainerCore;
import aqoursoro.teleportcraft.inventory.container.ContainerElectricGrinder;
import aqoursoro.teleportcraft.inventory.container.ContainerElectricSinteringFurnace;
import aqoursoro.teleportcraft.inventory.container.ContainerItemSite;
import aqoursoro.teleportcraft.inventory.container.ContainerStamper;
import aqoursoro.teleportcraft.inventory.container.ContainerTeleporter;
import aqoursoro.teleportcraft.inventory.container.ContainerTemplateProducer;
import aqoursoro.teleportcraft.inventory.container.ContainerThermalElectricGenerator;
import aqoursoro.teleportcraft.tileentity.TileEntityBoardProducer;
import aqoursoro.teleportcraft.tileentity.TileEntityCompressor;
import aqoursoro.teleportcraft.tileentity.TileEntityCore;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricSinteringFurnace;
import aqoursoro.teleportcraft.tileentity.TileEntityItemSite;
import aqoursoro.teleportcraft.tileentity.TileEntityStamper;
import aqoursoro.teleportcraft.tileentity.TileEntityTeleporter;
import aqoursoro.teleportcraft.tileentity.TileEntityTemplateProducer;
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

	public static final int GUI_ID_TELEPORTER = 3;

	public static final int ITEM_SITE = 4;

	public static final int BOARD_PRODUCER = 5;

	public static final int STAMPER = 6;

	public static final int TEMPLATE_PRODUCER = 7;

	public static final int CORE = 8;

	public static final int COMPRESSOR = 9;

	public static final int ELECTRIC_SINTERING_FURNACE = 10;

	

	
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
			case GUI_ID_TELEPORTER:
				return new ContainerTeleporter(player.inventory, (TileEntityTeleporter)world.getTileEntity(pos));
			case ITEM_SITE:
				return new ContainerItemSite(player.inventory, (TileEntityItemSite)world.getTileEntity(pos));
			case BOARD_PRODUCER:
				return new ContainerBoardProducer(player.inventory, (TileEntityBoardProducer)world.getTileEntity(pos));
			case STAMPER:
				return new ContainerStamper(player.inventory, (TileEntityStamper)world.getTileEntity(pos));
			case TEMPLATE_PRODUCER:
				return new ContainerTemplateProducer(player.inventory, (TileEntityTemplateProducer)world.getTileEntity(pos));
			case CORE:
				return new ContainerCore(player.inventory, (TileEntityCore)world.getTileEntity(pos));
			case COMPRESSOR:
				return new ContainerCompressor(player.inventory, (TileEntityCompressor)world.getTileEntity(pos));
			case ELECTRIC_SINTERING_FURNACE:
				return new ContainerElectricSinteringFurnace(player.inventory, (TileEntityElectricSinteringFurnace)world.getTileEntity(pos));
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
			case GUI_ID_TELEPORTER:
				return new GuiTeleporter(player.inventory, (TileEntityTeleporter)world.getTileEntity(pos));
			case ITEM_SITE:
				return new GuiItemSite(player.inventory, (TileEntityItemSite)world.getTileEntity(pos));
			case BOARD_PRODUCER:
				return new GuiBoardProducer(player.inventory, (TileEntityBoardProducer)world.getTileEntity(pos));
			case STAMPER:
				return new GuiStamper(player.inventory, (TileEntityStamper)world.getTileEntity(pos));
			case TEMPLATE_PRODUCER:
				return new GuiTemplateProducer(player.inventory, (TileEntityTemplateProducer)world.getTileEntity(pos));
			case CORE:
				return new GuiCore(player.inventory, (TileEntityCore)world.getTileEntity(pos));
			case COMPRESSOR:
				return new GuiCompressor(player.inventory, (TileEntityCompressor)world.getTileEntity(pos));
			case ELECTRIC_SINTERING_FURNACE:
				return new GuiElectricSinteringFurnace(player.inventory, (TileEntityElectricSinteringFurnace)world.getTileEntity(pos));
			default:
				return null;
		}
	}

}
