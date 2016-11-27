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

package eu.carrade.amaury.BelovedBlocks.blocks;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.BelovedItem;
import fr.zcraft.zlib.tools.world.WorldUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;


public abstract class BelovedBlock extends BelovedItem
{
    private final int amountPerCraft;
    private final boolean isUncraftable;
    
    public BelovedBlock(String internalName, MaterialData itemMaterial,  BBConfig.BlockSection itemConfig)
    {
        super(internalName, itemMaterial, itemConfig);
        this.isUncraftable = itemConfig.UNCRAFTABLE.get();
        this.amountPerCraft = itemConfig.AMOUNT_PER_CRAFT.get();
    }
    
    public BelovedBlock(String internalName, BBConfig.BlockSection itemConfig)
    {
        this(internalName, null, itemConfig);
    }
    
    protected Recipe getUncraftingRecipe()
    {
        ItemStack ingredient = getIngredient();
        if(ingredient == null) return null;
        ingredient = ingredient.clone();
        ingredient.setAmount(getMatterRatio());
        
        ShapelessRecipe recipe = new ShapelessRecipe(ingredient);
        recipe.addIngredient(makeItem(1).getData());
        return recipe;
    }
        
    /* **  Abstract methods to override  ** */

    /**
     * Returns the ratio between the amount of matter of the ingredient and of the result
     * of the crafting recipe.
     *
     * As example, a smooth double-stone-slab is crafted with four slabs in a square and this
     * gives two double-slabs, so the ratio is 2.
     *
     * This is used to determine the reversed craft balance, and it will be used as the amount
     * of matter of the original block given against one transformed block. So, this needs to
     * be an integer.
     *
     * @return The ratio for the recipes.
     */
    public int getMatterRatio()
    {
        return 1;
    }

    /**
     * Returns this block's main ingredient, given back in the reversed crafting recipe.
     *
     * @return The ingredient.
     */
    public abstract ItemStack getIngredient();

    /**
     * Returns the block to place in the world.
     *
     * @param blockOrientation The orientation of the block to place
     * @return The block.
     */
    public abstract WorldBlock getPlacedBlock(BlockFace blockOrientation);

    /**
     * In the default implementation of {@link #onBlockPlace(Block, Player)}, controls wherever the
     * placed block is updated and the Minecraft's physics applied. Override this to disable
     * this update, if needed.
     *
     * @return {@code true} if the physics have to be applied.
     */
    public boolean applyPhysics()
    {
        return true;
    }

    /**
     * Executed when this block is placed, if the placement is allowed.
     *
     * Override this if needed. Default behavior: change the block to the one returned
     * by {@link #getPlacedBlock(BlockFace)}.
     *
     * @param placedBlock The placed block. Use this to change the placed block.
     * @param player The player that placed the block.
     */
    public void onBlockPlace(Block placedBlock, Player player)
    {
        WorldBlock blockToPlace = getPlacedBlock(WorldUtils.get4thOrientation(player.getLocation()));
        BlockState state = placedBlock.getState();

        state.setType(blockToPlace.getType());
        state.setRawData(blockToPlace.getDataValue());

        state.update(true, applyPhysics());
    }


    public boolean isUncraftable()
    {
        return isUncraftable;
    }
    
    public int getAmountPerCraft()
    {
        return amountPerCraft;
    }
    
    @Override
    public String getItemTypeString()
    {
        return "blocks";
    }
}
