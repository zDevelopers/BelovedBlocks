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
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
		
		else if(blockType == Material.LOG
				&& e.getItemInHand().getDurability() == 0
				&& name.equals(p.getSmoothOakName())) {
			
			e.getBlockPlaced().setType(Material.LOG);
			e.getBlockPlaced().setData((byte) 12);
		}
		
		else if(blockType == Material.LOG
				&& e.getItemInHand().getDurability() == 1
				&& name.equals(p.getSmoothSpruceName())) {
			
			e.getBlockPlaced().setType(Material.LOG);
			e.getBlockPlaced().setData((byte) 13);
		}
		
		else if(blockType == Material.LOG
				&& e.getItemInHand().getDurability() == 2
				&& name.equals(p.getSmoothBirchName())) {
			
			e.getBlockPlaced().setType(Material.LOG);
			e.getBlockPlaced().setData((byte) 14);
		}
		
		else if(blockType == Material.LOG
				&& e.getItemInHand().getDurability() == 3
				&& name.equals(p.getSmoothJungleName())) {
			
			e.getBlockPlaced().setType(Material.LOG);
			e.getBlockPlaced().setData((byte) 15);
		}
		
		else if(blockType == Material.LOG_2
				&& e.getItemInHand().getDurability() == 0
				&& name.equals(p.getSmoothAcaciaName())) {
			
			e.getBlockPlaced().setType(Material.LOG_2);
			e.getBlockPlaced().setData((byte) 12);
		}
		
		else if(blockType == Material.LOG_2
				&& e.getItemInHand().getDurability() == 1
				&& name.equals(p.getSmoothDarkOakName())) {
			
			e.getBlockPlaced().setType(Material.LOG_2);
			e.getBlockPlaced().setData((byte) 13);
		}
	}
	
	/**
	 * Used to prevent our tool (shears) to get leaves like a normal shear, 
	 * and to make the smooth double slabs to drop our smooth blocks.
	 * 
	 * @param ev
	 */
	@EventHandler
	public void onBlockBreaks(BlockBreakEvent ev) {
		Material itemInHandType = ev.getPlayer().getItemInHand().getType();
		Block block = ev.getBlock();
		
		// This event only concerns players in survival game mode.
		if(ev.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if(p.isValidTool(ev.getPlayer().getItemInHand())) {
				// Those blocks don't drop as items.
				if(ev.getBlock().getType() == Material.DEAD_BUSH
						|| ev.getBlock().getType() == Material.DOUBLE_PLANT
						|| ev.getBlock().getType() == Material.LONG_GRASS
						|| ev.getBlock().getType() == Material.VINE) {
						// The tool doesn't loose any durability though.
					ev.setCancelled(true);
					ev.getBlock().setType(Material.AIR);
				}
				else if(ev.getBlock().getType() == Material.LEAVES
						|| ev.getBlock().getType() == Material.LEAVES_2
						|| ev.getBlock().getType() == Material.WOOL
						|| ev.getBlock().getType() == Material.WEB
						|| ev.getBlock().getType() == Material.STRING) {
							
						// The tool loses 2 durability points.
					short newDurability = (short) (ev.getPlayer().getItemInHand().getDurability()
							+ 2 * p.increaseDurability(ev.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY)));
					
					if(newDurability > ev.getPlayer().getItemInHand().getType().getMaxDurability()) {
						ev.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
						ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ITEM_BREAK, 0.8f, 1);
					}
					else {
						ev.getPlayer().getItemInHand().setDurability(newDurability);
					}
					
					ev.getPlayer().updateInventory();
					ev.setCancelled(true);
					ev.getBlock().setType(Material.AIR);
				}
			}
			
			// When you break a smooth double slab, our item is dropped.
			else if(itemInHandType == Material.WOOD_PICKAXE
						|| itemInHandType == Material.STONE_PICKAXE
						|| itemInHandType == Material.IRON_PICKAXE
						|| itemInHandType == Material.GOLD_PICKAXE
						|| itemInHandType == Material.DIAMOND_PICKAXE) {
				
				if(block.getType() == Material.DOUBLE_STEP) {
					if(block.getData() == 8) {
						ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothStoneItem(1));
						ev.getBlock().setType(Material.AIR);
					}
					else if(block.getData() == 9) {
						ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothSandstoneItem(1));
						ev.getBlock().setType(Material.AIR);
					}
				}
				else if(block.getType() == Material.DOUBLE_STONE_SLAB2 && block.getData() == 8) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothRedSandstoneItem(1));
					ev.getBlock().setType(Material.AIR);
				}

			}
			
			if(block.getType() == Material.LOG) {
				if(block.getData() == 12) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothOakItem(1));
					ev.getBlock().setType(Material.AIR);
				}
				else if(block.getData() == 13) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothSpruceItem(1));
					ev.getBlock().setType(Material.AIR);
				}
				else if(block.getData() == 14) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothBirchItem(1));
					ev.getBlock().setType(Material.AIR);
				}
				else if(block.getData() == 15) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothJungleItem(1));
					ev.getBlock().setType(Material.AIR);
				}
			}
			
			else if(block.getType() == Material.LOG_2){
				if(block.getData() == 12) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothAcaciaItem(1));
					ev.getBlock().setType(Material.AIR);
				}
				else if(block.getData() == 13) {
					ev.getPlayer().getWorld().dropItemNaturally(block.getLocation(), p.getSmoothDarkOakItem(1));
					ev.getBlock().setType(Material.AIR);
				}
			}
		}
	}
	
	
	/**
	 * Used to prevent our tool from shearing sheeps or mushroom cows.
	 * <p>
	 * The cow seems to disappear, a relog fix that. Cannot be fixed on our side (Minecraft or CBukkit bug).
	 * 
	 * @param ev
	 */
	@EventHandler
	public void onPlayerShearEntity(PlayerShearEntityEvent ev) {
		if(p.isValidTool(ev.getPlayer().getItemInHand())) {
			ev.setCancelled(true);
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
		else if(ev.getInventory() instanceof AnvilInventory) {
			// Avoid players to rename the slab items.
			ItemStack item = ev.getInventory().getItem(0);
			if(item != null) {
			if(item.equals(p.getSmoothStoneItem(item.getAmount()))
					|| item.equals(p.getSmoothSandstoneItem(item.getAmount()))
					|| item.equals(p.getSmoothRedSandstoneItem(item.getAmount()))
					) {
				ev.getInventory().setItem(2, new ItemStack(Material.AIR,0));
			}
			}	
		}
	}
	
	/**
	 * Returns the first non-empty stack inside the give inventory.
	 * 
	 * @param i The inventory
	 * @return The ItemStack
	 */
	private ItemStack getItemStack(Inventory i) {
		for(int t = 1 ; t < i.getSize() ; t++) {
			if(i.getItem(t) != null && i.getItem(t).getType() != Material.AIR) {
				return i.getItem(t);	
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the amount of non-empty stacks in the given inventory.
	 * 
	 * @param i The inventory.
	 * @return The amount.
	 */
	private int getCount(Inventory i) {
		int amount = 0;
		for(int t = 1 ; t < i.getSize() ; t++) {
			if(i.getItem(t) != null && i.getItem(t).getType() != Material.AIR) {
				if(i.getItem(t).getAmount() != 0) {
					amount++;
				}
			}
		}
		
		return amount;
	}
	
	/**
	 * Used to filter reversed crafts.
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPreCraftEvent(PrepareItemCraftEvent e) {
		ItemStack item = getItemStack(e.getInventory());
		
		if(getCount(e.getInventory()) == 1 && item != null && item.getType() != Material.AIR) {
			// Handles the smooth stone items.
			if(item.getType() == Material.STONE || item.getType() == Material.SANDSTONE || item.getType() == Material.RED_SANDSTONE) {
				if(item.hasItemMeta() && (item.equals(p.getSmoothStoneItem(item.getAmount())) || item.equals(p.getSmoothSandstoneItem(item.getAmount())) || item.equals(p.getSmoothRedSandstoneItem(item.getAmount())))) {
				
				}
				else {
					e.getInventory().setResult(new ItemStack(Material.AIR, 1));
				}
			}
			
			// Handles the log items.
			else if(item.getType() == Material.LOG || item.getType() == Material.LOG_2){
				if(item.hasItemMeta() && (item.equals(p.getSmoothOakItem(item.getAmount()))
						|| item.equals(p.getSmoothSpruceItem(item.getAmount()))
						|| item.equals(p.getSmoothBirchItem(item.getAmount()))
						|| item.equals(p.getSmoothJungleItem(item.getAmount()))
						|| item.equals(p.getSmoothAcaciaItem(item.getAmount()))
						|| item.equals(p.getSmoothDarkOakItem(item.getAmount())))) {
					
					ItemStack result = new ItemStack(item.getType(), 1);
					result.setDurability(item.getDurability());
					e.getInventory().setResult(result);
				}
			}
		}
	}	
}
	
