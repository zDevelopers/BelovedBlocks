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
package eu.carrade.amaury.BelovedBlocks.listeners;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import fr.zcraft.zlib.core.ZLibComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;


public class PortalsBlocksListener extends ZLibComponent implements Listener
{
	/**
	 * Called when block physics occurs.
	 */
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent ev) {
		if (ev.getBlock().getType() != Material.PORTAL || !BelovedBlocks.get().getConfig().getBoolean("blocks.portals.nether.allowPortalsAnywhere"))
			return;

		ev.setCancelled(true);
	}

	/**
	 * Called when a block is placed; used to detect portals blocks broken by water and
	 * to delete the whole portals area, just like in vanilla.
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWaterPlaced(PlayerInteractEvent ev)
	{
		// Only when a water block replaces a portal one
		final Block possiblePortalBlock = ev.getClickedBlock().getRelative(ev.getBlockFace());
		if (ev.getAction() != Action.RIGHT_CLICK_BLOCK || ev.getItem().getType() != Material.WATER_BUCKET || possiblePortalBlock.getType() != Material.PORTAL)
			return;

		// Only if portals updates are cancelled. Useless else, as it's the standard behavior if
		// the blocks updates are not cancelled.
		if (!BelovedBlocks.get().getConfig().getBoolean("blocks.portals.nether.allowPortalsAnywhere"))
			return;

		destroyPortalsBlocksRegion(possiblePortalBlock, Material.PORTAL, true);
	}

	/**
	 * Destroy an area of blocks of the same kind, starting at the given block.
	 *
	 * @param startingBlock The search' starting point.
	 * @param blockMaterial The searched block.
	 * @param keepStartingBlock If {@code true}, the starting block will not be destroyed.
	 */
	private void destroyPortalsBlocksRegion(final Block startingBlock, final Material blockMaterial, final boolean keepStartingBlock)
	{
		final Set<Block> blocksToDestroy = new HashSet<>();
		blocksToDestroy.add(startingBlock);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				for(Block blockToDestroy : blocksToDestroy)
				{
					for(BlockFace face : BlockFace.values())
					{
						Block relative = blockToDestroy.getRelative(face);
						if(relative.getType() == blockMaterial)
						{
							blocksToDestroy.add(relative);
						}
					}

					if(!keepStartingBlock || !blockToDestroy.equals(startingBlock))
						blockToDestroy.setType(Material.AIR);

					blocksToDestroy.remove(blockToDestroy);
				}

				if(blocksToDestroy.isEmpty())
				{
					cancel();
				}
			}
		}.runTaskTimer(BelovedBlocks.get(), 1l, 1l);
	}
}
