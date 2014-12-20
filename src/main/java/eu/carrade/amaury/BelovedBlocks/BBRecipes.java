package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

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
}
