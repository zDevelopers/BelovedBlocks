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

package eu.carrade.amaury.BelovedBlocks;

import de.diddiz.LogBlock.*;
import eu.carrade.amaury.BelovedBlocks.blocks.*;
import eu.carrade.amaury.BelovedBlocks.dependencies.PrismDependency.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;


public class BBListener implements Listener
{

	private BelovedBlocks p = null;

	public BBListener(BelovedBlocks plugin)
	{
		p = plugin;
	}

	/**
	 * Used to convert the blocks from/to the seamless state with our tool.
	 *
	 * @param ev
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev)
	{
		if (!ev.hasBlock() || !ev.hasItem()) return;

		ItemStack item = ev.getItem();
		Block block = ev.getClickedBlock();
		Material type = block.getType();
		Player player = ev.getPlayer();

		if (p.isValidStonecutterTool(item))
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
							+ p.increaseDurability(item.getEnchantmentLevel(Enchantment.DURABILITY)));

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

		else if (p.isValidSawTool(item))
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
							+ p.increaseDurability(item.getEnchantmentLevel(Enchantment.DURABILITY)));

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
	@EventHandler (ignoreCancelled = true)
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
	@EventHandler
	public void onBlockBreaks(final BlockBreakEvent ev)
	{
		// This event only concerns players in survival game mode.
		if (ev.getPlayer().getGameMode() != GameMode.CREATIVE)
		{
			if (p.isValidStonecutterTool(ev.getPlayer().getItemInHand()))
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
							+ p.increaseDurability(ev.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY))
							+ p.increaseDurability(ev.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY)));

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


			if (p.isValidSawTool(ev.getPlayer().getItemInHand()))
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
	 *
	 * @param ev
	 */
	@EventHandler
	public void onPlayerShearEntity(PlayerShearEntityEvent ev)
	{
		if (p.isValidStonecutterTool(ev.getPlayer().getItemInHand()))
		{
			ev.setCancelled(true);
		}
	}

	/**
	 *  - Workaround to fix the crafting grid being not updated when the item is taken
	 *    from the grid.
	 *    <p>
	 *  - Used to prevent our blocks to be renamed using an anvil.
	 *    <p>
	 *  - Used to allow our tools to be enchanted (ensure the name is kept).
	 *
	 * @param ev
	 */
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent ev)
	{
		if (ev.getInventory() instanceof CraftingInventory && ev.getSlot() == 0)
		{
			p.getServer().getScheduler().runTaskLater(p, new Runnable()
			{

				@Override
				public void run()
				{
					for (HumanEntity viewer : ev.getViewers())
					{
						if (viewer instanceof Player)
						{
							((Player) viewer).updateInventory();
						}
					}
				}

			}, 1l);
		}

		else if (ev.getInventory() instanceof AnvilInventory)
		{
			ItemStack item = ev.getInventory().getItem(0);
			ItemStack result = ev.getInventory().getItem(2);

			if (item != null)
			{

				// Items cannot be renamed
				if (item.hasItemMeta()
						&& item.getItemMeta().getDisplayName() != null
						&& (item.getItemMeta().getDisplayName().equals(p.getSmoothStoneName())
						|| item.getItemMeta().getDisplayName().equals(p.getSmoothSandstoneName())
						|| item.getItemMeta().getDisplayName().equals(p.getSmoothRedSandstoneName())
						|| item.getItemMeta().getDisplayName().equals(p.getSmoothQuartzName())))
				{
					// Avoid players to rename the slab items.
					ev.getInventory().setItem(2, new ItemStack(Material.AIR, 0));
				}

				// Saw
				else if (item.hasItemMeta()
						&& item.getItemMeta().getDisplayName() != null
						&& item.getItemMeta().getDisplayName().equals(p.getToolSawName()))
				{
					// Players can add enchantments to the saw.
					ev.getInventory().getItem(2).setDurability(item.getDurability());
					ItemMeta itemMeta = result.getItemMeta();
					itemMeta.setDisplayName(p.getToolSawName());
					ev.getInventory().getItem(2).setItemMeta(itemMeta);
				}

				// Stonecutter
				else if (item.hasItemMeta()
						&& item.getItemMeta().getDisplayName() != null
						&& item.getItemMeta().getDisplayName().equals(p.getToolStonecutterName()))
				{
					// Same for the stonecutter
					ev.getInventory().getItem(2).setDurability(item.getDurability());
					ItemMeta itemMeta = result.getItemMeta();
					itemMeta.setDisplayName(p.getToolStonecutterName());
					ev.getInventory().getItem(2).setItemMeta(itemMeta);
				}
			}
		}
	}

	/**
	 * Returns the first non-empty stack inside the give inventory.
	 *
	 * @param i The inventory
	 * @return The ItemStack
	 */
	private ItemStack getItemStack(Inventory i)
	{
		for (int t = 1; t < i.getSize(); t++)
		{
			if (i.getItem(t) != null && i.getItem(t).getType() != Material.AIR)
			{
				return i.getItem(t);
			}
		}

		return null;
	}

	/**
	 * Returns the amount of non-empty stacks in the given inventory.
	 *
	 * @param i The inventory.
	 * @return The amount.
	 */
	private int getCount(Inventory i)
	{
		int amount = 0;
		for (int t = 1; t < i.getSize(); t++)
		{
			if (i.getItem(t) != null && i.getItem(t).getType() != Material.AIR)
			{
				if (i.getItem(t).getAmount() != 0)
				{
					amount++;
				}
			}
		}

		return amount;
	}

	/**
	 * Used to filter reversed crafts.
	 *
	 * @param e
	 */
	@EventHandler
	public void onPreCraftEvent(PrepareItemCraftEvent e)
	{
		ItemStack item = getItemStack(e.getInventory());

		if (getCount(e.getInventory()) == 1 && item != null && item.getType() != Material.AIR)
		{
			// Handles the smooth stone items.
			if ((item.getType() == Material.STONE && item.getDurability() == 6)
					|| item.getType() == Material.SANDSTONE
					|| item.getType() == Material.RED_SANDSTONE
					|| item.getType() == Material.QUARTZ_BLOCK)
			{
				if (item.hasItemMeta()
						&& (item.equals(p.getSmoothStoneItem(item.getAmount()))
						|| item.equals(p.getSmoothSandstoneItem(item.getAmount()))
						|| item.equals(p.getSmoothRedSandstoneItem(item.getAmount()))
						|| item.equals(p.getSmoothQuartzItem(item.getAmount()))))
				{
					// ok
				}
				else
				{
					e.getInventory().setResult(new ItemStack(Material.AIR, 1));
				}
			}

			// Handles the log items.
			else if (item.getType() == Material.LOG || item.getType() == Material.LOG_2)
			{
				if (item.hasItemMeta() && (item.equals(p.getSmoothOakItem(item.getAmount()))
						|| item.equals(p.getSmoothSpruceItem(item.getAmount()))
						|| item.equals(p.getSmoothBirchItem(item.getAmount()))
						|| item.equals(p.getSmoothJungleItem(item.getAmount()))
						|| item.equals(p.getSmoothAcaciaItem(item.getAmount()))
						|| item.equals(p.getSmoothDarkOakItem(item.getAmount()))))
				{

					ItemStack result = new ItemStack(item.getType(), 1);
					result.setDurability(item.getDurability());
					e.getInventory().setResult(result);
				}
			}
		}
	}
}
	
