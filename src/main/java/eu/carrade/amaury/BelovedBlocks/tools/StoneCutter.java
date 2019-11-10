/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.dependencies.BelovedBlockLogger;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import java.util.Collections;
import java.util.List;



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
        return type == Material.STONE_SLAB || type == Material.SANDSTONE_SLAB|| type == Material.RED_SANDSTONE_SLAB|| type == Material.QUARTZ_SLAB;
    }

    @Override
    protected List<String> getUsage()
    {
        return Collections.singletonList(
                I.t("{gray}Right-click on a double slab to smooth or carve the block.")
        );
    }
    
    @Override
    protected boolean onUse(Player player, Block block)
    {
    	
        BlockState before = block.getState();
        PluginLogger.info("Retourne moi stp : {0} {1}", block.getType(),block.getType().name());
        //TODO
        //block.setData((byte) ( block.getData()+8));
       /*
      switch(type.name()){
        	case "SMOOTH_QUARTZ_SLAB":
        		
        		block.setType(Material.QUARTZ);
        		
        	case "SMOOTH_RED_SANDSTONE_SLAB":"SMOOTH_RED_SANDSTONE"
        	case "SMOOTH_SANDSTONE_SLAB":"SMOOTH_SANDSTONE"
        	case "SMOOTH_STONE_SLAB":"SMOOTH_STONE"
        	
        	default:
        		
        }*/
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
