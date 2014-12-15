package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class BBRecipes {

	private BelovedBlocks p = null;
	
	public BBRecipes(BelovedBlocks plugin){
		p = plugin;
		
		registerTool();
		registerStone();
		registerSand();
		registerRedsand();
	}
	
	
	/**
	 * Registers the recipes for the tool.
	 */
	public void registerTool() {
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
	 * Registers the recipes for the smooth stone double-slab item.
	 */
	public void registerStone() {
		if(p.getConfig().getBoolean("blocks.slabs.stone.craftable")) {
			ShapedRecipe smoothStoneSlabRecipe = new ShapedRecipe(p.getSmoothStoneItem(2));
			
			smoothStoneSlabRecipe.shape("SS ", "SS ", "   ");
			smoothStoneSlabRecipe.setIngredient('S', Material.STEP, 0);
			p.getServer().addRecipe(smoothStoneSlabRecipe);
			
			smoothStoneSlabRecipe.shape(" SS", " SS", "   ");
			p.getServer().addRecipe(smoothStoneSlabRecipe);
			
			smoothStoneSlabRecipe.shape("   ", " SS", " SS");
			p.getServer().addRecipe(smoothStoneSlabRecipe);
			
			smoothStoneSlabRecipe.shape("   ", "SS ", "SS ");
			p.getServer().addRecipe(smoothStoneSlabRecipe);
		}
	}
	
	/**
	 * Registers the recipes for the smooth sandstone double-slab item.
	 */
	public void registerSand() {
		if(p.getConfig().getBoolean("blocks.slabs.sandstone.craftable")) {
			ShapedRecipe smoothSandstoneSlabRecipe = new ShapedRecipe(p.getSmoothSandstoneItem(2));
			
			smoothSandstoneSlabRecipe.shape("SS ", "SS ", "   ");
			smoothSandstoneSlabRecipe.setIngredient('S', Material.STEP, 1);
			p.getServer().addRecipe(smoothSandstoneSlabRecipe);
			
			smoothSandstoneSlabRecipe.shape(" SS", " SS", "   ");
			p.getServer().addRecipe(smoothSandstoneSlabRecipe);
			
			smoothSandstoneSlabRecipe.shape("   ", " SS", " SS");
			p.getServer().addRecipe(smoothSandstoneSlabRecipe);
			
			smoothSandstoneSlabRecipe.shape("   ", "SS ", "SS ");
			p.getServer().addRecipe(smoothSandstoneSlabRecipe);
		}
	}
	
	/**
	 * Registers the recipes for the smooth red sandstone double-slab item.
	 */
	public void registerRedsand() {
		if(p.getConfig().getBoolean("blocks.slabs.red_sandstone.craftable")) {
			ShapedRecipe smoothRedSandstoneSlabRecipe = new ShapedRecipe(p.getSmoothRedSandstoneItem(2));
	
			smoothRedSandstoneSlabRecipe.shape("SS ", "SS ", "   ");
			smoothRedSandstoneSlabRecipe.setIngredient('S', Material.STONE_SLAB2, 0);
			p.getServer().addRecipe(smoothRedSandstoneSlabRecipe);
			
			smoothRedSandstoneSlabRecipe.shape(" SS", " SS", "   ");
			p.getServer().addRecipe(smoothRedSandstoneSlabRecipe);
			
			smoothRedSandstoneSlabRecipe.shape("   ", " SS", " SS");
			p.getServer().addRecipe(smoothRedSandstoneSlabRecipe);
			
			smoothRedSandstoneSlabRecipe.shape("   ", "SS ", "SS ");
			p.getServer().addRecipe(smoothRedSandstoneSlabRecipe);
		}
	}
	
	
}
