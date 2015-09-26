/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre <p/> This program
 * is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. <p/> This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. <p/> You should
 * have received a copy of the GNU General Public License along with this program.  If not, see
 * [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks.blocks.logs;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.SimpleBlock;
import eu.carrade.amaury.BelovedBlocks.utils.RecipesUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Set;


public abstract class CompleteLog extends BelovedBlock
{

	private Material logMaterial = null;

	private short representationDurability = 0;
	private byte placedBlockDurability = (byte) 0;


	public CompleteLog(String configurationKey)
	{
		super(configurationKey);
	}


	/**
	 * Sets the log material (LOG or LOG2) used by this log.
	 *
	 * @param logMaterial The material.
	 */
	protected void setLogMaterial(Material logMaterial)
	{
		this.logMaterial = logMaterial;
	}

	/**
	 * Sets the durability of the block as in the player's inventory, and sets
	 * the durability of the block in the world to {@code representationDurability + 12}.
	 *
	 * @param representationDurability The durability.
	 */
	protected void setLogEssenceDurability(short representationDurability)
	{
		this.representationDurability = representationDurability;
		this.placedBlockDurability = (byte) (representationDurability + 12);
	}

	/**
	 * Sets the durability of the block in the world.
	 *
	 * Call this only if the durability is not {@code representationDurability + 12}.
	 * After {@link #setLogEssenceDurability(short)}.
	 *
	 * @param placedBlockDurability The durability.
	 */
	protected void setPlacedBlockDurability(byte placedBlockDurability)
	{
		this.placedBlockDurability = placedBlockDurability;
	}


	@Override
	public ItemStack getItem()
	{
		ItemStack completeLog = new ItemStack(logMaterial);
		completeLog.setDurability(representationDurability);

		return completeLog;
	}

	@Override
	public Set<Recipe> getCraftingRecipes()
	{
		return RecipesUtils.getSquaredRecipes(getIngredient(), constructItem(4));
	}

	@Override
	public Integer getMatterRatio()
	{
		return 1;
	}

	@Override
	public ItemStack getIngredient()
	{
		return new ItemStack(logMaterial, 1, representationDurability);
	}

	@Override
	public SimpleBlock getPlacedBlock()
	{
		return new SimpleBlock(logMaterial, placedBlockDurability);
	}
}
