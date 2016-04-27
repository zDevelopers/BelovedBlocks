/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see [http://www.gnu.org/licenses/].
 */
package eu.carrade.amaury.BelovedBlocks;

import eu.carrade.amaury.BelovedBlocks.blocks.BelovedBlocksManager;
import eu.carrade.amaury.BelovedBlocks.commands.GiveCommand;
import eu.carrade.amaury.BelovedBlocks.dependencies.BelovedBlockLogger;
import eu.carrade.amaury.BelovedBlocks.listeners.BlocksListener;
import eu.carrade.amaury.BelovedBlocks.listeners.CraftingListener;
import eu.carrade.amaury.BelovedBlocks.listeners.PortalsBlocksListener;
import eu.carrade.amaury.BelovedBlocks.tools.ToolsManager;
import fr.zcraft.zlib.components.commands.Commands;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.core.ZPlugin;
import java.util.Locale;

public final class BelovedBlocks extends ZPlugin
{

    private static BelovedBlocks instance = null;

    private static BelovedBlocksManager belovedBlocksManager;
    private static ToolsManager toolsManager;

    /**
     * Returns the main plugin instance.
     */
    public static BelovedBlocks get()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;

        this.saveDefaultConfig();

        loadComponents(BBConfig.class, I18n.class,
                BlocksListener.class, CraftingListener.class, PortalsBlocksListener.class, BelovedBlockLogger.class);

        if (BBConfig.LANGUAGE.get() != null)
        {
            I18n.setPrimaryLocale(new Locale(BBConfig.LANGUAGE.get()));
        }
        else
        {
            I18n.useDefaultPrimaryLocale();
        }

        belovedBlocksManager = loadComponent(BelovedBlocksManager.class);
        toolsManager = loadComponent(ToolsManager.class);

        Commands.register("bb", GiveCommand.class);
    }

    /**
     * Returns the blocks manager.
     */
    static public BelovedBlocksManager getBelovedBlocksManager()
    {
        return belovedBlocksManager;
    }

    /**
     * Returns the tools manager.
     */
    static public ToolsManager getToolsManager()
    {
        return toolsManager;
    }

}
