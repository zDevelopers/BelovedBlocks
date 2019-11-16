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
package eu.carrade.amaury.BelovedBlocks.listeners;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import eu.carrade.amaury.BelovedBlocks.tools.BelovedTool;
import eu.carrade.amaury.BelovedBlocks.tools.StoneCutter;
import eu.carrade.amaury.BelovedBlocks.tools.Wrench;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.core.ZLibComponent;
import fr.zcraft.zlib.tools.items.ItemUtils;
import fr.zcraft.zlib.tools.text.MessageSender;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlocksListener extends ZLibComponent implements Listener {

	/**
	 * Used to convert the blocks from/to the seamless state with our tool.
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.hasBlock() || !event.hasItem()) {
			return;
		}
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		ItemStack item = event.getItem();
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();

		if (BelovedBlocks.getToolsManager().use(player, item, block)) {
			event.setCancelled(true);
		}
	}

	/**
	 * Used to place a real smooth block when our "smooth slabs" used as items
	 * are placed.
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent ev) {
		BelovedBlock belovedBlock = BelovedBlocks.getBelovedBlocksManager().getFromItem(ev.getItemInHand());
		if (belovedBlock == null) {
			return;
		}

		if (belovedBlock.canUse(ev.getPlayer())) {
			belovedBlock.onBlockPlace(ev.getBlockPlaced(), ev.getPlayer());
		} else {
			MessageSender.sendActionBarMessage(ev.getPlayer().getUniqueId(), I.t(I18n.getPlayerLocale(ev.getPlayer()),
					"{ce}You are not allowed to use the {0}.", belovedBlock.getDisplayName()));
			ev.setCancelled(true);
		}
	}

	/**
	 * Used to prevent our tool (shears) to get leaves like a normal shear, and
	 * to make the smooth double slabs to drop our smooth blocks.
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreaks(final BlockBreakEvent ev) {
		// This event only concerns players in survival/adventure game mode.
		if (ev.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		ItemStack item = ev.getPlayer().getItemInHand();

		BelovedTool tool = BelovedBlocks.getToolsManager().getFromItem(item);
		if (tool != null) {
			// Tools are fragile, loose extra durability
			ItemUtils.damageItemInHand(ev.getPlayer(), item, 1);

			// If they have a chance to break
			if ((float) Math.random() <= tool.getChanceToBreak()) {
				ItemUtils.breakItemInHand(ev.getPlayer(), item);
			}
		}

		// When breaking a BelovedBlock.
		ItemStack belovedDrop = BelovedBlocks.getBelovedBlocksManager().getDropForBlock(ev.getBlock());
		if (belovedDrop != null) {
			Collection<ItemStack> drops = ev.getBlock().getDrops(item);
			if (!drops.isEmpty()) {
				ev.getBlock().setType(Material.AIR);
				ItemUtils.dropNaturallyLater(ev.getBlock().getLocation(), belovedDrop);
			}
		}
	}

	/**
	 * Used to prevent our tool from shearing sheeps or mushroom cows.
	 * <p>
	 * The cow seems to disappear, a relog fix that. Cannot be fixed on our side
	 * (Minecraft or CBukkit bug).
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerShearEntity(PlayerShearEntityEvent ev) {
		if (BelovedBlocks.getToolsManager().getFromItem(ev.getPlayer().getItemInHand()) instanceof StoneCutter) {
			ev.setCancelled(true);
		}
	}


	
/**
 * Used to forbid wrench to be placed 
 * 
 * 
 *
 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onBlockPlace(PlayerInteractEvent ev) {
		if (!(BelovedBlocks.getToolsManager().getFromItem(ev.getPlayer().getItemInHand()) instanceof Wrench))
			return;
		ev.setCancelled(true);
	}

}
