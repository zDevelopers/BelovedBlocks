/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks.listeners;

import de.diddiz.LogBlock.Actor;
import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.dependencies.PrismDependency.PrismActionType;
import fr.zcraft.zlib.core.ZLibComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;


public class BlocksListener extends ZLibComponent implements Listener
{
	private BelovedBlocks p = null;

	public BlocksListener()
	{
		p = BelovedBlocks.get();
	}

	/**
	 * Used to convert the blocks from/to the seamless state with our tool.
	 *
	 * @param ev
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent ev)
	{
		if (!ev.hasBlock() || !ev.hasItem()) return;

		ItemStack item = ev.getItem();
		Block block = ev.getClickedBlock();
		Material type = block.getType();
		Player player = ev.getPlayer();

		if (p.getToolsManager().isValidStonecutterTool(item))
		{

			if ((type == Material.DOUBLE_STEP || type == Material.DOUBLE_STONE_SLAB2)
					&& ev.getAction() == Action.RIGHT_CLICK_BLOCK)
			{

				BlockState before = block.getState();

				block.setData((byte) (ev.getClickedBlock().getData() + 8));

				BlockState after = block.getState();

				// Logging
				if (p.getLogBlock().isEnabled())
				{
					p.getLogBlock().getConsumer().queueBlockReplace(new Actor(player.getName(), player.getUniqueId()), before, after);
				}
				if (p.getPrism() != null && p.getPrism().isEnabled())
				{
					PrismActionType action;
					if (block.getData() >= 8) action = PrismActionType.SMOOTH_BLOCK;
					else action = PrismActionType.CARVE_BLOCK;

					p.getPrism().registerBlockChange(player, before, after, action);
				}

				// Durability
				if (player.getGameMode() != GameMode.CREATIVE)
				{
					short newDurability = (short) (item.getDurability()
							+ p.getToolsManager().increaseDurability(item.getEnchantmentLevel(Enchantment.DURABILITY)));

					if (newDurability > item.getType().getMaxDurability())
					{
						player.getInventory().setItemInHand(new ItemStack(Material.AIR));
						player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
					}
					else
					{
						item.setDurability(newDurability);
						player.getInventory().setItemInHand(item);
					}

					player.updateInventory();
				}

				ev.setCancelled(true);
			}
		}

		else if (p.getToolsManager().isValidSawTool(item))
		{

			if ((type == Material.LOG || type == Material.LOG_2)
					&& ev.getAction() == Action.RIGHT_CLICK_BLOCK)
			{

				BlockState before = block.getState();

				block.setData((byte) (ev.getClickedBlock().getData() + 4));

				BlockState after = block.getState();

				// Logging
				if (p.getLogBlock().isEnabled())
				{
					p.getLogBlock().getConsumer().queueBlockReplace(new Actor(player.getName(), player.getUniqueId()), before, after);
				}
				if (p.getPrism() != null && p.getPrism().isEnabled())
				{
					p.getPrism().registerBlockChange(player, before, after, PrismActionType.MOVED_BARK);
				}

				// Durability
				if (player.getGameMode() != GameMode.CREATIVE)
				{
					short newDurability = (short) (item.getDurability()
							+ p.getToolsManager().increaseDurability(item.getEnchantmentLevel(Enchantment.DURABILITY)));

					if (newDurability > item.getType().getMaxDurability())
					{
						player.getInventory().setItemInHand(new ItemStack(Material.AIR));
						player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
					}
					else
					{
						item.setDurability(newDurability);
						player.getInventory().setItemInHand(item);
					}

					player.updateInventory();
				}

				ev.setCancelled(true);
			}
		}
	}

	/**
	 * Used to place a real smooth block when our "smooth slabs" used as items are
	 * placed.
	 *
	 * @param ev The event.
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent ev)
	{
		BelovedBlock belovedBlock = BelovedBlocks.get().getBelovedBlocksManager().stackToBelovedBlock(ev.getItemInHand());

		if (belovedBlock != null)
		{
			if (belovedBlock.canUse(ev.getPlayer().getUniqueId()))
				belovedBlock.onBlockPlace(ev.getBlockPlaced());
			else
				ev.setCancelled(true);
		}

	}

	/**
	 * Used to prevent our tool (shears) to get leaves like a normal shear, 
	 * and to make the smooth double slabs to drop our smooth blocks.
	 *
	 * @param ev
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreaks(final BlockBreakEvent ev)
	{
		// This event only concerns players in survival game mode.
		if (ev.getPlayer().getGameMode() != GameMode.CREATIVE)
		{
			if (p.getToolsManager().isValidStonecutterTool(ev.getPlayer().getItemInHand()))
			{
				// Those blocks don't drop as items.
				if (ev.getBlock().getType() == Material.DEAD_BUSH
						|| ev.getBlock().getType() == Material.DOUBLE_PLANT
						|| ev.getBlock().getType() == Material.LONG_GRASS
						|| ev.getBlock().getType() == Material.VINE)
				{
					// The tool doesn't loose any durability though.
					ev.setCancelled(true);
					ev.getBlock().setType(Material.AIR);
				}

				else if (ev.getBlock().getType() == Material.LEAVES
						|| ev.getBlock().getType() == Material.LEAVES_2
						|| ev.getBlock().getType() == Material.WOOL
						|| ev.getBlock().getType() == Material.WEB
						|| ev.getBlock().getType() == Material.STRING)
				{

					// The tool loses 2 durability points.
					short newDurability = (short) (ev.getPlayer().getItemInHand().getDurability()
							+ p.getToolsManager().increaseDurability(ev.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY))
							+ p.getToolsManager().increaseDurability(ev.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY)));

					if (newDurability > ev.getPlayer().getItemInHand().getType().getMaxDurability())
					{
						ev.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
						ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
					}
					else
					{
						ev.getPlayer().getItemInHand().setDurability(newDurability);
					}

					ev.getPlayer().updateInventory();
					ev.setCancelled(true);
					ev.getBlock().setType(Material.AIR);
				}
			}

			else
			{
				if (handleBlockBreak(ev.getBlock(), ev.getPlayer()))
					ev.setCancelled(true);
			}


			if (p.getToolsManager().isValidSawTool(ev.getPlayer().getItemInHand()))
			{
				// Chance the saw to break.
				float percent = (float) (p.getConfig().getInt("tool.saw.percentageToBreak") * 0.01);
				if ((float) Math.random() <= percent)
				{
					ev.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
					ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
				}
			}
		}
	}

	/**
	 * Breaks a block.
	 *
	 * @param block The block.
	 * @param player The breaker.
	 *
	 * @return {@code true} if the underlining event have to be cancelled.
	 */
	private boolean handleBlockBreak(final Block block, Player player)
	{
		final BelovedBlock belovedBlock = BelovedBlocks.get().getBelovedBlocksManager().getBlockFromBlock(block);

		if (belovedBlock != null)
		{
			// The block drops actually something
			if (!block.getDrops(player.getItemInHand()).isEmpty())
			{
				block.setType(Material.AIR);

				Bukkit.getScheduler().runTaskLater(BelovedBlocks.get(), new Runnable()
				{
					@Override
					public void run()
					{
						block.getWorld().dropItemNaturally(block.getLocation(), belovedBlock.constructItem(1));
					}
				}, 3l);

				return true;
			}
		}

		return false;
	}


	/**
	 * Used to prevent our tool from shearing sheeps or mushroom cows.
	 * <p>
	 * The cow seems to disappear, a relog fix that. Cannot be fixed on our side (Minecraft or CBukkit bug).
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerShearEntity(PlayerShearEntityEvent ev)
	{
		if (p.getToolsManager().isValidStonecutterTool(ev.getPlayer().getItemInHand()))
		{
			ev.setCancelled(true);
		}
	}
}
