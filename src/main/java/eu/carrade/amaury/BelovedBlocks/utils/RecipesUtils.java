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

package eu.carrade.amaury.BelovedBlocks.utils;

import org.bukkit.*;
import org.bukkit.inventory.*;

import java.util.*;


public class RecipesUtils
{
	/**
	 * Construct the recipes needed to craft {@code result} from the given ingredient
	 * placed in any 4*4 grid.
	 *
	 * @param ingredient The ingredient.
	 * @param result The result.
	 *
	 * @return A set with the four recipes.
	 */
	public static Set<Recipe> getSquaredRecipes(final ItemStack ingredient, final ItemStack result)
	{
		Set<Recipe> recipes = new HashSet<>();

		recipes.add(getShapedRecipe(result, ingredient, 'I', "II ", "II ", "   "));
		recipes.add(getShapedRecipe(result, ingredient, 'I', " II", " II", "   "));
		recipes.add(getShapedRecipe(result, ingredient, 'I', "   ", " II", " II"));
		recipes.add(getShapedRecipe(result, ingredient, 'I', "   ", "II ", "II "));

		return recipes;
	}

	/**
	 * Construct the recipes needed to craft {@code result} from the given ingredient
	 * placed in any 4*4 grid.
	 *
	 * @param ingredientMaterial The ingredient (material).
	 * @param ingredientDataValue The ingredient (data value).
	 * @param result The result.
	 *
	 * @return A set with the four recipes.
	 */
	public static Set<Recipe> getSquaredRecipes(final Material ingredientMaterial, final int ingredientDataValue, final ItemStack result)
	{
		return getSquaredRecipes(new ItemStack(ingredientMaterial, 1, (short) ingredientDataValue), result);
	}

	/**
	 * Constructs a shaped recipe with a single ingredient.
	 *
	 * @param result The recipe's result.
	 * @param ingredient The ingredient.
	 * @param shapeChar The char used in the recipe shape (single-ingredient recipe).
	 * @param shape The shape.
	 *
	 * @return The recipe.
	 */
	private static Recipe getShapedRecipe(final ItemStack result, final ItemStack ingredient, final Character shapeChar, final String... shape)
	{
		ShapedRecipe recipe = new ShapedRecipe(result);

		recipe.shape(shape);
		recipe.setIngredient(shapeChar, ingredient.getData());

		return recipe;
	}
}
