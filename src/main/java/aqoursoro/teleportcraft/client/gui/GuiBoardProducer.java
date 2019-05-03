package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerBoardProducer;
import aqoursoro.teleportcraft.tileentity.TileEntityBoardProducer;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoardProducer extends GuiContainer
{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/board_producer.png");
	
	protected InventoryPlayer Player;
	
	protected TileEntityBoardProducer tileEntity;
	
	
	public GuiBoardProducer(@Nonnull InventoryPlayer player, @Nonnull TileEntityBoardProducer tileentity) 
	{
		
		super(new ContainerBoardProducer(player, tileentity));
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
		this.drawTexturedModalRect(this.guiLeft + 131, this.guiTop + 35 + 17 - mythiniumEnergy, 177,17 - mythiniumEnergy, 6, 1 + mythiniumEnergy);
		
		//8 is the height of the texture
		int producing = this.getWorkingProgressScaled(8, totalTime);
		this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 31, 182, 1, 8, producing + 1);
		
		
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull final int totalTime) 
	{
		int i = this.tileEntity.getField(0);
		return i != 0 ? i * pixels / totalTime : 0;
	}

}