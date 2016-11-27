/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see [http://www.gnu.org/licenses/].
 */
package eu.carrade.amaury.BelovedBlocks.blocks.logs;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.WorldBlock;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;

public class CompleteLog extends BelovedBlock
{
    private final short itemData;
    private final short blockData;

    public CompleteLog(String internalName, Material logMaterial, int itemData, BBConfig.BlockSection config)
    {
        this(internalName, logMaterial, (short) itemData, config);
    }
    
    public CompleteLog(String internalName, Material logMaterial, short itemData, BBConfig.BlockSection config)
    {
        super(internalName, new MaterialData(logMaterial), config);
        
        this.itemData = itemData;
        this.blockData = (short) (itemData + 12);
    }

    @Override
    public ItemStackBuilder getItemBuilder()
    {
        return super.getItemBuilder().data(itemData);
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        return CraftingRecipes.get2x2Recipes(getIngredient().getData(), makeItem(4));
    }

    @Override
    public ItemStack getIngredient()
    {
        return new ItemStack(getItemMaterialData().getItemType(), 1, itemData);
    }

    @Override
    public WorldBlock getPlacedBlock(BlockFace facing)
    {
        return new WorldBlock(getItemMaterialData().getItemType(), blockData);
    }
}
