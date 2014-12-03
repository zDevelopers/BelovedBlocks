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
		
		p.getLogger().info(ev.getClickedBlock().getType().toString());
		
		if(ev.getClickedBlock() != null && ev.getItem().getType() == Material.DIAMOND_HOE) {
			switch(ev.getClickedBlock().getType()) {
				case DOUBLE_STEP:
				//case DOUBLE_STONE_SLAB2:
				p.getLogger().info("ok");
				ev.getClickedBlock().setData((byte) (ev.getClickedBlock().getData() + 8));
				break;
			default:
				break;	
			}
		}
		
		p.getLogger().info("" + ev.getClickedBlock().getData());
	}
}
