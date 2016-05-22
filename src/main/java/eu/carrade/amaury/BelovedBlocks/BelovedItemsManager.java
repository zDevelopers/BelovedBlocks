/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import fr.zcraft.zlib.core.ZLibComponent;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

abstract public class BelovedItemsManager<T extends BelovedItem> extends ZLibComponent
{
    private final Set<T> registeredItems = new CopyOnWriteArraySet<>();
    
    public BelovedItemsManager()
    {
        MANAGERS.add(this);
    }
    
    /**
     * Registers a new item.
     *
     * This will silently reject any block already registered (with the same
     * internal ID as an item already registered).
     *
     * @param <U> The type of the item to register.
     * @param item The item.
     * @return the registered item (for convinience reasons).
     */
    protected <U extends T> U register(U item)
    {
        registeredItems.add(item);
        
        if(item.isCraftable()) CraftingRecipes.add(item.getCraftingRecipes());
        
        return item;
    }
    
    /**
     * Returns the beloved item with the given internal name.
     *
     * @param internalName The internal name.
     *
     * @return The {@link BelovedItem}; {@code null} if there isn't any beloved
     * block with this internal name.
     */
    public T getFromInternalName(String internalName)
    {
        for (T item : registeredItems)
        {
            if (item.getInternalName().equals(internalName))
            {
                return item;
            }
        }

        return null;
    }
    
    /**
     * Returns the beloved item with the given display name.
     *
     * @param displayName The display name.
     *
     * @return The {@link BelovedItem}; {@code null} if there isn't any beloved
     * block with this display name.
     */
    public T getFromDisplayName(String displayName)
    {
        if(displayName == null) return null;
        
        for (T item : registeredItems)
        {
            if(item.getDisplayName() == null) continue;
            
            if (ChatColor.stripColor(displayName).equals(item.getDisplayName()))
            {
                return item;
            }
        }

        return null;
    }
    
    /**
     * Returns the beloved block represented by this item stack.
     *
     * @param item The item.
     *
     * @return The {@link BelovedBlock}; {@code null} if this does not
     * represents a beloved block.
     */
    public T getFromItem(ItemStack item)
    {
        if(item == null) return null;
        
        BelovedItem.Attribute attribute = BelovedItem.Attribute.fromItem(item);
        if(attribute != null)
        {
            T belovedItem = getFromInternalName(attribute.getItemInternalName());
            if(belovedItem != null) return belovedItem;
        }
        
        //Do not use item names as fallback
        if(!BBConfig.USE_ITEM_NAMES_FALLBACK.get()) return null;
        
        for (T belovedItem : registeredItems)
        {
            if(belovedItem.is(item)) return belovedItem;
        }

        return null;
    }
    
    
    public boolean exists(ItemStack item)
    {
        return getFromItem(item) != null;
    }
    
    
    /**
     * Returns an {@link Collections.UnmodifiableSet unmodifiable set}
     * containing the registered {@link BelovedBlock}s.
     *
     * @return A {@link Collections.UnmodifiableSet set} with the registered
     * {@link BelovedBlock}s.
     */
    public Iterable<T> getItems()
    {
        return Collections.unmodifiableSet(registeredItems);
    }
    
    static private final List<BelovedItemsManager> MANAGERS = new ArrayList<>();
    
    static public BelovedItem getItemFromInternalName(String internalName)
    {
        for(BelovedItemsManager manager : MANAGERS)
        {
            BelovedItem item = manager.getFromInternalName(internalName);
            if(item != null) return item;
        }
        
        return null;
    }
}
