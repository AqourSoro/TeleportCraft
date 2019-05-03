package aqoursoro.teleportcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aqoursoro.teleportcraft.TeleportCraft;
import aqoursoro.teleportcraft.capability.teleporter.CapabilityTeleportHandler;
import aqoursoro.teleportcraft.capability.teleporter.EnumTeleportStatus;
import aqoursoro.teleportcraft.capability.teleporter.ITeleportHandler;
import aqoursoro.teleportcraft.config.ModConfiguration;
import aqoursoro.teleportcraft.creativetabs.ModCreativeTabs;
import aqoursoro.teleportcraft.teleport.TeleporterNode;
import aqoursoro.teleportcraft.teleport.TeleporterUtility;
import aqoursoro.teleportcraft.tileentity.TileEntityTeleporter;
import aqoursoro.teleportcraft.util.ModGuiHandler;
import aqoursoro.teleportcraft.util.ModUtil;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockTeleporter extends BlockContainer
{
	public static final AxisAlignedBB TELEPORTER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", BlockTeleporter.EnumType.class);


	public BlockTeleporter(@Nonnull final String name)
	{
		super(Material.ROCK);
		ModUtil.setRegistryNames(this, name);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
		this.setResistance(30.0F);
		this.setHardness(3.0F);
		this.setLightLevel(0.5F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.REGULAR));
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && !player.isSneaking())
		{
			player.openGui(TeleportCraft.instance, ModGuiHandler.GUI_ID_TELEPORTER, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityTeleporter)
			{
				((TileEntityTeleporter)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

    @Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
    {
		if (entity instanceof EntityLivingBase && entity.hasCapability(CapabilityTeleportHandler.TELEPORT_CAPABILITY, null))
		{
			ITeleportHandler handler = entity.getCapability(CapabilityTeleportHandler.TELEPORT_CAPABILITY, null);
			if (!world.isRemote)
			{
				if (handler.getTeleportStatus() == EnumTeleportStatus.INACTIVE)
				{
					handler.setOnTeleporter(entity.getPosition().distanceSq(pos) <= 1);
					handler.setDimension(entity.dimension);

					if (handler.getOnTeleporter())
					{
						boolean isHostile = (entity instanceof EntityMob) || (entity instanceof EntityWolf && ((EntityWolf)entity).isAngry());
						boolean isPassive = (entity instanceof EntityAnimal);

						if ((isHostile == false || isHostile == ModConfiguration.teleportHostileMobs) && (isPassive == false || isPassive == ModConfiguration.teleportPassiveMobs))
						{
							TeleporterNode destinationNode = TeleporterUtility.teleport((EntityLivingBase)entity, pos);
						}
					}
				}
			}

			if (handler.getTeleportStatus() == EnumTeleportStatus.INACTIVE)
			{
				double width = 0.25;
				double height = 0.25;

				double mx = world.rand.nextGaussian() * 0.2d;
				double my = world.rand.nextGaussian() * 0.2d;
				double mz = world.rand.nextGaussian() * 0.2d;

				world.spawnParticle(EnumParticleTypes.PORTAL,
					pos.getX() + 0.5 + world.rand.nextFloat() * width * 2.0F - width,
					pos.getY() + 1.5 + world.rand.nextFloat() * height,
					pos.getZ() + 0.5 + world.rand.nextFloat() * width * 2.0F - width, mx, my, mz
				);
			}
		}
    }

    @Override
	public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
    {
		super.onFallenUpon(world, pos, entity, fallDistance);
		this.onEntityWalk(world, pos, entity);
    }


	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbourBlock, BlockPos fromPos)
	{
		if (!world.isRemote)
		{
			TileEntityTeleporter tileentity = (TileEntityTeleporter)world.getTileEntity(pos);
			if (tileentity != null)
			{
				boolean isNowPowered = (world.isBlockPowered(pos));
				boolean isAlreadyPowered = tileentity.isPowered();

				tileentity.setPowered(isNowPowered);

				if (isNowPowered != isAlreadyPowered)
				{
					// there is no way in forge to determine who activated/deactivated the teleporter, so we simply get the closest player
					// works for _most_ cases
					EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 16, false);
					if (player != null)
					{
						String translationKey = "message." + Reference.MOD_ID + '_' + this.getClass().getSimpleName() + '.' + (isNowPowered ? "teleporterLocked" : "teleporterUnlocked");
						TextComponentTranslation message = new TextComponentTranslation(translationKey);
						player.sendMessage(message);
					}
					tileentity.markDirty();
				}
			}
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntityTeleporter tileEntityTeleporter = (TileEntityTeleporter)world.getTileEntity(pos);
		if (tileEntityTeleporter != null)
		{
			tileEntityTeleporter.removeFromNetwork();

			if (tileEntityTeleporter.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			{
				IItemHandler handler = tileEntityTeleporter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				ItemStack stack = handler.getStackInSlot(0);
				if (!stack.isEmpty())
				{
					stack = stack.copy();
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
				}
			}
		}

		// super _must_ be called last because it removes the tile entity
		super.breakBlock(world, pos, state);
	}



	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityTeleporter();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, meta == 0 ? EnumType.REGULAR : EnumType.REGULAR);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for ( EnumType type : EnumType.values() )
		{
			list.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TELEPORTER_AABB; //new AxisAlignedBB(0.0F, 0.0F, 0.0F, getBounds().xCoord, getBounds().yCoord, getBounds().zCoord);
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return TELEPORTER_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}



	public static enum EnumType implements IStringSerializable
	{
		REGULAR(0, "regular", "teleporter", "teleporter");

		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final String registryName;

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];

		@Override
		public String getName()
		{
			return this.name;
		}

		public int getMetadata()
		{
			return this.meta;
		}

		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}

		public String getRegistryName()
		{
			return this.registryName;
		}

		public static EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;
			return META_LOOKUP[meta];
		}

		private EnumType(int meta, String name, String unlocalizedName, String registryName)
		{
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}

		static
		{
			for (EnumType value : values())
			{
				META_LOOKUP[value.getMetadata()] = value;
			}
		}

	}
}
