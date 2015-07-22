/**
 * Plugin BelovedBlocks
 * Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see [http://www.gnu.org/licenses/].
 */


package eu.carrade.amaury.BelovedBlocks.blocks.stones;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.SimpleBlock;
import eu.carrade.amaury.BelovedBlocks.utils.RecipesUtils;
import eu.carrade.amaury.BelovedBlocks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Set;


public class SmoothSandstoneBlock extends BelovedBlock
{
	public SmoothSandstoneBlock()
	{
		super("blocks.slabs.sandstone");

		setInternalName("sandstone");
	}

	@Override
	protected ItemStack getItem()
	{
		ItemStack smoothSandstone = new ItemStack(Material.SANDSTONE);
		smoothSandstone.setDurability((short) 2);

		return smoothSandstone;
	}

	@Override
	protected Set<Recipe> getCraftingRecipes()
	{
		return RecipesUtils.getSquaredRecipes(Material.STEP, 1, constructItem(2));
	}

	@Override
	protected Set<Recipe> getReversedCraftingRecipes()
	{
		return Utils.set(RecipesUtils.getReversedCraftingRecipe(Material.SANDSTONE, 2, new ItemStack(Material.STEP, 2), 1));
	}

	@Override
	public SimpleBlock getPlacedBlock()
	{
		return new SimpleBlock(Material.DOUBLE_STEP, 9);
	}
}
