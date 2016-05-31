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
import fr.zcraft.zlib.components.configuration.ConfigurationSection;

import java.util.Locale;

import static fr.zcraft.zlib.components.configuration.ConfigurationItem.item;
import static fr.zcraft.zlib.components.configuration.ConfigurationItem.section;

public class BBConfig extends Configuration
{
    static public final ConfigurationItem<Locale> LANGUAGE = item("lang", Locale.class);
    static public final ConfigurationItem<Boolean> USE_ITEM_NAMES_FALLBACK = item("useItemNamesFallback", false);
    
    static public final ToolsSection TOOLS = section("tools", ToolsSection.class, "tool");
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
            public final BlockSection STONE = section("stone", BlockSection.class);
            public final BlockSection SANDSTONE = section("sandstone", BlockSection.class);
            public final BlockSection RED_SANDSTONE = section("red_sandstone", BlockSection.class);
            public final BlockSection QUARTZ = section("quartz", BlockSection.class);
        }
        
        public final LogsSection LOGS = section("logs", LogsSection.class);
        static public class LogsSection extends ConfigurationSection
        {
            public final BlockSection OAK = section("oak", BlockSection.class);
            public final BlockSection SPRUCE = section("spruce", BlockSection.class);
            public final BlockSection BIRCH = section("birch", BlockSection.class);
            public final BlockSection JUNGLE = section("jungle", BlockSection.class);
            public final BlockSection ACACIA = section("acacia", BlockSection.class);
            public final BlockSection DARK_OAK = section("dark_oak", BlockSection.class);
        }
        
        public final PortalsSection PORTALS = section("portals", PortalsSection.class);
        static public class PortalsSection extends ConfigurationSection
        {
            public final PortalSection NETHER = section("nether", PortalSection.class);
            public final PortalSection END = section("end", PortalSection.class);
        }

        public final OthersSection OTHERS = section("others", OthersSection.class);
        static public class OthersSection extends ConfigurationSection
        {
            public final BlockSection BURNING_FURNACE = section("burning_furnace", BlockSection.class);
        }
    }
    
    static public final LogsSection LOGS = section("logs", LogsSection.class);
    static public class LogsSection extends ConfigurationSection
    {
        public final ConfigurationItem<Boolean> PRISM = item("prism", true);
        public final ConfigurationItem<Boolean> LOGBLOCK = item("logBlock", true);
    }
    
    // Helper sub-sections
    
    static public class ItemSection extends ConfigurationSection
    {
        public final ConfigurationItem<String> NAME = item("name", String.class);
        public final ConfigurationItem<Boolean> CRAFTABLE = item("craftable", true);
        public final ConfigurationItem<Boolean> GLOW = item("itemGlow", true);
    }
    
    static public class ToolSection extends ItemSection
    {
        public final ConfigurationItem<Boolean> USAGE_IN_LORE = item("usageInLore", true);
        public final ConfigurationItem<Integer> PERCENTAGE_BREAKING = item("percentageToBreak", 0);
    }
    
    static public class BlockSection extends ItemSection
    {
        public final ConfigurationItem<Integer> AMOUNT_PER_CRAFT = item("amountPerCraft", 1);
        public final ConfigurationItem<Boolean> UNCRAFTABLE = item("uncraftable", true);
    }

    static public class PortalSection extends BlockSection
    {
        public final ConfigurationItem<Boolean> ALLOW_ANYWHERE = item("allowPortalsAnywhere", true);
    }
}
