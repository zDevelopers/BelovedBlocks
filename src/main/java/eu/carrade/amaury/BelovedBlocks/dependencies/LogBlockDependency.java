package eu.carrade.amaury.BelovedBlocks.dependencies;

import de.diddiz.LogBlock.Actor;
import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import fr.zcraft.zlib.external.ExternalPluginComponent;
import fr.zcraft.zlib.tools.PluginLogger;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class LogBlockDependency extends ExternalPluginComponent<LogBlock>
{
    public LogBlockDependency()
    {
        super("LogBlock");
    }
    
    @Override
    protected void onLoad()
    {
        if(get().getConsumer() == null)
        {
            PluginLogger.error("Unable to access LogBlock consumer. Logging to LogBlock will be disabled.");
            this.setEnabled(false);
        }
    }

    private Consumer getConsumer()
    {
        return get().getConsumer();
    }
    
    public void logReplace(Player player, BlockState before, BlockState after)
    {
        if(!isEnabled()) return;
        getConsumer().queueBlockReplace(new Actor(player.getName(), player.getUniqueId()), before, after);
    }
}
