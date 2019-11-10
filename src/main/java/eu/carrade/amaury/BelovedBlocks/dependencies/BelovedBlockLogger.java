/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.carrade.amaury.BelovedBlocks.dependencies;

import fr.zcraft.zlib.core.ZLib;
import fr.zcraft.zlib.core.ZLibComponent;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class BelovedBlockLogger extends ZLibComponent
{
    static private LogBlockDependency logBlockLogger;
    static private PrismDependency prismLogger;
    
    @Override
    protected void onEnable()
    {
        logBlockLogger = ZLib.loadComponent(LogBlockDependency.class);
        prismLogger = ZLib.loadComponent(PrismDependency.class);
    }
    
    static private void logEvent(Player player, BlockState before, BlockState after, String type)
    {
        if (logBlockLogger != null)
            logBlockLogger.logReplace(player, before, after);

      //  if (prismLogger != null)
       //     prismLogger.registerBlockChange(player, before, after, PrismDependency.PrismActionType.valueOf(type));
    }
    
    static public void logCarve(Player player, BlockState before, BlockState after)
    {
       logEvent(player, before, after, "CARVE_BLOCK");
    }
    
    static public void logSmooth(Player player, BlockState before, BlockState after)
    {
        logEvent(player, before, after, "SMOOTH_BLOCK");
    }
    
    static public void logMoveBark(Player player, BlockState before, BlockState after)
    {
        logEvent(player, before, after, "MOVED_BARK");
    }
}
