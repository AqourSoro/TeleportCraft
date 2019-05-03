package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerTemplateProducer;
import aqoursoro.teleportcraft.tileentity.TileEntityTemplateProducer;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiTemplateProducer extends GuiContainer {

private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/template_producer.png");
	
	protected InventoryPlayer Player;
	
	protected TileEntityTemplateProducer tileEntity;
	
	
	public GuiTemplateProducer(@Nonnull InventoryPlayer player, @Nonnull TileEntityTemplateProducer tileentity) 
	{
		
		super(new ContainerTemplateProducer(player, tileentity));
		Player = player;
		tileEntity = tileentity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull final int mouseX, @Nonnull final int mouseY) 
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2- this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileEntity.getEnergyStored()) + " ME", 115, 72, 4210752);
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(@Nonnull final float partialTicks, @Nonnull final int mouseX, @Nonnull final int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int totalTime = tileEntity.getTotalTime(); 
		
		//18 is the height of texture
		int mythiniumEnergy = this.getWorkingProgressScaled(18,totalTime);
		this.drawTexturedModalRect(this.guiLeft + 130, this.guiTop + 35 + 18 - mythiniumEnergy, 176, 18 - mythiniumEnergy, 5, mythiniumEnergy + 1);
		
//		//20 is the length of the texture
//		int producing = this.getWorkingProgressScaled(20, totalTime);
//		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 35, 176, 0, 5, 17);
		
		
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull final int totalTime) 
	{
		int i = this.tileEntity.getField(0);
		return i != 0 ? i * pixels / totalTime : 0;
	}
}
