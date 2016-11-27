/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.BelovedItem;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import fr.zcraft.zlib.tools.items.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import org.bukkit.material.MaterialData;

abstract public class BelovedTool extends BelovedItem
{
    private final boolean usageInLore;
    private final float chanceToBreak;
    
    public BelovedTool(String internalName, Material itemMaterial, BBConfig.ToolSection toolConfig)
    {
        super(internalName, new MaterialData(itemMaterial), toolConfig);
        
        this.usageInLore = toolConfig.USAGE_IN_LORE.get();
        this.chanceToBreak = toolConfig.PERCENTAGE_BREAKING.get() * 0.01f;
    }

    /**
     * @return an {@link ItemStackBuilder} pre-configured for this tool.
     */
    @Override
    protected ItemStackBuilder getItemBuilder()
    {
        ItemStackBuilder builder = super.getItemBuilder();
        
        if(usageInLore)
        {
            for (String line : getUsage())
                builder.longLore(line);
                    
            if (chanceToBreak > 0)
                builder.loreLine()
                        .longLore(ChatColor.RED, I.t("Warning: this tool may break if used like its regular counterpart."));
        }
        
        return builder;
    }

    /**
     * Checks if this tool can be used on the given block.
     *
     * @param block the block
     * @return {@code true} if the tool can be used on this block.
     */
    abstract public boolean useableOn(Block block);
    
    public boolean use(Player player, ItemStack item, Block block)
    {
        if(!useableOn(block)) return false;
        if(!onUse(player, block)) return false;
        
        ItemUtils.damageItemInHand(player, item, 1);
        
        return true;
    }

    /**
     * Method called when the tool is used on a block.
     *
     * <p>This method is only called if {@link #useableOn(Block) useableOn(block)} returns {@code true}.</p>
     *
     * @param player The player using the tool.
     * @param block The block.
     * @return {@code true} if the tool was used (it's durability will be decreased).
     */
    abstract protected boolean onUse(Player player, Block block);
    
    //abstract protected boolean onBlockBreak(Player player, Block block);

    /**
     * @return The usage displayed in the tool's lore (if enabled). A “paragraph” per list item.
     */
    abstract protected List<String> getUsage();

    /**
     * @return The breaking probability if this tool is mis-used.
     */
    public float getChanceToBreak()
    {
        return chanceToBreak;
    }
    
    @Override
    public String getItemTypeString()
    {
        return "tools";
    }
}
