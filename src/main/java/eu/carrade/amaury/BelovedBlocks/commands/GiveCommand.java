/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.commands;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.BelovedItem;
import eu.carrade.amaury.BelovedBlocks.BelovedItemsManager;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import fr.zcraft.zlib.tools.items.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "give", usageParameters = "<item name> [amount] [playerName]")
public class GiveCommand extends Command
{
    @Override
    protected void run() throws CommandException
    {
        final BelovedItem belovedItem = getBelovedItemParameter(0);

        final int    amount = args.length > 1 ? getIntegerParameter(1) : 1;
        final Player player = args.length > 2 ? getPlayerParameter(2)  : playerSender();

        if (sender instanceof Player && !belovedItem.canGive(sender, player))
            throwNotAuthorized();


        final ItemStack item = belovedItem.makeItem(amount);

        ItemUtils.give(player, item);

        send(new RawText(I.t("Given "))
                        .color(ChatColor.GRAY)
                    .then("[").color(ChatColor.GRAY)
                    .then(belovedItem.getDisplayName())
                        .hover(item)
                        .color(ChatColor.GRAY)
                    .then("]").color(ChatColor.GRAY)
                    .then(I.t(" × {0} to {1}", amount, player.getDisplayName()))
                        .color(ChatColor.GRAY)
                .build()
        );
    }
    
    @Override
    protected List<String> complete()
    {
        if(args.length == 0) return getMatchingItemNames("");
        if(args.length == 1) return getMatchingItemNames(args[0]);
        if(args.length >= 3) return getMatchingPlayerNames(args[2]);
        
        return null;
    }
    
    @Override
    public boolean canExecute(CommandSender sender)
    {
        for(BelovedItem item : BelovedBlocks.getBelovedBlocksManager().getItems())
        {
            if(item.canGive(sender)) return true;
        }
        
        for(BelovedItem item : BelovedBlocks.getToolsManager().getItems())
        {
            if(item.canGive(sender)) return true;
        }
        
        return false;
    }
    
    protected BelovedItem getBelovedItemParameter(int index) throws CommandException
    {
        BelovedItem item;
        if(args.length <= index)
            throwInvalidArgument(I.t("You need to provide an item name"));
        
        item = BelovedItemsManager.getItemFromInternalName(args[index]);
        
        if(item == null)
            throwInvalidArgument(I.t("Unknown item name."));
        
        return item;
    }
    
    protected List<String> getMatchingItemNames(String prefix)
    {
        List<String> matches = new ArrayList<String>();
        
        for(BelovedItem item : BelovedBlocks.getBelovedBlocksManager().getItems())
        {
            if(!item.canGive(sender)) continue;
            if(item.getInternalName().startsWith(prefix)) matches.add(item.getInternalName());
        }
        
        for(BelovedItem item : BelovedBlocks.getToolsManager().getItems())
        {
            if(!item.canGive(sender)) continue;
            if(item.getInternalName().startsWith(prefix)) matches.add(item.getInternalName());
        }
        
        return matches;
    }
}
