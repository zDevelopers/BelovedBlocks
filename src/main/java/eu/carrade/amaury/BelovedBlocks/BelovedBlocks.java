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

import eu.carrade.amaury.BelovedBlocks.blocks.*;
import eu.carrade.amaury.BelovedBlocks.dependencies.*;
import eu.carrade.amaury.BelovedBlocks.i18n.*;
import eu.carrade.amaury.BelovedBlocks.utils.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.*;

import java.util.*;


public final class BelovedBlocks extends JavaPlugin
{

	private static BelovedBlocks instance = null;

	private I18n i = null;
	private BBRecipes recipes;

	private BelovedBlocksManager belovedBlocksManager;

	private LogBlockDependency lbDependency;
	private PrismDependency prismDependency;

	private String toolStonecutterName;
	private String toolSawName;
	private String smoothStoneName;
	private String smoothSandstoneName;
	private String smoothRedSandstoneName;
	private String smoothQuartzName;
	private String smoothOakName;
	private String smoothSpruceName;
	private String smoothBirchName;
	private String smoothJungleName;
	private String smoothAcaciaName;
	private String smoothDarkOakName;

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

		getServer().getPluginManager().registerEvents(new BBListener(this), this);

		BBCommand commandExecutor = new BBCommand(this);
		getCommand("belovedblocks").setExecutor(commandExecutor);
		getCommand("belovedblocks").setTabCompleter(commandExecutor);

