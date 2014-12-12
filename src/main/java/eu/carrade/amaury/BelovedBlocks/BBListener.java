package eu.carrade.amaury.BelovedBlocks;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BBListener implements Listener {
	
	private BelovedBlocks p = null;
	
	public BBListener(BelovedBlocks plugin) {
		p = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if(!ev.hasBlock() || !ev.hasItem()) return;
		
		ItemStack item = ev.getItem();
		Block block = ev.getClickedBlock();
		Player player = ev.getPlayer();
		
		if(item.getType() == Material.DIAMOND_HOE
				&& item.getItemMeta().getDisplayName().equals(p.getConfig().getString("tool.name"))) {
			switch(ev.getClickedBlock().getType()) {
				case DOUBLE_STEP:
				case DOUBLE_STONE_SLAB2:
					block.setData((byte) (ev.getClickedBlock().getData() + 8));
					
					if(player.getGameMode() != GameMode.CREATIVE) {
						short newDurability = item.getDurability();
						if(!item.containsEnchantment(Enchantment.DURABILITY)) {
							newDurability++;
						}
						else {
							int level = item.getEnchantmentLevel(Enchantment.DURABILITY);
							if(new Random().nextInt(100) <= (100/(level + 1))) {
								newDurability++;
							}
						}
						
						item.setDurability(newDurability);
						player.getInventory().setItemInHand(item);
						player.updateInventory();
					}
					
					break;
			default:
				break;
			}
			
			ev.setCancelled(true);
		}
	}
}
