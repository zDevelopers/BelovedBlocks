/*
 * Copyright or © or Copr. AmauryCarrade (2015)
 * 
 * http://amaury.carrade.eu
 * 
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
package eu.carrade.amaury.BelovedBlocks.tools;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.i18n.I18n;
import eu.carrade.amaury.BelovedBlocks.utils.GlowEffect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Random;


public class ToolsManager
{
	private final BelovedBlocks p;
	private final I18n i;

	private final String STONECUTTER_NAME;
	private final String SAW_NAME;


	public ToolsManager()
	{
		p = BelovedBlocks.get();
		i = p.getI18n();

		STONECUTTER_NAME = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("tool.stonecutter.name"));
		SAW_NAME = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', p.getConfig().getString("tool.saw.name"));
	}


	/**
	 * Registers the recipes for the tool.
	 */
	public void registerToolsRecipes()
	{
		if (p.getConfig().getBoolean("tool.stonecutter.craftable"))
		{
			ShapedRecipe toolRecipe = new ShapedRecipe(p.getToolsManager().getToolStonecutterItem());

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

		if (p.getConfig().getBoolean("tool.saw.craftable"))
		{
			ShapedRecipe toolRecipe = new ShapedRecipe(p.getToolsManager().getToolSawItem());

			toolRecipe.shape("IIS", "   ", "   ");
			toolRecipe.setIngredient('I', Material.IRON_INGOT);
			toolRecipe.setIngredient('S', Material.STICK);

			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "IIS", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "   ", "IIS");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("SII", "   ", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "SII", "   ");
			p.getServer().addRecipe(toolRecipe);

			toolRecipe.shape("   ", "   ", "SII");
			p.getServer().addRecipe(toolRecipe);
		}
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
		meta.setDisplayName(STONECUTTER_NAME);

		if (p.getConfig().getBoolean("tool.stonecutter.usageInLore"))
		{
			meta.setLore(Arrays.asList(i.t("tool.stonecutter.howto.line1"), i.t("tool.stonecutter.howto.line2")));
		}

		tool.setItemMeta(meta);

		if (p.getConfig().getBoolean("tool.stonecutter.itemGlow"))
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
		meta.setDisplayName(SAW_NAME);

		if (p.getConfig().getBoolean("tool.saw.usageInLore"))
		{
			if (p.getConfig().getInt("tool.saw.percentageToBreak") != 0)
			{
				meta.setLore(Arrays.asList(i.t("tool.saw.howto.line1"), i.t("tool.saw.howto.line2"), " ", i.t("tool.saw.howto.line3"), i.t("tool.saw.howto.line4")));
			}
			else
			{
				meta.setLore(Arrays.asList(i.t("tool.saw.howto.line1"), i.t("tool.saw.howto.line2")));
			}
		}

		tool.setItemMeta(meta);

		if (p.getConfig().getBoolean("tool.saw.itemGlow"))
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
				&& tool.getItemMeta().getDisplayName().equals(STONECUTTER_NAME));
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
				&& tool.getItemMeta().getDisplayName().equals(SAW_NAME));
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
		return STONECUTTER_NAME;
	}

	public String getToolSawName()
	{
		return SAW_NAME;
	}
}
