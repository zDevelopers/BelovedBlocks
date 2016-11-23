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

import java.util.Collections;


public class EndPortalBlock extends BelovedBlock
{
    public EndPortalBlock()
    {
        super("end-portal", Material.CARPET, BBConfig.BLOCKS.PORTALS.END);
    }

    @Override
    public ItemStackBuilder getItemBuilder()
    {
        return super.getItemBuilder().data(DyeColor.BLACK.getDyeData());
    }

    @Override
    public Iterable<Recipe> getCraftingRecipes()
    {
        ShapedRecipe portalRecipe = new ShapedRecipe(makeItem(getAmountPerCraft()));

        portalRecipe.shape("CCC", "ENE", "SSS");

        portalRecipe.setIngredient('E', Material.EYE_OF_ENDER);
        portalRecipe.setIngredient('N', Material.NETHER_STAR);
        portalRecipe.setIngredient('S', Material.ENDER_STONE);
        portalRecipe.setIngredient('C', new ItemStackBuilder(Material.CARPET).data(DyeColor.BLACK.getDyeData()).item().getData());

        return Collections.singletonList((Recipe) portalRecipe);
    }

    @Override
    public ItemStack getIngredient()
    {
        return null;
    }

    @Override
    public WorldBlock getPlacedBlock(BlockFace blockOrientation)
    {
        return new WorldBlock(Material.ENDER_PORTAL, 0);
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
