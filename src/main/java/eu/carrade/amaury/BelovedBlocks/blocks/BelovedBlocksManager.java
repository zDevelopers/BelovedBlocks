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

package eu.carrade.amaury.BelovedBlocks.blocks;

import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteAcaciaLog;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteBirchLog;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteDarkOakLog;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteJungleLog;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteOakLog;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteSpruceLog;
import eu.carrade.amaury.BelovedBlocks.blocks.portals.NetherPortalBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothQuartzBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothRedSandstoneBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothSandstoneBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothStoneBlock;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class BelovedBlocksManager
{
	private Set<BelovedBlock> blocks = new HashSet<>();

	public BelovedBlocksManager()
	{
		registerBelovedBlock(new SmoothStoneBlock());
		registerBelovedBlock(new SmoothSandstoneBlock());
		registerBelovedBlock(new SmoothRedSandstoneBlock());
		registerBelovedBlock(new SmoothQuartzBlock());

		registerBelovedBlock(new CompleteOakLog());
		registerBelovedBlock(new CompleteSpruceLog());
		registerBelovedBlock(new CompleteBirchLog());
		registerBelovedBlock(new CompleteJungleLog());
		registerBelovedBlock(new CompleteAcaciaLog());
		registerBelovedBlock(new CompleteDarkOakLog());

		registerBelovedBlock(new NetherPortalBlock());
	}

	/**
	 * Registers a new block in the BelovedBlock plugin.
	 *
	 * This will silently reject any block already registered (with the same internal ID as a
	 * block already registered).
	 *
	 * @param block The block.
	 */
	public void registerBelovedBlock(BelovedBlock block)
	{
		if (!blocks.contains(block))
			blocks.add(block);
	}


	/**
	 * Returns the beloved block represented by this item stack.
	 *
	 * @param item The item.
	 *
	 * @return The {@link BelovedBlock}; {@code null} if this does not represents a beloved block.
	 */
	public BelovedBlock stackToBelovedBlock(final ItemStack item)
	{
		if (!item.hasItemMeta()) return null; // Not a beloved block.

		return getBlockFromDisplayName(item.getItemMeta().getDisplayName());
	}

	/**
	 * Returns the beloved block with the given display name.
	 *
	 * @param displayName The display name.
	 *
	 * @return The {@link BelovedBlock}; {@code null} if there isn't any
	 *         beloved block with this display name.
	 */
	public BelovedBlock getBlockFromDisplayName(String displayName)
	{
		for (BelovedBlock block : blocks)
		{
			if (block.getDisplayName().equals(displayName))
				return block;
		}

		return null;
	}

	/**
	 * Returns the beloved block with the given internal name.
	 *
	 * @param internalName The internal name.
	 *
	 * @return The {@link BelovedBlock}; {@code null} if there isn't any
	 *         beloved block with this internal name.
	 */
	public BelovedBlock getBlockFromInternalName(String internalName)
	{
		for (BelovedBlock block : blocks)
		{
			if (block.getInternalName().equals(internalName))
				return block;
		}

		return null;
	}

	/**
	 * Returns the beloved block corresponding to the given block.
	 *
	 * @param block The block.
	 *
	 * @return The {@link BelovedBlock}; {@code null} if there isn't any
	 *         beloved block corresponding to this block.
	 */
	public BelovedBlock getBlockFromBlock(Block block)
	{
		for (BelovedBlock belovedBlock : blocks)
		{
			if (belovedBlock.getPlacedBlock().sameBlockAs(block))
				return belovedBlock;
		}

		return null;
	}


	/**
	 * Returns an {@link Collections.UnmodifiableSet unmodifiable set} containing the
	 * registered {@link BelovedBlock}s.
	 *
	 * @return A {@link Collections.UnmodifiableSet set} with the registered {@link BelovedBlock}s.
	 */
	public Set<BelovedBlock> getBelovedBlocks()
	{
		return Collections.unmodifiableSet(blocks);
	}
}
