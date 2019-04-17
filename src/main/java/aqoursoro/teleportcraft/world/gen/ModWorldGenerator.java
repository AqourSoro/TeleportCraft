package aqoursoro.teleportcraft.world.gen;

import java.util.Random;

import javax.annotation.Nonnull;

import aqoursoro.teleportcraft.init.ModBlocks;
import aqoursoro.teleportcraft.util.ModUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ModWorldGenerator implements IWorldGenerator {

	/* these values are very similar to Iron */
	private static final int ORE_MIN_Y = 8;
	private static final int GENERALORE_MAX_Y = 64;
	private static final int MYTHINIUMORE_MAX_Y = 50;
	private static final int GENERALORE_SIZE = 8;
	private static final int MYTHINIUMORE_SIZE = 5;
	private static final int GENERALORE_CHANCE = 5;
	private static final int MYTHINIUMORE_CHANCE = 4;

	@Override
	public void generate(@Nonnull final Random random, final int chunkX, final int chunkZ, @Nonnull final World world, @Nonnull final IChunkGenerator chunkGenerator, @Nonnull final IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionType()) {
			case NETHER:
				break;
			case OVERWORLD:
				this.generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			case THE_END:
				break;
			default:
				break;

		}
	}

	private void generateOverworld(@Nonnull final Random random, final int chunkX, final int chunkZ, @Nonnull final World world, @Nonnull final IChunkGenerator chunkGenerator, @Nonnull final IChunkProvider chunkProvider) 
	{
		this.generateOre(ModBlocks.COPPER_ORE.getDefaultState(), world, random, chunkX << 4, chunkZ << 4, ORE_MIN_Y, GENERALORE_MAX_Y, GENERALORE_SIZE, GENERALORE_CHANCE);
		this.generateOre(ModBlocks.LEAD_ORE.getDefaultState(), world, random, chunkX << 4, chunkZ << 4, ORE_MIN_Y, GENERALORE_MAX_Y, GENERALORE_SIZE - 1, GENERALORE_CHANCE);
		this.generateOre(ModBlocks.TIN_ORE.getDefaultState(), world, random, chunkX << 4, chunkZ << 4, ORE_MIN_Y, GENERALORE_MAX_Y, GENERALORE_SIZE, GENERALORE_CHANCE);
		this.generateOre(ModBlocks.MYTHINIUM_ORE.getDefaultState(), world, random, chunkX << 4, chunkZ << 4, ORE_MIN_Y, MYTHINIUMORE_MAX_Y, MYTHINIUMORE_SIZE, MYTHINIUMORE_CHANCE);
	}

	private void generateOre(@Nonnull final IBlockState ore, @Nonnull final World world, @Nonnull final Random random, final int x, final int z, final int minY, final int maxY, final int size, final int chances) 
	{
		for (int chance = 0; chance < chances; chance++) 
		{
			final BlockPos pos = new BlockPos(x + random.nextInt(16), minY + ModUtil.randomBetween(minY, maxY), z + random.nextInt(16));
			final WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}

}
