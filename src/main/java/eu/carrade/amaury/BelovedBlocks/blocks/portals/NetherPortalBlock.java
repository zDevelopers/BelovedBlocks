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
package eu.carrade.amaury.BelovedBlocks.blocks.portals;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.WorldBlock;
import fr.zcraft.zlib.tools.items.ItemStackBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Dye;

import java.util.Collections;


public class NetherPortalBlock extends BelovedBlock
{
    public NetherPortalBlock()
    {
        super("portal-nether", Material.STAINED_GLASS_PANE, BBConfig.BLOCKS.PORTALS.NETHER);
    }

    @Override
    public ItemStackBuilder getItemBuilder()
    {
        return super.getItemBuilder().data(DyeColor.PURPLE.getData());
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        ShapedRecipe portalRecipe = new ShapedRecipe(makeItem(getAmountPerCraft()));

        portalRecipe.shape("BOB", "PCP", "OPO");

        Dye purpleDye = new Dye(Material.INK_SACK);
        purpleDye.setColor(DyeColor.PURPLE);

        portalRecipe.setIngredient('B', Material.BLAZE_POWDER);
        portalRecipe.setIngredient('O', Material.OBSIDIAN);
        portalRecipe.setIngredient('P', Material.ENDER_PEARL);
        portalRecipe.setIngredient('C', purpleDye.toItemStack(1).getData());

        return Collections.singletonList((Recipe) portalRecipe);
    }

    @Override
    public ItemStack getIngredient()
    {
        return null;
    }

    @Override
    public WorldBlock getPlacedBlock(BlockFace facing)
    {
        byte dataValue = 1;

        if (facing != null)
        {
            switch (facing)
            {
                case EAST:
                case WEST:
                    dataValue = 2; break;

                default:
                    dataValue = 1;
            }
        }

        return new WorldBlock(Material.PORTAL, dataValue);
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
