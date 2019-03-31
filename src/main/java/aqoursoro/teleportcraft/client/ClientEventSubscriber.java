package aqoursoro.teleportcraft.client;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.init.ModItems;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Preconditions;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = CLIENT)
public class ClientEventSubscriber 
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String DEFAULT_VARIANT = "normal";
	
	@SubscribeEvent
	public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event)
	{
		registerTileEntitySpecialRenderers();
		LOGGER.debug("Registered tile entity special renderers");

		registerEntityRenderers();
		LOGGER.debug("Registered entity renderers");
		
		registerItemBlockModel(ModBlocks.MYTHINIUM_BLOCK);
		registerItemBlockModel(ModBlocks.MYTHINIUM_ORE);
		registerItemBlockModel(ModBlocks.COPPER_ORE);
		
	}
	
	private static void registerTileEntitySpecialRenderers() {
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExampleTileEntity.class, new RenderExampleTileEntity());
	}
	
	private static void registerEntityRenderers() {
//		RenderingRegistry.registerEntityRenderingHandler(Entity___.class, renderManager -> new Entity___Renderer(renderManager));
	}
	
	private static void registerItemModel(@Nonnull final Item item) 
	{
		Preconditions.checkNotNull(item, "Item cannot be null!");
		final ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item Registry Name cannot be null!");
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), DEFAULT_VARIANT));
	}
	
	private static void registerItemBlockModel(@Nonnull final Block block) 
	{
		Preconditions.checkNotNull(block, "Block cannot be null!");
		final ResourceLocation registryName = block.getRegistryName();
		Preconditions.checkNotNull(registryName, "Block Registry Name cannot be null!");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), DEFAULT_VARIANT));
	}
	
//	@SubscribeEvent
//	public static void onTextureStitchEvent(@Nonnull final TextureStitchEvent event) 
//	{
//		// register texture for example tile entity
//		final ResourceLocation registryName = ModBlocks.EXAMPLE_TILE_ENTITY.getRegistryName();
//		event.getMap().registerSprite(new ResourceLocation(registryName.getNamespace(), "block/" + registryName.getPath()));
//	}
}
