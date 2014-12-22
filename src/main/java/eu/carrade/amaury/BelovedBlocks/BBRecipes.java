/**
 *  Plugin BelovedBlocks
 *  Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class BBRecipes {

	private BelovedBlocks p = null;
	
	public BBRecipes(BelovedBlocks plugin){
		p = plugin;
		
		/** Tools **/
		
		registerTool();
		
		
		/** Slabs **/
		
		if(p.getConfig().getBoolean("blocks.slabs.stone.craftable")) {
			registerSquaredRecipe(Material.STEP, 0, p.getSmoothStoneItem(2));
		}
		
		if(p.getConfig().getBoolean("blocks.slabs.sandstone.craftable")) {
			registerSquaredRecipe(Material.STEP, 1, p.getSmoothSandstoneItem(2));
		}
		
		if(p.getConfig().getBoolean("blocks.slabs.red_sandstone.craftable")) {
			registerSquaredRecipe(Material.STONE_SLAB2, 0, p.getSmoothRedSandstoneItem(2));
		}
		
		// Slab's uncrafting recipes
		
		registerUncraft(Material.STONE, 6, new ItemStack(Material.STEP, 2), 0);
		
		registerUncraft(Material.SANDSTONE, 2, new ItemStack(Material.STEP, 2), 1);
		
		registerUncraft(Material.RED_SANDSTONE, 2, new ItemStack(Material.STONE_SLAB2, 2), 0);
		
		
		/** Logs **/
		
		if(p.getConfig().getBoolean("blocks.logs.oak.craftable")) {
			registerSquaredRecipe(Material.LOG, 0, p.getSmoothOakItem(4));
		}
		
		if(p.getConfig().getBoolean("blocks.logs.spruce.craftable")) {
			registerSquaredRecipe(Material.LOG, 1, p.getSmoothSpruceItem(4));
		}
		
		if(p.getConfig().getBoolean("blocks.logs.birch.craftable")) {
			registerSquaredRecipe(Material.LOG, 2, p.getSmoothBirchItem(4));
		}
		
		if(p.getConfig().getBoolean("blocks.logs.jungle.craftable")) {
			registerSquaredRecipe(Material.LOG, 3, p.getSmoothJungleItem(4));
		}
		
		if(p.getConfig().getBoolean("blocks.logs.acacia.craftable")) {
			registerSquaredRecipe(Material.LOG_2, 0, p.getSmoothAcaciaItem(4));
		}
		
		if(p.getConfig().getBoolean("blocks.logs.dark_oak.craftable")) {
			registerSquaredRecipe(Material.LOG_2, 1, p.getSmoothDarkOakItem(4));
		}		
		
	}
	
	
	/**
	 * Registers the recipes for the tool.
	 */
	private void registerTool() {
		if(p.getConfig().getBoolean("tool.craftable")) {
			ShapedRecipe toolRecipe = new ShapedRecipe(p.getToolItem());
			
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
	}
	
	/**
	 * Registers a recipe to craft {@code result} from the given ingredient
	 * placed in a 4*4 grid.
	 * 
	 * @param ingredientMaterial The ingredient (material).
	 * @param materialDataValue The ingredient (data value).
	 * @param result The result.
	 */
	private void registerSquaredRecipe(Material ingredientMaterial, int ingredientDataValue, ItemStack result) {
		ShapedRecipe recipe = new ShapedRecipe(result);
		
		recipe.shape("II ", "II ", "   ");
		recipe.setIngredient('I', ingredientMaterial, ingredientDataValue);
		p.getServer().addRecipe(recipe);
		
		recipe.shape(" II", " II", "   ");
		p.getServer().addRecipe(recipe);
		
		recipe.shape("   ", " II", " II");
		p.getServer().addRecipe(recipe);
		
		recipe.shape("   ", "II ", "II ");
		p.getServer().addRecipe(recipe);
	}
	
	private void registerUncraft(Material ingredientMaterial, int ingredientDataValue, ItemStack result, int resultData){
		result.setDurability((short) resultData);
		ShapelessRecipe recipe = new ShapelessRecipe(result);
		recipe.addIngredient(ingredientMaterial, ingredientDataValue);
		p.getServer().addRecipe(recipe);
	}
}
