/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.dependencies.BelovedBlockLogger;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Saw extends BelovedTool
{
    public Saw()
    {
        super("saw", Material.IRON_AXE, BBConfig.TOOLS.SAW);
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        ArrayList<Recipe> recipes = new ArrayList<>();
        
        ShapedRecipe baseRecipe = CraftingRecipes.shaped(makeItem(1), Material.STICK, Material.IRON_INGOT);
        
        recipes.add(CraftingRecipes.shaped(baseRecipe, "BBA", "   ", "   "));
        recipes.add(CraftingRecipes.shaped(baseRecipe, "   ", "BBA", "   "));
        recipes.add(CraftingRecipes.shaped(baseRecipe, "   ", "   ", "BBA"));
        recipes.add(CraftingRecipes.shaped(baseRecipe, "ABB", "   ", "   "));
        recipes.add(CraftingRecipes.shaped(baseRecipe, "   ", "ABB", "   "));
        recipes.add(CraftingRecipes.shaped(baseRecipe, "   ", "   ", "ABB"));
        
        return recipes;
    }

    @Override
    public boolean useableOn(Block block)
    {
        Material type = block.getType();
        return type == Material.LOG || type == Material.LOG_2;
    }

    @Override
    protected boolean onUse(Player player, Block block)
    {
        BlockState before = block.getState();
        block.setData((byte) (block.getData() + 4));
        BlockState after = block.getState();
        BelovedBlockLogger.logMoveBark(player, before, after);
        
        return true;
    }

    @Override
    protected List<String> getUsage()
    {
        return Collections.singletonList(
                I.t("{gray}Right-click a log to move the bark around the block.")
        );
    }
}
