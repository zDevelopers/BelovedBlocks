/**
 *  Plugin BelovedBlocks
 *  Copyright (C) 2014-2015 Amaury Carrade
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

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import eu.carrade.amaury.BelovedBlocks.i18n.I18n;

public final class BelovedBlocks extends JavaPlugin {
	
	private I18n i18n = null;
	
	private String toolName;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		if(getConfig().getString("lang") == null) {
			i18n = new I18n(this);
		}
		else {
			i18n = new I18n(this, getConfig().getString("lang"));
		}
		
		getServer().getPluginManager().registerEvents(new BBListener(this), this);
		
		toolName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("tool.name"));
		
		registerRecipes();
	}
	
	/**
	 * Returns the internationalization manager.
	 * 
	 * @return
	 */
	public I18n getI18n() {
		return i18n;
	}
	
	/**
	 * Registers the recipes used by this plugin.
	 */
	public void registerRecipes() {
		if(getConfig().getBoolean("tool.craftable")) {
			ItemStack item = new ItemStack(Material.DIAMOND_HOE);
			ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(toolName);
			item.setItemMeta(meta);
			
			ShapedRecipe recipe = new ShapedRecipe(item);
			recipe.shape("DS ", "DS ", " S ");
			recipe.setIngredient('D', Material.DIAMOND);
			recipe.setIngredient('S', Material.STICK);
			getServer().addRecipe(recipe);
			
			recipe.shape(" DS", " DS", "  S");
			getServer().addRecipe(recipe);
		}
	}
	
	/**
	 * Checks if the given tool is a valid tool.
	 * 
	 * @param tool The tool to check.
	 * @return The result.
	 */
	public boolean isValidTool(ItemStack tool) {
		return (tool.getType() == Material.DIAMOND_HOE
				&& tool.getItemMeta().getDisplayName().equals(toolName));
	}
	
	/**
	 * Calculates the new durability, taking into account the unbreaking enchantment.
	 * 
	 * @param unbreakingLevel The Unbreaking level (0 if not enchanted with that).
	 * @return The durability to add.
	 */
	public short increaseDurability(int unbreakingLevel) {
		if(new Random().nextInt(100) <= (100/(unbreakingLevel + 1))) {
			return 1;
		}
		
		return 0;
	}
}
