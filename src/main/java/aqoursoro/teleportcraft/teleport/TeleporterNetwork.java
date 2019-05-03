package aqoursoro.teleportcraft.teleport;

import java.util.ArrayList;

import aqoursoro.teleportcraft.block.BlockTeleporter;
import aqoursoro.teleportcraft.capability.electricenergy.CapabilityElectricEnergy;
import aqoursoro.teleportcraft.item.ItemIDChip;
import aqoursoro.teleportcraft.tileentity.TileEntityTeleporter;
import aqoursoro.teleportcraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * TeleporterNetwork is the singleton responsible for saving the teleporter data onto the world file, and is
 * responsible for retrieving destination and source nodes during teleportation.
 *
 */
public class TeleporterNetwork extends WorldSavedData
{

	private ArrayList<TeleporterNode> network = new ArrayList<TeleporterNode>();

	public TeleporterNetwork()
	{
		super(Reference.MOD_ID);
	}

	public TeleporterNetwork(String identifier)
	{
		super(identifier);
	}


	public static TeleporterNetwork get(World world)
	{
		TeleporterNetwork instance = (TeleporterNetwork)world.getMapStorage().getOrLoadData(TeleporterNetwork.class, Reference.MOD_ID);
		if (instance == null)
		{
			instance = new TeleporterNetwork();
			world.setData(Reference.MOD_ID, instance);
			instance.markDirty();
		}
		return instance;
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList netNBT = nbt.getTagList("Network", NBT.TAG_COMPOUND);

		if (this.network.size() != 0) this.network.clear();

		for (int i = 0; i < netNBT.tagCount(); ++i)
		{
			NBTTagCompound nodeNBT = netNBT.getCompoundTagAt(i);
			TeleporterNode node = new TeleporterNode(nodeNBT);
			this.network.add(node);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList netNBT = new NBTTagList();

		for (int i = 0; i < this.network.size(); ++i)
		{
			TeleporterNode node = this.network.get(i);
			NBTTagCompound nodeNBT = node.writeToNBT(new NBTTagCompound());
			netNBT.appendTag(nodeNBT);
		}

		nbt.setTag("Network", netNBT);
		return nbt;
	}


	public TeleporterNode getNode(BlockPos pos, int dimension)
	{
		for (int i = 0; i < this.network.size(); ++i)
		{
			TeleporterNode node = this.network.get(i);
			if (node.matches(pos, dimension))
			{
				return node;
			}
		}
		return null;
	}


	public void addNode(TeleporterNode node)
	{
		this.network.add(node);
		this.markDirty();
	}

	public boolean removeNode(BlockPos pos, int dimension)
	{
		for (int i = 0; i < this.network.size(); ++i)
		{
			TeleporterNode node = this.network.get(i);
			if (node.matches(pos, dimension))
			{
				this.network.remove(node);
				this.markDirty();
				return true;
			}
		}
		return false;
	}


	/**
	 * gets the next node that can be teleported to from the target teleporter
	 *
	 */
	public TeleporterNode getNextNode(Entity entityIn, TeleporterNode sourceNode)
	{

		TileEntityTeleporter tEntSource = (TileEntityTeleporter)entityIn.world.getTileEntity(sourceNode.pos);
		ItemStack sourceKey_1 = tEntSource.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		ItemStack sourceKey_2 = tEntSource.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1);
		
		int source_energy = tEntSource.getCapability(CapabilityElectricEnergy.ELECTRIC_ENERGY, null).getEnergyStored();

		TeleporterNode destinationNode = null;

		// get the top-most entity (rider) for sending messages
		Entity potentialPlayerEntity = entityIn;
		while (!potentialPlayerEntity.getPassengers().isEmpty())
		{
			potentialPlayerEntity = potentialPlayerEntity.getControllingPassenger();
		}

		int index = this.network.indexOf(sourceNode);
		for (int i = index + 1; i < this.network.size() + index; ++i)
		{

			TeleporterNode node = this.network.get(i % this.network.size());

			WorldServer destinationWorld = DimensionManager.getWorld(node.dimension);
			if (destinationWorld != null)
			{
				// if this node matches the source node, continue
				if (node == sourceNode)
				{
					continue;
				}

				// if the teleporter types are different, continue
				if (sourceNode.type != node.type)
				{
					continue;
				}

				// if the teleporter isn't inter-dimensional and the dimensions are different, continue
				if (sourceNode.type == BlockTeleporter.EnumType.REGULAR && sourceNode.dimension != node.dimension)
				{
					continue;
				}

				// if a tile entity doesn't exist at the specified node location, continue
				TileEntityTeleporter tEntDest = (TileEntityTeleporter)destinationWorld.getTileEntity(node.pos);
				if (tEntDest == null)
				{
					continue;
				}
				
				ItemStack destinationKey_1 = tEntDest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
				ItemStack destinationKey_2 = tEntDest.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1);
				// if the key itemstacks are different, continue
				if (!this.doKeyStacksMatch(sourceKey_1, destinationKey_1) || !this.doKeyStacksMatch(sourceKey_2, destinationKey_2))
				{
					continue;
				}
				
				if(sourceKey_1 == ItemStack.EMPTY || sourceKey_2 == ItemStack.EMPTY)
				{
					continue;
				}
				
				if(destinationKey_1 == ItemStack.EMPTY || destinationKey_2 == ItemStack.EMPTY)
				{
					continue;
				}
				// if the destination node is obstructed, continue
				if (this.isObstructed(destinationWorld, node))
				{
					if (potentialPlayerEntity instanceof EntityPlayer)
					{
						EntityPlayer entityPlayer = (EntityPlayer) potentialPlayerEntity;
						entityPlayer.sendMessage(this.getMessage("teleporterBlocked"));
					}
					continue;
				}

				// if the destination node is powered, continue
				if (tEntDest.isPowered() == true)
				{
					if (potentialPlayerEntity instanceof EntityPlayer)
					{
						EntityPlayer entityPlayer = (EntityPlayer) potentialPlayerEntity;
						entityPlayer.sendMessage(this.getMessage("teleporterDisabled"));
					}
					continue;
				}

				// if all above conditions are met, we've found a valid destination node.
				destinationNode = node;
				break;
			}
		}

