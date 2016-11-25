/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.carrade.amaury.BelovedBlocks;

import fr.zcraft.zlib.components.attributes.Attributes;
import fr.zcraft.zlib.components.nbt.NBTException;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import fr.zcraft.zlib.tools.reflection.NMSException;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

abstract public class BelovedItem
{
    static public class Attribute extends fr.zcraft.zlib.components.attributes.Attribute
    {
        static public final UUID BELOVEDBLOCKS_UUID = UUID.fromString("a1279a29-0036-4bf8-bd0e-270f69da117b");
        
        public Attribute()
        {
            this(null);
        }
        
        public Attribute(BelovedItem item)
        {
            setUUID(BELOVEDBLOCKS_UUID);
            setItem(item);
        }
        
        public final BelovedItem getItem()
        {
            return BelovedItemsManager.getItemFromInternalName(getItemInternalName());
        }
        
        public final void setItem(BelovedItem item)
        {
            if(item == null) return;
            setItemInternalName(item.getInternalName());
        }
        
        public final String getItemInternalName()
        {
            return getCustomData();
        }
        
        public final void setItemInternalName(String internalName)
        {
            setCustomData(internalName);
        }
        
        static public Attribute fromItem(ItemStack item)
        {
            try
            {
                return Attributes.get(item, BELOVEDBLOCKS_UUID, Attribute.class);
            }
            catch (NMSException | NBTException ex)
            {
                PluginLogger.warning("Error while retrieving BelovedBlocks Attribute data", ex);
                return null;
            }
        }
    }
    
    /**
     * Used to identify the block when used. Display name of the item visible to
     * any player.
     */
    private final String displayName;

    /**
     * The internal name this block.
     *
     * Used by the /bb give command, and for the permissions: 
     * - usage permission (place, remove): 
     *   belovedblocks.blocks.{internalName}.use (default true);
     * - craft permission (craft, uncraft):
     *   belovedblocks.blocks.{internalName}.craft (default true); 
     * - give permission: 
     *   belovedblocks.blocks.{internalName}.give.self (default op),
     *   belovedblocks.blocks.{internalName}.give.others (default op).
     */
    private final String internalName;

    /**
     * Can this block be crafted or uncrafted, regardless to the permissions?
     */
    private final boolean isCraftable;

    /**
     * If true the item will be glowing.
     */
    private final boolean glowOnItem;
    
    private final Material itemMaterial;
    
    public BelovedItem(String internalName, Material itemMaterial, String displayName, boolean isCraftable, boolean glowOnItem)
    {
        if(displayName != null)
            displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        
        this.displayName = displayName;
        
        if(internalName == null)
            throw new IllegalArgumentException("BelovedItem's internalName can't be null.");
        
        this.internalName = internalName;
        this.isCraftable = isCraftable;
        this.glowOnItem = glowOnItem;
        this.itemMaterial = itemMaterial;
    }

    public BelovedItem(String internalName, Material itemMaterial, BBConfig.ItemSection config)
    {
        this(internalName, itemMaterial, config.NAME.get(), config.CRAFTABLE.get(), config.GLOW.get());
    }
    
    public BelovedItem(String internalName, BBConfig.ItemSection config)
    {
        this(internalName, null, config);
    }
        
    public abstract Iterable<Recipe> getCraftingRecipes();
    
    /* **  Item constructors  ** */
    
    /**
     * Returns the item used by the players to place this block.
     *
     * @return The item.
     */
    protected ItemStackBuilder getItemBuilder()
    {
        return new ItemStackBuilder(itemMaterial)
                .title(displayName)
                .glow(glowOnItem);
    }
    
    public ItemStack makeItem(int amount)
    {
        ItemStack item = this.getItemBuilder().amount(amount).hideAttributes("HIDE_ATTRIBUTES").craftItem();
        try
        {
            Attributes.set(item, new Attribute(this));
        }
        catch(NBTException | NMSException ex)
        {
            PluginLogger.error("Unable to set BlovedBlocks attributes for item", ex);
        }
        
        return item;
    }
    
