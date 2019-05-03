package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerElectricGrinder;
import aqoursoro.teleportcraft.inventory.container.ContainerThermalElectricGenerator;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityThermalElectricGenerator;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiThermalElectricGenerator extends GuiContainer
{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/thermal_electric_generator.png");
	
	protected InventoryPlayer Player;
	
	protected TileEntityThermalElectricGenerator tileEntity;
	
	protected int totalTime;
	
	public GuiThermalElectricGenerator(@Nonnull InventoryPlayer player, @Nonnull TileEntityThermalElectricGenerator tileentity) 
	{
		super(new ContainerThermalElectricGenerator(player, tileentity));
		
		Player = player;
		
		tileEntity = tileentity;
		
		totalTime = tileEntity.getTotalTime();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull final int mouseX, @Nonnull final int mouseY)
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2- this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
		this.fontRenderer.drawString("Energy: " + tileEntity.getEnergyStored() + "EE", 71, 61, 16777215);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int burning = this.getWorkingProgressScaled(13,totalTime);
		this.drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 40 +(12 - burning), 176, 12 - burning, 14, burning);
			
		int energy = this.getEnergyStoringProgressScaled(75, tileEntity.getMaxEnergyStored());
		this.drawTexturedModalRect(this.guiLeft + 77, this.guiTop + 24, 176, 14, energy + 1, 24);
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull int totalTime) 
	{
		int i = tileEntity.getField(0);
		return i != 0 ? i * pixels / totalTime : 0;
	}
	
	private int getEnergyStoringProgressScaled(@Nonnull final int pixels, @Nonnull final int capacity)
	{
		int i = tileEntity.getEnergyStored();
		return i != 0 ? i * pixels / capacity : 0;
	}
}