		if (destinationNode == null && potentialPlayerEntity instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer) potentialPlayerEntity;
			entityPlayer.sendMessage(this.getMessage("teleporterNotFound"));
		}

		return destinationNode;
	}

	private boolean isObstructed(World world, TeleporterNode node)
	{
		BlockPos blockPos1 = new BlockPos(node.pos.getX(), node.pos.getY() + 1, node.pos.getZ());
		BlockPos blockPos2 = new BlockPos(node.pos.getX(), node.pos.getY() + 2, node.pos.getZ());
		Block block1 = world.getBlockState(blockPos1).getBlock();
		Block block2 = world.getBlockState(blockPos2).getBlock();

		if (block1.isPassable(world, blockPos1) && block2.isPassable(world, blockPos2))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean doKeyStacksMatch(ItemStack sourceKey, ItemStack destinationKey)
	{
		// if both keys are null, they match (obviously!)
		if (sourceKey.isEmpty() && destinationKey.isEmpty())
		{
			return true;
		}
		// if one or both keys are not null...
		else
		{
			// if they're both not null...
			if (!(sourceKey.isEmpty() || destinationKey.isEmpty()))
			{
				// ensure that the items match
				if (sourceKey.getItem() != destinationKey.getItem()) return false;
				// ensure that the item metadata matches
				if (sourceKey.getItemDamage() != destinationKey.getItemDamage()) return false;

				// if the source key has an NBT tag
				if (sourceKey.hasTagCompound())
				{
					// ensure that the destination key also has an NBT tag
					if (!destinationKey.hasTagCompound()) return false;

					// if the key items are written books
					if (sourceKey.getItem() == Items.WRITTEN_BOOK)
					{
						// ensure that the book authors and titles match
						String sourceBookNBT = sourceKey.getTagCompound().getString("author") + ":" + sourceKey.getTagCompound().getString("title");
						String destinationBookNBT = destinationKey.getTagCompound().getString("author") + ":" + destinationKey.getTagCompound().getString("title");
						if (!sourceBookNBT.equals(destinationBookNBT)) return false;
					}
					// if it's any other type of item
					else
					{
						// ensure that the nbt tags match
						if (!ItemStack.areItemStackTagsEqual(sourceKey, destinationKey)) return false;
					}
				}

				// if we're still here, everything matches
				return true;
			}

			// if one key is null and the other is not, they don't match
			else return false;
		}
	}

	private TextComponentTranslation getMessage(String messageName)
	{
		return new TextComponentTranslation("message." + Reference.MOD_ID + '_' + this.getClass().getSimpleName() + '.' + messageName);
	}

}
