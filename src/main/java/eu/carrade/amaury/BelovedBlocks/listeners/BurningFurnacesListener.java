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

import fr.zcraft.zlib.core.ZLibComponent;
import fr.zcraft.zlib.tools.runners.RunTask;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;


public class BurningFurnacesListener extends ZLibComponent implements Listener
{
    /**
     * If indefinitely burning furnaces are opened, they are updated and loses their burning state.
     *
     * To avoid that, we re-set the burning state when these furnaces are opened. This is only
     * executed for empty furnaces, avoiding ones used as a real furnace to be always burning.
     */
    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBurningFurnaceOpened(PlayerInteractEvent ev)
    {
        if (!ev.hasBlock() || ev.getClickedBlock().getType() != Material.BURNING_FURNACE)
            return;

        final Block block = ev.getClickedBlock();
        final BlockState state = block.getState();

        if (isEmptyBurningFurnace(state))
        {
            RunTask.nextTick(new Runnable() {
                @Override
                public void run()
                {
                    // Only if it's still a furnace—e.g. not broken
                    if (block.getType() == Material.FURNACE)
                    {
                        final BlockState newState = block.getState();

                        newState.setType(Material.BURNING_FURNACE);
                        newState.setRawData(state.getRawData());

                        newState.update(true, false);
                    }
                }
            });
        }
    }


    private boolean isEmptyBurningFurnace(BlockState block)
    {
        if (!(block instanceof Furnace)) return false;

        final FurnaceInventory inventory = ((Furnace) block).getInventory();
        return isEmpty(inventory.getFuel()) && isEmpty(inventory.getSmelting()) && isEmpty(inventory.getResult());
    }

    private boolean isEmpty(ItemStack stack)
    {
        return stack == null || stack.getType() == Material.AIR;
    }
}
