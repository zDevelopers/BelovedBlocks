package eu.carrade.amaury.BelovedBlocks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BBListener implements Listener {
	
	private BelovedBlocks p = null;
	
	public BBListener(BelovedBlocks plugin) {
		p = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if(!ev.hasBlock() || !ev.hasItem()) return;
		
		if(ev.getItem().getType() == Material.DIAMOND_HOE
				&& ev.getItem().getItemMeta().getDisplayName().equals(p.getConfig().getString("tool.name"))) {
			switch(ev.getClickedBlock().getType()) {
				case DOUBLE_STEP:
				//case DOUBLE_STONE_SLAB2:
				ev.getClickedBlock().setData((byte) (ev.getClickedBlock().getData() + 8));
				break;
			default:
				break;
			}
			
			ev.setCancelled(true);
		}
	}
}
