package aqoursoro.teleportcraft.client.gui;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.inventory.container.ContainerElectricGrinder;
import aqoursoro.teleportcraft.inventory.container.ContainerItemSite;
import aqoursoro.teleportcraft.tileentity.TileEntityElectricGrinder;
import aqoursoro.teleportcraft.tileentity.TileEntityItemSite;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiItemSite extends GuiContainer {

	private static final ResourceLocation TEXTURES = new ResourceLocation(
			Reference.MOD_ID + ":textures/gui/item_site.png");

	protected InventoryPlayer Player;

	protected TileEntityItemSite tileEntity;

	public GuiItemSite(@Nonnull InventoryPlayer player, @Nonnull TileEntityItemSite tileentity) {
		super(new ContainerItemSite(player, tileentity));
		Player = player;
		tileEntity = tileentity;
		this.xSize = 216;
		this.ySize = 222;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(@Nonnull final int mouseX, @Nonnull final int mouseY) {
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 6,
				4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileEntity.getEnergyStored()) + " ME", 6, 91, 4210752);
		this.fontRenderer.drawString("Inventory", 8, 128, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int maxEnergy = tileEntity.getTotalEnergy();
		
		//71 is the height of the texture
		int energy = this.getWorkingProgressScaled(71,maxEnergy);
		this.drawTexturedModalRect(this.guiLeft + 12, this.guiTop + 15 + 71 - energy, 220, 75 - energy, 17, energy + 1);
		
	}
	
	private int getWorkingProgressScaled(@Nonnull final int pixels, @Nonnull final int maxEnergy) 
	{
		int i = this.tileEntity.getField();
		return i != 0 ? i * pixels / maxEnergy : 0;
	}

}


