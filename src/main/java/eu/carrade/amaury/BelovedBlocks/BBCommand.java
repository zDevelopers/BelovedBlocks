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

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.carrade.amaury.BelovedBlocks.i18n.I18n;

public class BBCommand implements TabExecutor {
	
	private BelovedBlocks p;
	private I18n i;
	
	public BBCommand(BelovedBlocks plugin) {
		p = plugin;
		i = p.getI18n();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		
		if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("give"))) {
			sender.sendMessage(i.t("cmd.version", p.getDescription().getName(), p.getDescription().getVersion()));
			sender.sendMessage(i.t("cmd.help.giveTool"));
			sender.sendMessage(i.t("cmd.help.giveBlock"));
			
			return true;
		}
		
		if(args.length > 1 && args[0].equalsIgnoreCase("give")) { // /bb give ...
			
			ItemStack toGive = null;
			Player target = null;
			
			if(sender instanceof Player) {
				target = (Player) sender;
			}
			
			if(args[1].equalsIgnoreCase("tool")) { // /bb give tool [target]
				toGive = p.getToolItem();
				
				if(args.length >= 3) {
					target = p.getServer().getPlayer(args[2]);
				}
			}
			
			else if(args[1].equalsIgnoreCase("block")) { // /bb give block <stone|sandstone|red-sandstone> [amount] [target]
				if(args.length < 3) {
					sender.sendMessage(i.t("cmd.help.giveBlock"));
					return true;
				}
				else {
					int amount = 1;
					if(args.length >= 4) {
						try {
							amount = Integer.valueOf(args[3]);
						} catch(NumberFormatException e) {
							sender.sendMessage(i.t("nan", args[3]));
							return true;
						}
					}
					
					switch(args[2]) {
						case "stone":
							toGive = p.getSmoothStoneItem(amount);
							break;
						case "sandstone":
							toGive = p.getSmoothSandstoneItem(amount);
							break;
						case "red-sandstone":
							toGive = p.getSmoothRedSandstoneItem(amount);
							break;
						default:
							sender.sendMessage(i.t("cmd.give.invalidBlock"));
							return true;
					}
					
					if(args.length >= 5) {
						target = p.getServer().getPlayer(args[4]);
					}
				}
			}
			
			if(target == null) {
				if(sender instanceof Player) {
					sender.sendMessage(i.t("cmd.give.unknownPlayer"));
				}
				else {
					sender.sendMessage(i.t("cmd.give.playerRequiredFromConsole"));
				}
				
				return true;
			}
			
			if(target.getInventory().addItem(toGive).size() != 0) {
				// Inventory was full
				target.getWorld().dropItem(target.getLocation(), toGive);
			}
			else {
				target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 0.2f, 1.8f);
			}
			
			sender.sendMessage(i.t("cmd.give.given",
						toGive.getItemMeta().getDisplayName(),
						String.valueOf(toGive.getAmount()),
						target.getName()));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
