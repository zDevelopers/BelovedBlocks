package eu.carrade.amaury.BelovedBlocks.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Observer;
import org.bukkit.block.data.type.Piston;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.zlib.tools.items.CraftingRecipes;

public class Wrench extends BelovedTool {
	public Wrench() {
		super("wrench", Material.TRIPWIRE_HOOK, BBConfig.TOOLS.WRENCH);
	}

	@Override
	public Iterable<Recipe> getCraftingRecipes() {
		ArrayList<Recipe> recipes = new ArrayList<>();

		ShapedRecipe baseRecipe = CraftingRecipes.shaped(makeItem(1), Material.IRON_INGOT);

		recipes.add(CraftingRecipes.shaped(baseRecipe, " A ", " AA", "A  "));
		recipes.add(CraftingRecipes.shaped(baseRecipe, " A ", "AA ", "  A"));

		return recipes;
	}

	@Override
	public boolean useableOn(Block block) {
		Material type = block.getType();
		PluginLogger.info("Type block {0}", type);
		// boolean glazed_terracotta = type == Material.BLACK_GLAZED_TERRACOTTA
		// ||type == Material.BLUE_GLAZED_TERRACOTTA ||type ==
		// Material.BROWN_GLAZED_TERRACOTTA ||type ==
		// Material.CYAN_GLAZED_TERRACOTTA ||type ==
		// Material.GRAY_GLAZED_TERRACOTTA ||type ==
		// Material.GREEN_GLAZED_TERRACOTTA ||type ==
		// Material.LIGHT_BLUE_GLAZED_TERRACOTTA ||type ==
		// Material.LIGHT_GRAY_GLAZED_TERRACOTTA ||type ==
		// Material.LIME_GLAZED_TERRACOTTA ||type ==
		// Material.MAGENTA_GLAZED_TERRACOTTA ||type ==
		// Material.ORANGE_GLAZED_TERRACOTTA ||type ==
		// Material.PINK_GLAZED_TERRACOTTA ||type ==
		// Material.PURPLE_GLAZED_TERRACOTTA ||type ==
		// Material.RED_GLAZED_TERRACOTTA ||type ==
		// Material.WHITE_GLAZED_TERRACOTTA ||type ==
		// Material.YELLOW_GLAZED_TERRACOTTA ;

		boolean glazed_terracotta = type.name().endsWith("_GLAZED_TERRACOTTA");
		
		boolean logs = type == Material.ACACIA_LOG || type == Material.BIRCH_LOG || type == Material.DARK_OAK_LOG
				|| type == Material.JUNGLE_LOG || type == Material.SPRUCE_LOG || type == Material.OAK_LOG;
		boolean stripped_logs = type == Material.STRIPPED_ACACIA_LOG || type == Material.STRIPPED_BIRCH_LOG
				|| type == Material.STRIPPED_DARK_OAK_LOG || type == Material.STRIPPED_JUNGLE_LOG
				|| type == Material.STRIPPED_OAK_LOG || type == Material.STRIPPED_SPRUCE_LOG;
		boolean pistons = type == Material.PISTON || type == Material.STICKY_PISTON;
		boolean pumpkins = type == Material.JACK_O_LANTERN || type == Material.CARVED_PUMPKIN;
		boolean bone = type == Material.BONE_BLOCK;
		boolean observer = type == Material.OBSERVER;
		return glazed_terracotta || logs || stripped_logs || pistons || pumpkins || bone || observer;
	}
	
	@Override
	protected boolean onUse(Player player, Block block) {
		BlockState before = block.getState();
		
		PluginLogger.info("type of the block {0} data of the block {1}", block.getType(),block.getBlockData());
		BlockState state= block.getState();
		
		if (this.useableOn(block)) {

			BlockData blockData = block.getBlockData();
			if (blockData instanceof Directional) {
				PluginLogger.info("Directional");
				Directional directional = (Directional) blockData;
				BlockFace face=directional.getFacing();
				int size=BlockFace.values().length;
				directional.setFacing(BlockFace.values()[face.ordinal()%size]);
				
				block.setBlockData(directional);
			}
			if (blockData instanceof Orientable) {
				PluginLogger.info("Orientable");
				Orientable orientable = (Orientable) blockData;
				Axis axis=orientable.getAxis();
				orientable.setAxis(Axis.values()[axis.ordinal()%3]);
				block.setBlockData(orientable);
			}
			if (blockData instanceof Piston) {
				PluginLogger.info("Piston");
				Piston piston = (Piston) blockData;
				BlockFace face=piston.getFacing();
				int size=BlockFace.values().length;
				piston.setFacing(BlockFace.values()[face.ordinal()%size]);
				block.setBlockData(piston);
			}
			if (blockData instanceof Observer) {
				PluginLogger.info("Observer");
				Observer observer = (Observer) blockData;
				BlockFace face=observer.getFacing();
				int size=BlockFace.values().length;
				observer.setFacing(BlockFace.values()[face.ordinal()%size]);
				block.setBlockData(observer);
			}
		}
		state.setBlockData(block.getBlockData());
		PluginLogger.info("type of the block {0} data of the block {1}", block.getType(),block.getBlockData());
		
		
		BlockState after = block.getState();
		// BelovedBlockLogger.logMoveBark(player, before, after);

		return true;
	}

	@Override
	protected List<String> getUsage() {
		return Collections.singletonList(
				I.t("{gray}Left-Right click can rotate a block, shift click allow for faster rotation ."));
	}
}
