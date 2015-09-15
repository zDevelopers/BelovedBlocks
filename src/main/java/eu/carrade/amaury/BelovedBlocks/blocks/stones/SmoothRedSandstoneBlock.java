/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre <p/> This program
 * is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. <p/> This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. <p/> You should
 * have received a copy of the GNU General Public License along with this program.  If not, see
 * [http://www.gnu.org/licenses/].
 */


package eu.carrade.amaury.BelovedBlocks.blocks.stones;

import eu.carrade.amaury.BelovedBlocks.blocks.*;
import eu.carrade.amaury.BelovedBlocks.utils.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

import java.util.*;


public class SmoothRedSandstoneBlock extends BelovedBlock
{

	public SmoothRedSandstoneBlock()
	{
		super("blocks.slabs.red_sandstone");

		setInternalName("red-sandstone");
	}

	@Override
	protected ItemStack getItem()
	{
		ItemStack smoothRedSandstone = new ItemStack(Material.RED_SANDSTONE);
		smoothRedSandstone.setDurability((short) 2);

		return smoothRedSandstone;
	}

	@Override
	protected Set<Recipe> getCraftingRecipes()
	{
		return RecipesUtils.getSquaredRecipes(Material.STONE_SLAB2, 0, constructItem(2));
	}

	@Override
	protected Set<Recipe> getReversedCraftingRecipes()
	{
		return Utils.set(RecipesUtils.getReversedCraftingRecipe(Material.RED_SANDSTONE, 2, new ItemStack(Material.STONE_SLAB2, 2), 0));
	}

	@Override
	public SimpleBlock getPlacedBlock()
	{
		return new SimpleBlock(Material.DOUBLE_STONE_SLAB2, 8);
	}
}
