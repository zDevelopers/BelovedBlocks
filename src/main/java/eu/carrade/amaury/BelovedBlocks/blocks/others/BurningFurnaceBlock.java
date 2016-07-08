/*
 * Copyright or © or Copr. AmauryCarrade (2015)
 * 
 * http://amaury.carrade.eu
 * 
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
package eu.carrade.amaury.BelovedBlocks.blocks.others;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.WorldBlock;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Collections;


public class BurningFurnaceBlock extends BelovedBlock
{
    public BurningFurnaceBlock()
    {
        super("burning-furnace", Material.FURNACE, BBConfig.BLOCKS.OTHERS.BURNING_FURNACE);
    }

    @Override
    protected ItemStackBuilder getItemBuilder()
    {
        return super.getItemBuilder()
                .longLore(ChatColor.GRAY, I.t("This furnace will always be burning when placed, as long as you don't use it to cook something."));
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        ShapelessRecipe recipe = new ShapelessRecipe(makeItem(getAmountPerCraft()));

        recipe.addIngredient(Material.FURNACE);
        recipe.addIngredient(Material.BLAZE_POWDER);

        return Collections.<Recipe>singletonList(recipe);
    }

    @Override
    public ItemStack getIngredient()
    {
        return null;
    }

    @Override
    public WorldBlock getPlacedBlock(BlockFace facing)
    {
        byte dataValue = 2;

        if (facing != null)
        {
            switch (facing)
            {
                case SOUTH:
                    dataValue = 2; break;
                case NORTH:
                    dataValue = 3; break;
                case EAST:
                    dataValue = 4; break;
                case WEST:
                    dataValue = 5; break;
            }
        }

        return new WorldBlock(Material.BURNING_FURNACE, dataValue);
    }

    @Override
    public boolean applyPhysics()
    {
        return false;
    }

    @Override
    public boolean isUncraftable()
    {
        return false;
    }
}
