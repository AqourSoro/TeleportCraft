package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerElectricSinteringFurnace;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricSinteringFurnace;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiElectricSinteringFurnace extends GuiContainer {

private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/electric_sintering_furnace.png");
	
	protected InventoryPlayer Player;
	
	protected TileEntityElectricSinteringFurnace tileEntity;
	
	
	public GuiElectricSinteringFurnace(@Nonnull InventoryPlayer player, @Nonnull TileEntityElectricSinteringFurnace tileentity) 
	{
		
		super(new ContainerElectricSinteringFurnace(player, tileentity));
		Player = player;
		tileEntity = tileentity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull final int mouseX, @Nonnull final int mouseY) 
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2- this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileEntity.getEnergyStored()) + " EE", 115, 72, 4210752);
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(@Nonnull final float partialTicks, @Nonnull final int mouseX, @Nonnull final int mouseY) 
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int totalTime = tileEntity.getTotalTime(); 

		if(tileEntity.isWorking())
		{
			//16 is the height of texture
			int electric = this.getWorkingProgressScaled(16,totalTime);
			this.drawTexturedModalRect(this.guiLeft + 60, this.guiTop + 35 + 15 - electric, 176, 15 - electric, 10, electric + 1);
			
			//22 is the length of the texture
			int sintering = this.getWorkingProgressScaled(22, totalTime);
			this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 35, 177, 16, sintering + 1, 17);	
		}
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull final int totalTime) 
	{
		int i = this.tileEntity.getField(0);
		return i != 0 ? i * pixels / totalTime : 0;
	}
}
