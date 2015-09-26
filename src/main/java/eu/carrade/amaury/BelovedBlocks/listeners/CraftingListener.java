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

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CraftingListener implements Listener
{
	private BelovedBlocks p = null;

	public CraftingListener()
	{
		p = BelovedBlocks.get();
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
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClick(final InventoryClickEvent ev)
	{
		if (ev.getInventory() instanceof CraftingInventory && ev.getSlot() == 0)
		{
			Bukkit.getScheduler().runTaskLater(p, new Runnable()
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
						&& item.getItemMeta().getDisplayName() != null)
				{
					BelovedBlock block = p.getBelovedBlocksManager().getBlockFromDisplayName(item.getItemMeta().getDisplayName());

					if(block != null)
					{
						// Avoid players to rename the slab items.
						ev.getInventory().setItem(2, new ItemStack(Material.AIR, 0));
					}
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
	 * Used to filter reversed crafts.
	 *
	 * @param ev
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPreCraftEvent(PrepareItemCraftEvent ev)
	{
		ItemStack item = getItemStack(ev.getInventory());

		if (getCount(ev.getInventory()) == 1 && item != null && item.getType() != Material.AIR)
		{
			boolean validReversedCraft = true;

			if(!item.hasItemMeta())validReversedCraft = false;

			BelovedBlock block = p.getBelovedBlocksManager().getBlockFromDisplayName(item.getItemMeta().getDisplayName());
			if(block == null || !block.isUncraftable()) validReversedCraft = false;


			if(!validReversedCraft)
			{
				ev.getInventory().getResult().setType(Material.AIR);
				ev.getInventory().getResult().setAmount(1);

				// One viewer only, because it's a crafting inventory.
				((Player) ev.getViewers().get(0)).updateInventory();
			}
			else
			{
				final ItemStack ingredient = block.getIngredient();
				ingredient.setAmount(block.getMatterRatio());

				ev.getInventory().setResult(ingredient);
			}
		}
	}


	/* **  Utilities  ** */

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
}
