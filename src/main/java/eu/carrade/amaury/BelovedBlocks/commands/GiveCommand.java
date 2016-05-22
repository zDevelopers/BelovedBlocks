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
import fr.zcraft.zlib.tools.items.ItemUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

@CommandInfo(name = "give", usageParameters = "<item name> [amount] [playerName]")
public class GiveCommand extends Command
{
    @Override
    protected void run() throws CommandException
    {
        BelovedItem block = getBelovedItemParameter(0);
        int amount;
        Player player;
        
        amount = args.length > 1 ? getIntegerParameter(1) : 1;
        player = args.length > 2 ? getPlayerParameter(2) : playerSender();
        
        if(sender instanceof Player)
        {
            if(!block.canGive(playerSender().getUniqueId(), player.getUniqueId()))
                throwNotAuthorized();
        }
        
        ItemUtils.give(player, block.makeItem(amount));
    }
    
    @Override
    protected List<String> complete()
    {
        if(args.length == 0) return getMatchingItemNames("");
        if(args.length == 1) return getMatchingItemNames(args[0]);
        if(args.length >= 3) return getMatchingPlayerNames(args[2]);
        
        return null;
    }
    
    protected BelovedItem getBelovedItemParameter(int index) throws CommandException
    {
        BelovedItem item;
        if(args.length <= index)
            throwInvalidArgument("You need to provide an item name");
        
        item = BelovedItemsManager.getItemFromInternalName(args[index]);
        
        if(item == null)
            throwInvalidArgument("Unknown item name.");
        
        return item;
    }
    
    protected List<String> getMatchingItemNames(String prefix)
    {
        List<String> matches = new ArrayList<String>();
        
        for(BelovedItem item : BelovedBlocks.getBelovedBlocksManager().getItems())
        {
            if(item.getInternalName().startsWith(prefix)) matches.add(item.getInternalName());
        }
        
        for(BelovedItem item : BelovedBlocks.getToolsManager().getItems())
        {
            if(item.getInternalName().startsWith(prefix)) matches.add(item.getInternalName());
        }
        
        return matches;
    }
}
