package eu.carrade.amaury.BelovedBlocks.dependencies;

import fr.zcraft.zlib.external.ExternalPluginComponent;
import fr.zcraft.zlib.tools.PluginLogger;
import me.botsko.prism.Prism;
import me.botsko.prism.actionlibs.ActionType;
import me.botsko.prism.actionlibs.RecordingQueue;
import me.botsko.prism.actions.BlockChangeAction;
import me.botsko.prism.exceptions.InvalidActionException;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class PrismDependency extends ExternalPluginComponent<Prism>
{
    public PrismDependency()
    {
        super("Prism");
    }

    @Override
    protected void onLoad()
    {
        // Our actions are registered
        try
        {
            for (PrismActionType action : PrismActionType.values())
            {
                Prism.getActionRegistry().registerCustomAction(getPlugin(), action.getAction());
            }
        }
        catch (InvalidActionException e)
        {
            PluginLogger.warning("Prism is installed, but WE CANNOT REGISTER OUR ACTIONS.");
            PluginLogger.warning("Please check if BelovedBlocks is allowed to access the Prism API in the Prism's configuration file located at plugins/Prism/config.yml .");

            PluginLogger.error("Unable to register custom Prism Actions", e);
            this.setEnabled(false);
        }
    }

    /**
     * Registers a block change in Prism.
     *
     * @param player The player who changed the block.
     * @param before The BlockState before the change.
     * @param after The BlockState after the change.
     * @param actionType The action.
     */
    public void registerBlockChange(Player player, BlockState before, BlockState after, PrismActionType actionType)
    {
        if (!isEnabled()) return;

        BlockChangeAction action = new BlockChangeAction();
        action.setActionType(actionType.getAction().getName());
        action.setPlayerName(player);
        action.setBlock(after);
        action.setBlockId(after.getTypeId());
        action.setBlockSubId(after.getRawData());
        action.setOldBlockId(before.getTypeId());
        action.setOldBlockSubId(before.getRawData());

        RecordingQueue.addToQueue(action);
    }

    /**
     * Our registered Prism actions.
     */
    public enum PrismActionType
    {
        SMOOTH_BLOCK(new ActionType("bb-smooth-block", false, true, true, "BBHandler", "smoothed")),
        CARVE_BLOCK(new ActionType("bb-carve-block", false, true, true, "BBHandler", "carved")),
        MOVED_BARK(new ActionType("bb-moved-bark", false, true, true, "BBHandler", "moved the bark of"));

        private final ActionType action;

        PrismActionType(ActionType action)
        {
            this.action = action;
        }

        public ActionType getAction()
        {
            return action;
        }
    }
}
