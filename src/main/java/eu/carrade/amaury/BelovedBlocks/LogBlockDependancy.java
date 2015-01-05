package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;

public class LogBlockDependancy {
	
	private Consumer lbconsumer = null;
	
	public LogBlockDependancy() {
		
		// We try to load the plugin
		Plugin lb = Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
		if(lb == null || !lb.isEnabled()) {
			return; // cannot load
		}
		
		try {
			lbconsumer = ((LogBlock) lb).getConsumer();
		} catch(Exception e) {
			// cannot load (too old?)
			Bukkit.getLogger().info("[BelovedBlocks] LogBlock is installed but cannot be loaded. Consider upgrading it.");
			return;
		}
	}
	
	public boolean isEnabled() {
		return lbconsumer != null;
	}
	
	public Consumer getConsumer() {
		return lbconsumer;
	}
}
