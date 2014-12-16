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
		registerOak();
		registerSpruce();
		registerBirch();
		registerJungle();
		registerAcacia();
		registerDarkOak();
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
	
	public void registerOak() {
		if(p.getConfig().getBoolean("blocks.logs.oak.craftable")) {
			ShapedRecipe smoothOakLogRecipe = new ShapedRecipe(p.getSmoothOakItem(4));
	
			smoothOakLogRecipe.shape("LL ", "LL ", "   ");
			smoothOakLogRecipe.setIngredient('L', Material.LOG, 0);
			p.getServer().addRecipe(smoothOakLogRecipe);
			
			smoothOakLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothOakLogRecipe);
			
			smoothOakLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothOakLogRecipe);
			
			smoothOakLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothOakLogRecipe);
		}
	}
	
	public void registerSpruce() {
		if(p.getConfig().getBoolean("blocks.logs.spruce.craftable")) {
			ShapedRecipe smoothSpruceLogRecipe = new ShapedRecipe(p.getSmoothSpruceItem(4));
	
			smoothSpruceLogRecipe.shape("LL ", "LL ", "   ");
			smoothSpruceLogRecipe.setIngredient('L', Material.LOG, 1);
			p.getServer().addRecipe(smoothSpruceLogRecipe);
			
			smoothSpruceLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothSpruceLogRecipe);
			
			smoothSpruceLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothSpruceLogRecipe);
			
			smoothSpruceLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothSpruceLogRecipe);
		}
	}
	
	public void registerBirch() {
		if(p.getConfig().getBoolean("blocks.logs.birch.craftable")) {
			ShapedRecipe smoothBirchLogRecipe = new ShapedRecipe(p.getSmoothBirchItem(4));
	
			smoothBirchLogRecipe.shape("LL ", "LL ", "   ");
			smoothBirchLogRecipe.setIngredient('L', Material.LOG, 2);
			p.getServer().addRecipe(smoothBirchLogRecipe);
			
			smoothBirchLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothBirchLogRecipe);
			
			smoothBirchLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothBirchLogRecipe);
			
			smoothBirchLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothBirchLogRecipe);
		}
	}
	
	public void registerJungle() {
		if(p.getConfig().getBoolean("blocks.logs.jungle.craftable")) {
			ShapedRecipe smoothJungleLogRecipe = new ShapedRecipe(p.getSmoothJungleItem(4));
	
			smoothJungleLogRecipe.shape("LL ", "LL ", "   ");
			smoothJungleLogRecipe.setIngredient('L', Material.LOG, 3);
			p.getServer().addRecipe(smoothJungleLogRecipe);
			
			smoothJungleLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothJungleLogRecipe);
			
			smoothJungleLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothJungleLogRecipe);
			
			smoothJungleLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothJungleLogRecipe);
		}
	}
	
	public void registerAcacia() {
		if(p.getConfig().getBoolean("blocks.logs.acacia.craftable")) {
			ShapedRecipe smoothAcaciaLogRecipe = new ShapedRecipe(p.getSmoothAcaciaItem(4));
	
			smoothAcaciaLogRecipe.shape("LL ", "LL ", "   ");
			smoothAcaciaLogRecipe.setIngredient('L', Material.LOG_2, 0);
			p.getServer().addRecipe(smoothAcaciaLogRecipe);
			
			smoothAcaciaLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothAcaciaLogRecipe);
			
			smoothAcaciaLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothAcaciaLogRecipe);
			
			smoothAcaciaLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothAcaciaLogRecipe);
		}
	}
	
	public void registerDarkOak() {
		if(p.getConfig().getBoolean("blocks.logs.dark_oak.craftable")) {
			ShapedRecipe smoothDarkOakLogRecipe = new ShapedRecipe(p.getSmoothDarkOakItem(4));
	
			smoothDarkOakLogRecipe.shape("LL ", "LL ", "   ");
			smoothDarkOakLogRecipe.setIngredient('L', Material.LOG_2, 1);
			p.getServer().addRecipe(smoothDarkOakLogRecipe);
			
			smoothDarkOakLogRecipe.shape(" LL", " LL", "   ");
			p.getServer().addRecipe(smoothDarkOakLogRecipe);
			
			smoothDarkOakLogRecipe.shape("   ", " LL", " LL");
			p.getServer().addRecipe(smoothDarkOakLogRecipe);
			
			smoothDarkOakLogRecipe.shape("   ", "LL ", "LL ");
			p.getServer().addRecipe(smoothDarkOakLogRecipe);
		}
	}
	
}