		toolStonecutterName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("tool.stonecutter.name"));
		toolSawName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("tool.saw.name"));

		smoothStoneName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.slabs.stone.name"));
		smoothSandstoneName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.slabs.sandstone.name"));
		smoothRedSandstoneName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.slabs.red_sandstone.name"));
		smoothQuartzName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.slabs.quartz.name"));

		smoothOakName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.oak.name"));
		smoothSpruceName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.spruce.name"));
		smoothBirchName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.birch.name"));
		smoothJungleName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.jungle.name"));
		smoothAcaciaName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.acacia.name"));
		smoothDarkOakName = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', getConfig().getString("blocks.logs.dark_oak.name"));

		recipes = new BBRecipes(this);

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
	 * Returns the item used as a replacement for the smooth stone double-slab.
	 *
	 * @param amount The amount of items in the stack.
	 * @return the item.
	 */
	public ItemStack getSmoothStoneItem(int amount)
	{
		ItemStack smoothStone = new ItemStack(Material.STONE, amount);
		smoothStone.setDurability((short) 6);

		ItemMeta itemMeta = smoothStone.getItemMeta();
		itemMeta.setDisplayName(smoothStoneName);
		smoothStone.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.slabs.stone.itemGlow"))
		{
			GlowEffect.addGlow(smoothStone);
		}

		return smoothStone;
	}

	/**
	 * Returns the item used as a replacement for the smooth sandstone double-slab.
	 *
	 * @param amount The amount of items in the stack.
	 * @return the item.
	 */
	public ItemStack getSmoothSandstoneItem(int amount)
	{
		ItemStack smoothSandstone = new ItemStack(Material.SANDSTONE, amount);
		smoothSandstone.setDurability((short) 2);

		ItemMeta itemMeta = smoothSandstone.getItemMeta();
		itemMeta.setDisplayName(smoothSandstoneName);
		smoothSandstone.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.slabs.sandstone.itemGlow"))
		{
			GlowEffect.addGlow(smoothSandstone);
		}

		return smoothSandstone;
	}

	/**
	 * Returns the item used as a replacement for the smooth red sandstone double-slab.
	 *
	 * @param amount The amount of items in the stack.
	 * @return the item.
	 */
	public ItemStack getSmoothRedSandstoneItem(int amount)
	{
		ItemStack smoothRedSandstone = new ItemStack(Material.RED_SANDSTONE, amount);
		smoothRedSandstone.setDurability((short) 2);

		ItemMeta itemMeta = smoothRedSandstone.getItemMeta();
		itemMeta.setDisplayName(smoothRedSandstoneName);
		smoothRedSandstone.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.slabs.red_sandstone.itemGlow"))
		{
			GlowEffect.addGlow(smoothRedSandstone);
		}

		return smoothRedSandstone;
	}

	/**
	 * Returns the item used as a replacement for the smooth quartz double-slab.
	 *
	 * @param amount The amount of items in the stack.
	 * @return the item.
	 */
	public ItemStack getSmoothQuartzItem(int amount)
	{
		ItemStack smoothQuartz = new ItemStack(Material.QUARTZ_BLOCK, amount);

		ItemMeta itemMeta = smoothQuartz.getItemMeta();
		itemMeta.setDisplayName(smoothQuartzName);
		smoothQuartz.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.slabs.quartz.itemGlow"))
		{
			GlowEffect.addGlow(smoothQuartz);
		}

		return smoothQuartz;
	}

	public ItemStack getSmoothOakItem(int amount)
	{
		ItemStack smoothOak = new ItemStack(Material.LOG, amount);
		smoothOak.setDurability((short) 0);

		ItemMeta itemMeta = smoothOak.getItemMeta();
		itemMeta.setDisplayName(smoothOakName);
		smoothOak.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.oak.itemGlow"))
		{
			GlowEffect.addGlow(smoothOak);
		}

		return smoothOak;
	}

	public ItemStack getSmoothSpruceItem(int amount)
	{
		ItemStack smoothSpruce = new ItemStack(Material.LOG, amount);
		smoothSpruce.setDurability((short) 1);

		ItemMeta itemMeta = smoothSpruce.getItemMeta();
		itemMeta.setDisplayName(smoothSpruceName);
		smoothSpruce.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.spruce.itemGlow"))
		{
			GlowEffect.addGlow(smoothSpruce);
		}

		return smoothSpruce;
	}

	public ItemStack getSmoothBirchItem(int amount)
	{
		ItemStack smoothBirch = new ItemStack(Material.LOG, amount);
		smoothBirch.setDurability((short) 2);

		ItemMeta itemMeta = smoothBirch.getItemMeta();
		itemMeta.setDisplayName(smoothBirchName);
		smoothBirch.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.birch.itemGlow"))
		{
			GlowEffect.addGlow(smoothBirch);
		}

		return smoothBirch;
	}

	public ItemStack getSmoothJungleItem(int amount)
	{
		ItemStack smoothJungle = new ItemStack(Material.LOG, amount);
		smoothJungle.setDurability((short) 3);

		ItemMeta itemMeta = smoothJungle.getItemMeta();
		itemMeta.setDisplayName(smoothJungleName);
		smoothJungle.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.jungle.itemGlow"))
		{
			GlowEffect.addGlow(smoothJungle);
		}

		return smoothJungle;
	}

	public ItemStack getSmoothAcaciaItem(int amount)
	{
		ItemStack smoothAcacia = new ItemStack(Material.LOG_2, amount);
		smoothAcacia.setDurability((short) 0);

		ItemMeta itemMeta = smoothAcacia.getItemMeta();
		itemMeta.setDisplayName(smoothAcaciaName);
		smoothAcacia.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.acacia.itemGlow"))
		{
			GlowEffect.addGlow(smoothAcacia);
		}

		return smoothAcacia;
	}

	public ItemStack getSmoothDarkOakItem(int amount)
	{
		ItemStack smoothDarkOak = new ItemStack(Material.LOG_2, amount);
		smoothDarkOak.setDurability((short) 1);

		ItemMeta itemMeta = smoothDarkOak.getItemMeta();
		itemMeta.setDisplayName(smoothDarkOakName);
		smoothDarkOak.setItemMeta(itemMeta);

		if (getConfig().getBoolean("blocks.logs.dark_oak.itemGlow"))
		{
			GlowEffect.addGlow(smoothDarkOak);
		}

		return smoothDarkOak;
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

	public String getSmoothStoneName()
	{
		return smoothStoneName;
	}

	public String getSmoothSandstoneName()
	{
		return smoothSandstoneName;
	}

	public String getSmoothRedSandstoneName()
	{
		return smoothRedSandstoneName;
	}

	public String getSmoothQuartzName()
	{
		return smoothQuartzName;
	}

	public String getSmoothOakName()
	{
		return smoothOakName;
	}

	public String getSmoothSpruceName()
	{
		return smoothSpruceName;
	}

	public String getSmoothBirchName()
	{
		return smoothBirchName;
	}

	public String getSmoothJungleName()
	{
		return smoothJungleName;
	}

	public String getSmoothAcaciaName()
	{
		return smoothAcaciaName;
	}

	public String getSmoothDarkOakName()
	{
		return smoothDarkOakName;
	}
}
