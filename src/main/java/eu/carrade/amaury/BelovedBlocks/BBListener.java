/**
 *  Plugin BelovedBlocks
 *  Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BBListener implements Listener {
	
	private BelovedBlocks p = null;
	
	public BBListener(BelovedBlocks plugin) {
		p = plugin;
	}
	
	/**
	 * Used to convert the blocks from/to the seamless state with our tool.
	 * 
	 * @param ev
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if(!ev.hasBlock() || !ev.hasItem()) return;
		
		ItemStack item = ev.getItem();
		Block block = ev.getClickedBlock();
		Material type = block.getType();
		Player player = ev.getPlayer();
		
		if(p.isValidTool(item)) {
			
			if((type == Material.DOUBLE_STEP || type == Material.DOUBLE_STONE_SLAB2)
					&& ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				block.setData((byte) (ev.getClickedBlock().getData() + 8));
				
				// Durability
				if(player.getGameMode() != GameMode.CREATIVE) {
					short newDurability = (short) (item.getDurability()
							+ p.increaseDurability(item.getEnchantmentLevel(Enchantment.DURABILITY)));
					
					if(newDurability > item.getType().getMaxDurability()) {
						player.getInventory().setItemInHand(new ItemStack(Material.AIR));
						player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
					}
					else {
						item.setDurability(newDurability);
						player.getInventory().setItemInHand(item);
					}
					
					player.updateInventory();
				}
				
				ev.setCancelled(true);
			}
		}
	}
	
	/**
	 * Used to place a real smooth block when our "smooth slabs" used as items are
	 * placed.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Material blockType = e.getBlockPlaced().getType();
		ItemStack item = e.getItemInHand();
		String name = item.getItemMeta().getDisplayName();
		
		// If the display name is null, this is a vanilla block, not handled here.
		if(name == null) return;
		
		if(blockType == Material.STONE
				&& item.getDurability() == 6
				&& name.equals(p.getSmoothStoneName())) {
			
			e.getBlockPlaced().setType(Material.DOUBLE_STEP);
			e.getBlockPlaced().setData((byte) 8);
		}
		
		else if(blockType == Material.SANDSTONE
				&& e.getItemInHand().getDurability() == 2
				&& name.equals(p.getSmoothSandstoneName())) {
			
			e.getBlockPlaced().setType(Material.DOUBLE_STEP);
			e.getBlockPlaced().setData((byte) 9);
		}
		
		else if(e.getBlockPlaced().getType() == Material.RED_SANDSTONE
				&& e.getItemInHand().getDurability() == 2
				&& name.equals(p.getSmoothRedSandstoneName())) {
			
			e.getBlockPlaced().setType(Material.DOUBLE_STONE_SLAB2);
			e.getBlockPlaced().setData((byte) 8);
		}
	}
	
	/**
	 * Used to prevent our tool (shears) to get leaves like a normal shear.
	 * 
	 * @param ev
	 */
	@EventHandler
	public void onBlockBreaks(BlockBreakEvent ev) {
		if(p.isValidTool(ev.getPlayer().getItemInHand())) {
			if(ev.getBlock().getType() == Material.LEAVES || ev.getBlock().getType() == Material.LEAVES_2) {
				ev.setCancelled(true);
				ev.getBlock().setType(Material.AIR);
			}
		}
	}
	
	/**
	 * Workaround to fix the crafting grid being not updated when the item is taken
	 * from the grid.
	 * 
	 * @param ev
	 */
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent ev) {
		if(ev.getInventory() instanceof CraftingInventory && ev.getSlot() == 0) {
			p.getServer().getScheduler().runTaskLater(p, new Runnable() {

				@Override
				public void run() {
					for(HumanEntity viewer : ev.getViewers()) {
						if(viewer instanceof Player) {
							((Player) viewer).updateInventory();
						}
					}
				}
					
			}, 1l);
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e){
		
		ItemStack item = new ItemStack(Material.STEP, 1);
		ItemMeta itemMeta = item.getItemMeta();
		
		if(e.getBlock().getType() == Material.DOUBLE_STEP){
			if(e.getBlock().getData() == 8){
			itemMeta.setDisplayName(ChatColor.RESET + p.getConfig().getString("blocks.slabs.stone.name"));
			item.setItemMeta(itemMeta);
			e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
			e.getBlock().setType(Material.AIR);
			}else if(e.getBlock().getData() == 9){
				item.setDurability((short) 1);
				itemMeta.setDisplayName(ChatColor.RESET + p.getConfig().getString("blocks.slabs.sandstone.name"));
				item.setItemMeta(itemMeta);
				e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
				e.getBlock().setType(Material.AIR);
			}
		}else if(e.getBlock().getType() == Material.DOUBLE_STONE_SLAB2){
			if(e.getBlock().getData() == 8){
			item.setType(Material.STONE_SLAB2);
			itemMeta.setDisplayName(ChatColor.RESET + p.getConfig().getString("blocks.slabs.red_sandstone.name"));
			item.setItemMeta(itemMeta);
			e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
			e.getBlock().setType(Material.AIR);
			}
		}
	}
}
