/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.commands;

import eu.carrade.amaury.BelovedBlocks.BelovedBlocks;
import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlock;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.tools.items.ItemUtils;
import org.bukkit.entity.Player;

@CommandInfo(name = "give", usageParameters = "<block name> [amount] [playerName]")
public class GiveCommand extends Command
{
    @Override
    protected void run() throws CommandException
    {
        BelovedBlock block = getBelovedBlockParameter(0);
        int amount;
        Player player;
        
        amount = args.length > 1 ? getIntegerParameter(1) : 1;
        player = args.length > 2 ? getPlayerParameter(2) : playerSender();
        
        if(sender instanceof Player)
        {
            if(!block.canGive(playerSender().getUniqueId(), player.getUniqueId()))
                throwNotAuthorized();
        }
        
        ItemUtils.give(player, block.constructItem(amount));
    }
    
    protected BelovedBlock getBelovedBlockParameter(int index) throws CommandException
    {
        if(args.length <= index)
            throwInvalidArgument("You need to provide a block name");
        
        BelovedBlock block = BelovedBlocks.get().getBelovedBlocksManager().getBlockFromInternalName(args[index]);
        
        if(block == null)
            throwInvalidArgument("Unknown block name.");
        
        return block;
    }
}
