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

package eu.carrade.amaury.BelovedBlocks.blocks.portals;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.SimpleBlock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Dye;

import java.util.HashSet;
import java.util.Set;


public class NetherPortalBlock extends BelovedBlock
{
	private final Integer PORTAL_AMOUNT_PER_CRAFT;

	public NetherPortalBlock()
	{
		super("blocks.portals.nether");

		setInternalName("portal-nether");

		PORTAL_AMOUNT_PER_CRAFT = BelovedBlocks.get().getConfig().getInt("blocks.portals.nether.amountPerCraft", 16);
	}

	@Override
	public ItemStack getItem()
	{
		ItemStack portal = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		portal.setDurability(DyeColor.PURPLE.getData());
		return portal;
	}

	@Override
	public Set<Recipe> getCraftingRecipes()
	{
		ShapedRecipe portalRecipe = new ShapedRecipe(constructItem(PORTAL_AMOUNT_PER_CRAFT));

		portalRecipe.shape("BOB", "PCP", "OPO");

		Dye purpleDye = new Dye(Material.INK_SACK);
		purpleDye.setColor(DyeColor.PURPLE);

		portalRecipe.setIngredient('B', Material.BLAZE_POWDER);
		portalRecipe.setIngredient('O', Material.OBSIDIAN);
		portalRecipe.setIngredient('P', Material.ENDER_PEARL);
		portalRecipe.setIngredient('C', purpleDye.toItemStack(1).getData());

		Set<Recipe> recipes = new HashSet<>();
		recipes.add(portalRecipe);
		return recipes;
	}

	@Override
	public Integer getMatterRatio()
	{
		return null;
	}

	@Override
	public ItemStack getIngredient()
	{
		return null;
	}

	@Override
	public SimpleBlock getPlacedBlock()
	{
		return new SimpleBlock(Material.PORTAL, 0);
	}

	@Override
	public boolean applyPhysics()
	{
		return false;
	}
}