    public boolean is(ItemStack item)
    {
        if(item == null) return false;
        if(!item.getType().equals(itemMaterial)) return false;
        if(item.getItemMeta() == null) return false;
        
        return displayName.equals(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
    }
    
    /* **  Permissions-related helper methods  ** */
    
    /**
     * Checks if the given player can use this block (place or break it).
     *
     * @param user The player.
     *
     * @return {@code true} if allowed.
     */
    public Boolean canUse(CommandSender user)
    {
        return isAllowed(user, "use");
    }

    /**
     * Checks if the given player can give this block using the {@code /bb give}
     * command to the {@code givenTo} player.
     *
     * @param user The player.
     * @param givenTo The receiver of the block.
     *
     * @return {@code true} if allowed.
     */
    public Boolean canGive(CommandSender user, CommandSender givenTo)
    {
        return canGive(user, user.equals(givenTo));
    }

    /**
     * Checks if the given player can give this block using the
     * {@code /bb give}.
     *
     * @param user The player.
     * @param self {@code true} if the receiver is the same player as the giver.
     *
     * @return {@code true} if allowed.
     */
    public Boolean canGive(CommandSender user, boolean self)
    {
        return isAllowed(user, "give", (self ? "self" : "others"));
    }
    
    /**
     * Checks if the given player can give this block using the
     * {@code /bb give}, whether it is to itself or to other players.
     *
     * @param user The player.
     *
     * @return {@code true} if allowed.
     */
    public Boolean canGive(CommandSender user)
    {
        return canGive(user, true) || canGive(user, false);
    }

    /**
     * Checks if the given player can craft this block (or uncraft it).
     *
     * @param user The player.
     *
     * @return {@code true} if allowed.
     */
    public Boolean canCraft(CommandSender user)
    {
        return isAllowed(user, "craft");
    }

    /**
     * Checks if the given player is granted the permission
     * {@code subPermissionNode} associated with this block, i.e. the permission
     * {@code belovedblocks.blocks.internalName.subPermissionNode}.
     *
     * @param user The player to check.
     * @param subPermissionNode The sub permission node.
     * @param extra An extra permission string that will be appended to the final
     *              permission string. It may be null.
     *
     * @return {@code True} if the permission is granted.
     */
    private Boolean isAllowed(CommandSender user, String subPermissionNode, String extra)
    {
        String permissionString = "belovedblocks." + subPermissionNode + "." + getItemTypeString() + "." + getInternalName();
        if(extra != null) 
            permissionString += "." + extra;
        
        return user.hasPermission(permissionString);
    }
    
    /**
     * Checks if the given player is granted the permission
     * {@code subPermissionNode} associated with this block, i.e. the permission
     * {@code belovedblocks.blocks.internalName.subPermissionNode}.
     *
     * @param user The player to check.
     * @param subPermissionNode The sub permission node.
     *
     * @return {@code True} if the permission is granted.
     */
    private Boolean isAllowed(CommandSender user, String subPermissionNode)
    {
        return isAllowed(user, subPermissionNode, null);
    }

    /* **  Accessors  ** */
    public String getDisplayName()
    {
        return displayName;
    }
    
    public String getInternalName()
    {
        return internalName;
    }

    public boolean isCraftable()
    {
        return isCraftable;
    }
    
    public boolean getGlowOnItem()
    {
        return glowOnItem;
    }
    
    protected Material getItemMaterial()
    {
        return itemMaterial;
    }
    
    /**
     * Returns the string identifier for the type (block, tool, …) of this item.
     * @return the string identifier for the type (block, tool, …) of this item.
     */
    public abstract String getItemTypeString();

    /* **  Comparison methods  ** */

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BelovedItem)) return false;
        
        return internalName.equals(((BelovedItem)o).internalName);
    }

    @Override
    public int hashCode()
    {
        return getInternalName().hashCode();
    }
}
