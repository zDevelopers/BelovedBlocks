/**
 * Plugin BelovedBlocks
 * Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks.blocks.logs;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.utils.RecipesUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
	 *
	 * @param placedBlockDurability The durability.
	 */
	protected void setPlacedBlockDurability(byte placedBlockDurability)
	{
		this.placedBlockDurability = placedBlockDurability;
	}


	@Override
	protected ItemStack getItem()
	{
		ItemStack completeLog = new ItemStack(logMaterial);
		completeLog.setDurability(representationDurability);

		return completeLog;
	}

	@Override
	protected Set<Recipe> getCraftingRecipes()
	{
		return RecipesUtils.getSquaredRecipes(logMaterial, representationDurability, constructItem(4));
	}

	@Override
	protected Set<Recipe> getReversedCraftingRecipes()
	{
		return null;
	}

	@Override
	public void onBlockPlace(Block placedBlock)
	{
		placedBlock.setType(logMaterial);
		placedBlock.setData(placedBlockDurability);
	}
}
