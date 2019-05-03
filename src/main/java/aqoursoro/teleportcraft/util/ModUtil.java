package aqoursoro.teleportcraft.util;

import com.google.common.base.Preconditions;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Random;


@SuppressWarnings("WeakerAccess")
public class ModUtil 
{
	private static final Random RANDOM = new Random();
	
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final String name) 
	{
		return setRegistryNames(entry, new ResourceLocation(Reference.MOD_ID, name));
	}
	
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) 
	{
		return setRegistryNames(entry, registryName, registryName.getPath());
	}
	
	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setRegistryNames(@Nonnull final T entry, @Nonnull final ResourceLocation registryName, @Nonnull final String unlocalizedName) 
	{
		entry.setRegistryName(registryName);
		if (entry instanceof Block) 
		{
			((Block) entry).setTranslationKey(unlocalizedName);
		}
		if (entry instanceof Item) 
		{
			((Item) entry).setTranslationKey(unlocalizedName);
		}
		return entry;
	}

	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setCreativeTab(@Nonnull final T entry) 
	{
		return setCreativeTab(entry, ModCreativeTabs.CREATIVE_TAB);
	}

	@Nonnull
	public static <T extends IForgeRegistryEntry.Impl<?>> T setCreativeTab(@Nonnull final T entry, final CreativeTabs creativeTab) 
	{
		if (entry instanceof Block) 
		{
			((Block) entry).setCreativeTab(creativeTab);
		}
		if (entry instanceof Item) 
		{
			((Item) entry).setCreativeTab(creativeTab);
		}
		return entry;
	}
	
	@Nonnull
	public static CreativeTabs[] getCreativeTabs(@Nonnull final Item item) 
	{
		Preconditions.checkNotNull(item, "Item cannot be null!");
		if (item.getRegistryName().getNamespace().equals(Reference.MOD_ID)) 
		{
			return new CreativeTabs[]{item.getCreativeTab(), ModCreativeTabs.CREATIVE_TAB, CreativeTabs.SEARCH};
		}
		return new CreativeTabs[]{item.getCreativeTab(), CreativeTabs.SEARCH};
	}
	
	public static Block getBlockFromPos(World worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos).getBlock();
	}

	public static IBlockState getStateFromPos(World worldIn, BlockPos pos) 
	{
		return worldIn.getBlockState(pos);
	}
	
	public static String getRegistryNameForClass(final Class<?> clazz, final String removeType) 
	{
		return org.apache.commons.lang3.StringUtils.uncapitalize(clazz.getSimpleName().replace(removeType, "")).replaceAll("([A-Z])", "_$1").toLowerCase();
	}

	public static int[] split(final int complete, final int parts) 
	{
		final int[] arr = new int[parts];
		int left = complete;
		int partsLeft = parts;
		for (int i = 0; partsLeft > 0; i++) {
			final int size = ((left + partsLeft) - 1) / partsLeft; 
			arr[i] = size;
			left -= size;
			partsLeft--;
		}
		return arr;
	}
	
	public static int safeDivision(final int division, final int divisor)
	{
		if(divisor == 0)
		{
			return 0;
		}
		else
		{
			return division / divisor;
		}
	}
	
	//Used for generators to calculate.
	public static double randomBetween(final int min, final int max) 
	{
		return RANDOM.nextInt((max - min) + 1) + min;
	}
	
	public static double map(final double input, final double inputStart, final double inputEnd, final double outputStart, final double outputEnd) 
	{
		final double input_range = inputEnd - inputStart;
		final double output_range = outputEnd - outputStart;

		return (((input - inputStart) * output_range) / input_range) + outputStart;
	}
	
	@Nonnull
	public static Side getLogicalSide(@Nonnull final World world) 
	{
		if (world.isRemote) 
		{
			return Side.CLIENT;
		} 
		else 
		{
			return Side.SERVER;
		}
	}
	
	public static void logLogicalSide(@Nonnull final Logger logger, @Nonnull final World world) 
	{
		logger.info("Logical Side: " + getLogicalSide(world));
	}
	
	public static void dump(@Nonnull final Logger logger, @Nonnull final Object... objects) 
	{
		for (final Object object : objects) 
		{
			final Field[] fields = object.getClass().getDeclaredFields();
			logger.info("Dump of " + object + ":");
			for (final Field field : fields) 
			{
				try 
				{
					field.setAccessible(true);
					logger.info(field.getName() + " - " + field.get(object));
				} 
				catch (IllegalArgumentException | IllegalAccessException e) 
				{
					logger.info("Error getting field " + field.getName());
					logger.info(e.getLocalizedMessage());
				}
			}
		}
	}
}
