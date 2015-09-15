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

package eu.carrade.amaury.BelovedBlocks.blocks.stones;

import eu.carrade.amaury.BelovedBlocks.blocks.*;
import eu.carrade.amaury.BelovedBlocks.utils.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

import java.util.*;


public class SmoothStoneBlock extends BelovedBlock
{

	public SmoothStoneBlock()
	{
		super("blocks.slabs.stone");

		setInternalName("stone");
	}

	@Override
	protected ItemStack getItem()
	{
		ItemStack smoothStone = new ItemStack(Material.STONE);
		smoothStone.setDurability((short) 6);

		return smoothStone;
	}

	@Override
	protected Set<Recipe> getCraftingRecipes()
	{
		return RecipesUtils.getSquaredRecipes(Material.STEP, 0, constructItem(2));
	}

	@Override
	protected Set<Recipe> getReversedCraftingRecipes()
	{
		return Utils.set(RecipesUtils.getReversedCraftingRecipe(Material.STONE, 6, new ItemStack(Material.STEP, 2), 0));
	}

	@Override
	public SimpleBlock getPlacedBlock()
	{
		return new SimpleBlock(Material.DOUBLE_STEP, 8);
	}
}
