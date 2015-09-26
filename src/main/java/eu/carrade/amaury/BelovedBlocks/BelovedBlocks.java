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
import eu.carrade.amaury.BelovedBlocks.utils.GlowEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;


public final class BelovedBlocks extends JavaPlugin
{

	private static BelovedBlocks instance = null;

	private I18n i = null;

	private BelovedBlocksManager belovedBlocksManager;

	private LogBlockDependency lbDependency;
	private PrismDependency prismDependency;

	private String toolStonecutterName;
	private String toolSawName;

	/**
	 * Returns the main plugin instance.
	 *
	 * @return The instance.
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

		getServer().getPluginManager().registerEvents(new BlocksListener(), this);
		getServer().getPluginManager().registerEvents(new CraftingListener(), this);

		BBCommand commandExecutor = new BBCommand(this);
		getCommand("belovedblocks").setExecutor(commandExecutor);
		getCommand("belovedblocks").setTabCompleter(commandExecutor);

		toolStonecutterName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("tool.stonecutter.name"));
		toolSawName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("tool.saw.name"));

		new BBRecipes(this);

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
	 *
	 * @return
	 */
	public BelovedBlocksManager getBelovedBlocksManager()
	{
		return belovedBlocksManager;
	}

	/**
	 * Returns the internationalization manager.
	 *
	 * @return
	 */
	public I18n getI18n()
	{
		return i;
	}

	/**
	 * Returns the interface between our plugin and LogBlock.
	 *
	 * @return
	 */
	public LogBlockDependency getLogBlock()
	{
		return lbDependency;
	}

	/**
	 * Returns the interface between our plugin and Prism.
	 *
	 * @return
	 */
	public PrismDependency getPrism()
	{
		return prismDependency;
	}

	/**
	 * Returns the item used as the stonecutter.
	 *
	 * @return the item.
	 */
	public ItemStack getToolStonecutterItem()
	{
		ItemStack tool = new ItemStack(Material.SHEARS);

		ItemMeta meta = tool.getItemMeta();
		meta.setDisplayName(toolStonecutterName);

		if (getConfig().getBoolean("tool.stonecutter.usageInLore"))
		{
			meta.setLore(Arrays.asList(i.t("tool.stonecutter.howto.line1"), i.t("tool.stonecutter.howto.line2")));
		}

		tool.setItemMeta(meta);

		if (getConfig().getBoolean("tool.stonecutter.itemGlow"))
		{
			GlowEffect.addGlow(tool);
		}

		return tool;
	}

	/**
	 * Returns the item used as the saw.
	 *
	 * @return the item.
	 */
	public ItemStack getToolSawItem()
	{
		ItemStack tool = new ItemStack(Material.IRON_AXE);

		ItemMeta meta = tool.getItemMeta();
		meta.setDisplayName(toolSawName);

		if (getConfig().getBoolean("tool.saw.usageInLore"))
		{
			if (getConfig().getInt("tool.saw.percentageToBreak") != 0)
			{
				meta.setLore(Arrays.asList(i.t("tool.saw.howto.line1"), i.t("tool.saw.howto.line2"), " ", i.t("tool.saw.howto.line3"), i.t("tool.saw.howto.line4")));
			}
			else
			{
				meta.setLore(Arrays.asList(i.t("tool.saw.howto.line1"), i.t("tool.saw.howto.line2")));
			}
		}

		tool.setItemMeta(meta);

		if (getConfig().getBoolean("tool.saw.itemGlow"))
		{
			GlowEffect.addGlow(tool);
		}

		return tool;
	}



	/**
	 * Checks if the given tool is a valid stonecutter.
	 *
	 * @param tool The tool to check.
	 * @return The result.
	 */
	public boolean isValidStonecutterTool(ItemStack tool)
	{
		return (tool != null
				&& tool.getType() == Material.SHEARS
				&& tool.getItemMeta().getDisplayName() != null
				&& tool.getItemMeta().getDisplayName().equals(toolStonecutterName));
	}

	/**
	 * Checks if the given tool is a valid saw.
	 *
	 * @param tool The tool to check.
	 * @return The result.
	 */
	public boolean isValidSawTool(ItemStack tool)
	{
		return (tool != null
				&& tool.getType() == Material.IRON_AXE
				&& tool.getItemMeta().getDisplayName() != null
				&& tool.getItemMeta().getDisplayName().equals(toolSawName));
	}

	/**
	 * Calculates the new durability, taking into account the unbreaking enchantment.
	 *
	 * @param unbreakingLevel The Unbreaking level (0 if not enchanted with that).
	 * @return The durability to add.
	 */
	public short increaseDurability(int unbreakingLevel)
	{
		if (new Random().nextInt(100) <= (100 / (unbreakingLevel + 1)))
		{
			return 1;
		}

		return 0;
	}

	public String getToolStonecutterName()
	{
		return toolStonecutterName;
	}

	public String getToolSawName()
	{
		return toolSawName;
	}
}
