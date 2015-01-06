package eu.carrade.amaury.BelovedBlocks.dependancies;

import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionType;
import me.botsko.prism.actionlibs.RecordingQueue;
import me.botsko.prism.actions.BlockChangeAction;
import me.botsko.prism.exceptions.InvalidActionException;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;

public class PrismDependancy {
	
	private boolean enabled;
	private Prism prism = null;
	
	public PrismDependancy(BelovedBlocks p) {
		
		// We try to load the plugin
		Plugin prismTest = Bukkit.getServer().getPluginManager().getPlugin("Prism");
		if(prismTest == null || !prismTest.isEnabled()) {
			return; // cannot load
		}
		
		prism = (Prism) prismTest;
		
		enabled = p.getConfig().getBoolean("logs.prism");
		
		// We registers our action
		try {
			Prism.getActionRegistry().registerCustomAction(p,
					new ActionType("bb-smooth-block", false, true, true, "BBHandler", "smoothed"));
			Prism.getActionRegistry().registerCustomAction(p,
					new ActionType("bb-carve-block", false, true, true, "BBHandler", "carved"));
			Prism.getActionRegistry().registerCustomAction(p,
					new ActionType("bb-moved-bark", false, true, true, "BBHandler", "moved the bark of"));
			
		} catch (InvalidActionException e) {
			// If something you register is incorrect, Prism will let you know in e.getMessage();
			p.getLogger().warning("Prism is installed, but WE CANNOT REGISTER OUR ACTIONS.");
			p.getLogger().info("Please check if BelovedBlock is allowed to access the Prism API in the Prism's configuration file located at plugins/Prism/config.yml .");
			p.getLogger().info("The stack trace is displayed below.");
			
			e.printStackTrace();
			
			prism = null;
		}
	}
	
	public boolean isEnabled() {
		return enabled && prism != null;
	}
	
	public Prism getPrism() {
		return prism;
	}
	
	/**
	 * Registers a block change in Prism.
	 * 
	 * @param player The player who changed the block.
	 * @param before The BlockState before the change.
	 * @param after The BlockState after the change.
	 * @param actionType One of {@code bb-smooth-block}, {@code bb-carve-block} and {@code bb-moved-bark}.
	 */
	public void registerBlockChange(Player player, BlockState before, BlockState after, String actionType) {
		if(!isEnabled()) return;
		
		BlockChangeAction action = new BlockChangeAction();
		action.setActionType(actionType);
		action.setPlayerName(player);
		action.setBlock(after);
		action.setBlockId(after.getTypeId());
		action.setBlockSubId(after.getRawData());
		action.setOldBlockId(before.getTypeId());
		action.setOldBlockSubId(before.getRawData());
		
		RecordingQueue.addToQueue(action);
	}
}
