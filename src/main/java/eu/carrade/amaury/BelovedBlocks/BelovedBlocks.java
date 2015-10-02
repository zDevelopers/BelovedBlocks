/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlocksManager;
import eu.carrade.amaury.BelovedBlocks.dependencies.LogBlockDependency;
import eu.carrade.amaury.BelovedBlocks.dependencies.PrismDependency;
import eu.carrade.amaury.BelovedBlocks.i18n.I18n;
import eu.carrade.amaury.BelovedBlocks.listeners.BlocksListener;
import eu.carrade.amaury.BelovedBlocks.listeners.CraftingListener;
import eu.carrade.amaury.BelovedBlocks.tools.ToolsManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class BelovedBlocks extends JavaPlugin
{

	private static BelovedBlocks instance = null;

	private I18n i = null;

	private BelovedBlocksManager belovedBlocksManager;
	private ToolsManager toolsManager;

	private LogBlockDependency lbDependency;
	private PrismDependency prismDependency;

	/**
	 * Returns the main plugin instance.
	 */
	public static BelovedBlocks get()
	{
		return instance;
	}

	@Override
	public void onEnable()
	{
		instance = this;

		this.saveDefaultConfig();

		if (getConfig().getString("lang") == null)
		{
			i = new I18n(this);
		}
		else
		{
			i = new I18n(this, getConfig().getString("lang"));
		}

		belovedBlocksManager = new BelovedBlocksManager();
		toolsManager = new ToolsManager();

		getServer().getPluginManager().registerEvents(new BlocksListener(), this);
		getServer().getPluginManager().registerEvents(new CraftingListener(), this);

		BBCommand commandExecutor = new BBCommand(this);
		getCommand("belovedblocks").setExecutor(commandExecutor);
		getCommand("belovedblocks").setTabCompleter(commandExecutor);

		toolsManager.registerToolsRecipes();

		lbDependency = new LogBlockDependency(this);

		// If Prism is not present a NoClassDefFoundError is thrown, and the plugin
		// cannot be loaded.
		try
		{
			prismDependency = new PrismDependency(this);
		}
		catch (NoClassDefFoundError ignored)
		{
			// Prism cannot be loaded.
		}
	}

	/**
	 * Returns the blocks manager.
	 */
	public BelovedBlocksManager getBelovedBlocksManager()
	{
		return belovedBlocksManager;
	}

	/**
	 * Returns the tools manager.
	 */
	public ToolsManager getToolsManager()
	{
		return toolsManager;
	}

	/**
	 * Returns the internationalization manager.
	 */
	public I18n getI18n()
	{
		return i;
	}

	/**
	 * Returns the interface between our plugin and LogBlock.
	 */
	public LogBlockDependency getLogBlock()
	{
		return lbDependency;
	}

	/**
	 * Returns the interface between our plugin and Prism.
	 */
	public PrismDependency getPrism()
	{
		return prismDependency;
	}
}
