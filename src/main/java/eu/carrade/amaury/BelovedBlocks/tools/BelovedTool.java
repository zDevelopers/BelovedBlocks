/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.BelovedItem;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import fr.zcraft.zlib.tools.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static fr.zcraft.zlib.components.i18n.I.t;

abstract public class BelovedTool extends BelovedItem
{
    private final boolean usageInLore;
    private final float chanceToBreak;
    
    public BelovedTool(String internalName, Material itemMaterial, BBConfig.ToolSection toolConfig)
    {
        super(internalName, itemMaterial, toolConfig);
        
        this.usageInLore = toolConfig.USAGE_IN_LORE.get();
        this.chanceToBreak = toolConfig.PERCENTAGE_BREAKING.get() * 0.01f;
    }
    
    @Override
    protected ItemStackBuilder getItemBuilder()
    {
        ItemStackBuilder builder = super.getItemBuilder();
        
        if(usageInLore)
        {
            builder.lore(t("tool." + getInternalName() + ".howto.line1"), t("tool." + getInternalName() + ".howto.line2"));
                    
            if (chanceToBreak > 0)
            {
                builder.lore(" ", t("tool." + getInternalName() + ".howto.line3"), t("tool." + getInternalName() + ".howto.line4"));
            }
        }
        
        return builder;
    }
    
    abstract public boolean useableOn(Block block);
    
    public boolean use(Player player, ItemStack item, Block block)
    {
        if(!useableOn(block)) return false;
        if(!onUse(player, block)) return false;
        
        ItemUtils.damageItemInHand(player, item, 1);
        
        return true;
    }
    
    abstract protected boolean onUse(Player player, Block block);
    
    //abstract protected boolean onBlockBreak(Player player, Block block);
    
    
    public float getChanceToBreak()
    {
        return chanceToBreak;
    }
}
