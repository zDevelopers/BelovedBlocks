/*
 * Copyright or © or Copr. AmauryCarrade (2015)
 * 
 * http://amaury.carrade.eu
 * 
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
package eu.carrade.amaury.BelovedBlocks.listeners;

import eu.carrade.amaury.BelovedBlocks.*;
import eu.carrade.amaury.BelovedBlocks.blocks.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;


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
