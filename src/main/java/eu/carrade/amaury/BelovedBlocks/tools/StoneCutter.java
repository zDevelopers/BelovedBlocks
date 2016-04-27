/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.dependencies.BelovedBlockLogger;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

public class StoneCutter extends BelovedTool
{
    public StoneCutter()
    {
        super("stonecutter", Material.SHEARS, BBConfig.TOOLS.STONECUTTER);
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        return CraftingRecipes.get2x2DiagonalRecipes(Material.DIAMOND, makeItem(1));
    }

    @Override
    public boolean useableOn(Block block)
    {
        Material type = block.getType();
        return type == Material.DOUBLE_STEP || type == Material.DOUBLE_STONE_SLAB2;
    }

    @Override
    protected boolean onUse(Player player, Block block)
    {
        BlockState before = block.getState();
        block.setData((byte) (block.getData() + 8));
        BlockState after = block.getState();
        
        //Logging
        if(block.getData() >= 8)
        {
            BelovedBlockLogger.logSmooth(player, before, after);
        }
        else
        {
            BelovedBlockLogger.logCarve(player, before, after);
        }
        
        player.playSound(block.getLocation(), Sound.valueOf("BLOCK_STONE_HIT"), 1f, 2f);
        
        return true;
    }

}
