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

import fr.zcraft.zlib.components.configuration.Configuration;
import fr.zcraft.zlib.components.configuration.ConfigurationItem;
import static fr.zcraft.zlib.components.configuration.ConfigurationItem.item;
import static fr.zcraft.zlib.components.configuration.ConfigurationItem.section;
import fr.zcraft.zlib.components.configuration.ConfigurationSection;

public class BBConfig extends Configuration
{
    static public final ConfigurationItem<String> LANGUAGE = item("lang", "");
    
    static public final ToolsSection TOOLS = section("tools", ToolsSection.class);
    static public class ToolsSection extends ConfigurationSection
    {
        public final ToolSection STONECUTTER = section("stonecutter", ToolSection.class);
        public final ToolSection SAW = section("saw", ToolSection.class);
    }
    
    static public final BlocksSection BLOCKS = section("blocks", BlocksSection.class);
    static public class BlocksSection extends ConfigurationSection
    {
        public final SlabsSection SLABS = section("slabs", SlabsSection.class);
        static public class SlabsSection extends ConfigurationSection
        {
            public final ItemSection STONE = section("stone", ItemSection.class);
            public final ItemSection SANDSTONE = section("sandstone", ItemSection.class);
            public final ItemSection RED_SANDSTONE = section("red_sandstone", ItemSection.class);
            public final ItemSection QUARTZ = section("quartz", ItemSection.class);
        }
        
        public final LogsSection LOGS = section("logs", LogsSection.class);
        static public class LogsSection extends ConfigurationSection
        {
            public final ItemSection OAK = section("oak", ItemSection.class);
            public final ItemSection SPRUCE = section("spruce", ItemSection.class);
            public final ItemSection BIRCH = section("birch", ItemSection.class);
            public final ItemSection JUNGLE = section("jungle", ItemSection.class);
            public final ItemSection ACACIA = section("acacia", ItemSection.class);
            public final ItemSection DARK_OAK = section("dark_oak", ItemSection.class);
        }
        
        public final PortalsSection PORTALS = section("portals", PortalsSection.class);
        static public class PortalsSection extends ConfigurationSection
        {
            public final ItemSection NETHER = section("nether", PortalSection.class);
            static public class PortalSection extends ItemSection
            {
                public final ConfigurationItem<Boolean> ALLOW_ANYWHERE = item("allowPortalsAnywhere", true);
            }
        }
    }
    
    static public final LogsSection LOGS = section("logs", LogsSection.class);
    static public class LogsSection extends ConfigurationSection
    {
        public final ConfigurationItem<Boolean> PRISM = item("prism", true);
        public final ConfigurationItem<Boolean> LOGBLOCK = item("logBlock", true);
    };
    
    //Helper sub-sections
    
    static public class ItemSection extends ConfigurationSection
    {
        public final ConfigurationItem<String> NAME = item("name");
        public final ConfigurationItem<Boolean> CRAFTABLE = item("craftable", true);
        public final ConfigurationItem<Boolean> GLOW = item("itemGlow", true);
        public final ConfigurationItem<Integer> AMOUNT_PER_CRAFT = item("amountPerCraft", 1);
    }
    
    static public class ToolSection extends ItemSection
    {
        public final ConfigurationItem<Boolean> USAGE_IN_LORE = item("usageInLore", true);
        public final ConfigurationItem<Integer> PERCENTAGE_BREAKING = item("percentageToBreak", 0);
    }
}
