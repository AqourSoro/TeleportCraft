package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerCore;
import aqoursoro.teleportcraft.tileentity.TileEntityCore;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCore extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/core_block.png");
	
	protected InventoryPlayer Player;
	
	protected TileEntityCore tileEntity;

	public GuiCore(@Nonnull InventoryPlayer player, @Nonnull TileEntityCore tileentity) 
	{
		super(new ContainerCore(player, tileentity));
		
		Player = player;
		
		tileEntity = tileentity;
		
		this.xSize = 176;
		
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = "Core";
//		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2- this.fontRenderer.getStringWidth(tileName) / 2), 3, 4210752);
//		this.fontRenderer.drawString(this.Player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileEntity.getEnergyStored()) + " EE", 8, 5, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int maxEnergy = tileEntity.getTotalEnergy();
		int k = this.getWorkingProgressScaled(70, maxEnergy);
		this.drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 15 + 70 - k, 180, 74 - k, 16, k + 1);
		
		if (ContainerCore.correctEnter1 == 0) 
		{
			this.drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 97, 179, 81, 8, 8);
		}else 
		{
			this.drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 97, 193, 81, 8, 8);
		}
		
		if (ContainerCore.correctEnter2 == 0) 
		{
			this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 97, 179, 81, 8, 8);
		}else 
		{
			this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 97, 193, 81, 8, 8);
		}
		
		if (ContainerCore.correctEnter3 == 0) 
		{
			this.drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 97, 179, 81, 8, 8);
		}else 
		{
			this.drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 97, 193, 81, 8, 8);
		}
		
		if (ContainerCore.correctEnter4 == 0) 
		{
			this.drawTexturedModalRect(this.guiLeft + 131, this.guiTop + 97, 179, 81, 8, 8);
		}else 
		{
			this.drawTexturedModalRect(this.guiLeft + 131, this.guiTop + 97, 193, 81, 8, 8);
		}
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull final int totalTime) 
	{
		int i = this.tileEntity.getField();
		return i != 0 ? i * pixels / totalTime : 0;
	}
	
//	private int getEnergyStoredScaled(int pixels)
//	{
//		int i = this.tileEntity.getEnergyStored();
//		int j = this.tileEntity.getMaxEnergyStored();
//		return i != 0 && j != 0 ? i * pixels / j : 0; 
//	}

	
}
