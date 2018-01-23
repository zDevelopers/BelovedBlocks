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

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.BelovedItem;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.tools.BelovedTool;
import fr.zcraft.zlib.core.ZLibComponent;
import fr.zcraft.zlib.tools.items.InventoryUtils;
import fr.zcraft.zlib.tools.items.ItemUtils;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;


public class CraftingListener extends ZLibComponent implements Listener
{
    /**
     * - Workaround to fix the crafting grid being not updated when the item is
     * taken from the grid.
     * <p>
     * - Used to prevent our blocks to be renamed using an anvil.
     * <p>
     * - Used to allow our tools to be enchanted (ensure the name is kept).
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent ev)
    {
        if (ev.getInventory() instanceof CraftingInventory && ev.getSlot() == 0)
        {
            InventoryUtils.updateInventoryLater(ev.getInventory());
        }

        // Disable Anvil overrides if not using item names.
        else if (ev.getInventory() instanceof AnvilInventory && BBConfig.USE_ITEM_NAMES_FALLBACK.get())
        {
            ItemStack item = ev.getInventory().getItem(0);
            ItemStack result = ev.getInventory().getItem(2);
            if (item == null) return;
            BelovedBlock block = BelovedBlocks.getBelovedBlocksManager().getFromItem(item);
            
            // Items cannot be renamed
            if(block != null)
            {
                ev.getInventory().setItem(2, new ItemStack(Material.AIR, 0));
                return;
            }
            
            BelovedTool tool = BelovedBlocks.getToolsManager().getFromItem(item);
            
            // Players can add enchantments to the tools.
            if(tool != null) 
            {
                ItemUtils.setDisplayName(result, tool.getDisplayName());
                ev.getInventory().setItem(2, result);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPreCraft(final PrepareItemCraftEvent event) 
    {
        final Recipe recipe = event.getRecipe();

        // Invalid or incomplete recipe entered
        if (recipe == null)
            return;

        BelovedItem item = BelovedBlocks.getBelovedBlocksManager().getFromItem(recipe.getResult());
        if(item == null)
            item = BelovedBlocks.getToolsManager().getFromItem(recipe.getResult());
        
        if(item != null)
        {
            for(HumanEntity entity : event.getViewers())
            {
                if(!(entity instanceof Player && item.canCraft((Player)entity)))
                {
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                }
            }
        }
    }
}
