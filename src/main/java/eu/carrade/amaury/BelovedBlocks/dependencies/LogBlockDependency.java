package eu.carrade.amaury.BelovedBlocks.dependencies;

import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class LogBlockDependency
{

	private boolean enabled;
	private Consumer lbconsumer = null;

	public LogBlockDependency(BelovedBlocks p)
	{

		// We try to load the plugin
		Plugin lb = Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
		if (lb == null || !lb.isEnabled())
		{
			return; // cannot load
		}

		try
		{
			lbconsumer = ((LogBlock) lb).getConsumer();
		}
		catch (Exception e)
		{
			// cannot load (too old?)
			Bukkit.getLogger().info("[BelovedBlocks] LogBlock is installed but cannot be loaded. Consider upgrading it.");
			return;
		}

		enabled = p.getConfig().getBoolean("logs.logBlock");
	}

	public boolean isEnabled()
	{
		return enabled && lbconsumer != null;
	}

	public Consumer getConsumer()
	{
		return lbconsumer;
	}
}
