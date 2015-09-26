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

package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;


public class BBRecipes
{

	private BelovedBlocks p = null;

	public BBRecipes(BelovedBlocks plugin)
	{
		p = plugin;

		/** Tools **/

		registerTools();

	}

	/**
	 * Registers the recipes for the tool.
	 */
	private void registerTools()
	{
		if (p.getConfig().getBoolean("tool.stonecutter.craftable"))
		{
			ShapedRecipe toolRecipe = new ShapedRecipe(p.getToolStonecutterItem());

			toolRecipe.shape("D  ", " D ", "   ");
			toolRecipe.setIngredient('D', Material.DIAMOND);
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape(" D ", "  D", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape(" D ", "D  ", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("  D", " D ", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "D  ", " D ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", " D ", "  D");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", " D ", "D  ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "  D", " D ");
			p.getServer().addRecipe(toolRecipe);
		}

		if (p.getConfig().getBoolean("tool.saw.craftable"))
		{
			ShapedRecipe toolRecipe = new ShapedRecipe(p.getToolSawItem());

			toolRecipe.shape("IIS", "   ", "   ");
			toolRecipe.setIngredient('I', Material.IRON_INGOT);
			toolRecipe.setIngredient('S', Material.STICK);

			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "IIS", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "   ", "IIS");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("SII", "   ", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "SII", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "   ", "SII");
			p.getServer().addRecipe(toolRecipe);
		}
	}
}
