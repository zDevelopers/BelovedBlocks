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
		
		registerTool();
		registerStone();
		registerSand();
		registerRedsand();
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
	public void registerTool() {
		if(getConfig().getBoolean("tool.craftable")) {
			ItemStack item = new ItemStack(Material.SHEARS);
			ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(toolName);
			item.setItemMeta(meta);
			²²
			ShapedRecipe recipe = new ShapedRecipe(item);
			
			recipe.shape("D  ", " D ", "   ");
			recipe.setIngredient('D', Material.DIAMOND);
			getServer().addRecipe(recipe);
			
			recipe.shape(" D ", "  D", "   ");
			getServer().addRecipe(recipe);
			
			recipe.shape(" D ", "D  ", "   ");
			getServer().addRecipe(recipe);
			
			recipe.shape("  D", " D ", "   ");
			getServer().addRecipe(recipe);
			
			recipe.shape("   ", "D  ", " D ");
			getServer().addRecipe(recipe);
			
			recipe.shape("   ", " D ", "  D");
			getServer().addRecipe(recipe);
			
			recipe.shape("   ", " D ", "D  ");
			getServer().addRecipe(recipe);
			
			recipe.shape("   ", "  D", " D ");
			getServer().addRecipe(recipe);
		}
	}
	
	public void registerStone(){
		ItemStack stoneStep = new ItemStack(Material.STEP, 2);
		stoneStep.setDurability((short) 0);
		ItemMeta itemMeta = stoneStep.getItemMeta();
	    itemMeta.setDisplayName(ChatColor.RESET + "Smooth Stone Slab");
	    stoneStep.setItemMeta(itemMeta);
		ShapedRecipe StepCraft = new ShapedRecipe(stoneStep);
		
		StepCraft.shape("SS ", "SS ", "   ");
		StepCraft.setIngredient('S', Material.STEP, 0);
		getServer().addRecipe(StepCraft);
		StepCraft.shape(" SS", " SS", "   ");
		getServer().addRecipe(StepCraft);
		StepCraft.shape("   ", " SS", " SS");
		getServer().addRecipe(StepCraft);
		StepCraft.shape("   ", "SS ", "SS ");
		getServer().addRecipe(StepCraft);
	}
	
	public void registerSand(){
		ItemStack sandStep = new ItemStack(Material.STEP, 2);
		sandStep.setDurability((short) 1);
		ItemMeta itemMeta = sandStep.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + "Smooth Sandstone Slab");
		sandStep.setItemMeta(itemMeta);
		ShapedRecipe SandCraft = new ShapedRecipe(sandStep);
		
		SandCraft.shape("SS ", "SS ", "   ");
		SandCraft.setIngredient('S', Material.STEP, 1);
		getServer().addRecipe(SandCraft);
		SandCraft.shape(" SS", " SS", "   ");
		getServer().addRecipe(SandCraft);
		SandCraft.shape("   ", " SS", " SS");
		getServer().addRecipe(SandCraft);
		SandCraft.shape("   ", "SS ", "SS ");
		getServer().addRecipe(SandCraft);
	}
	
	public void registerRedsand(){
		ItemStack redsandStep = new ItemStack(Material.STONE_SLAB2, 2);
		redsandStep.setDurability((short) 0);
		ItemMeta itemMeta = redsandStep.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + "Smooth Red Sandstone Slab");
		redsandStep.setItemMeta(itemMeta);
		ShapedRecipe RedSandCraft = new ShapedRecipe(redsandStep);

		RedSandCraft.shape("SS ", "SS ", "   ");
		RedSandCraft.setIngredient('S', Material.STONE_SLAB2, 0);
		getServer().addRecipe(RedSandCraft);
		RedSandCraft.shape(" SS", " SS", "   ");
		getServer().addRecipe(RedSandCraft);
		RedSandCraft.shape("   ", " SS", " SS");
		getServer().addRecipe(RedSandCraft);
		RedSandCraft.shape("   ", "SS ", "SS ");
		getServer().addRecipe(RedSandCraft);
	}
	
	/**
	 * Checks if the given tool is a valid tool.
	 * 
	 * @param tool The tool to check.
	 * @return The result.
	 */
	public boolean isValidTool(ItemStack tool) {
		return (tool.getType() == Material.SHEARS
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
