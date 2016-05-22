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
package eu.carrade.amaury.BelovedBlocks.blocks;

import eu.carrade.amaury.BelovedBlocks.BBConfig;
import eu.carrade.amaury.BelovedBlocks.BelovedItemsManager;
import eu.carrade.amaury.BelovedBlocks.blocks.logs.CompleteLog;
import eu.carrade.amaury.BelovedBlocks.blocks.portals.NetherPortalBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothQuartzBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothRedSandstoneBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothSandstoneBlock;
import eu.carrade.amaury.BelovedBlocks.blocks.stones.SmoothStoneBlock;
import fr.zcraft.zlib.tools.items.CraftingRecipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BelovedBlocksManager extends BelovedItemsManager<BelovedBlock>
{
    @Override
    protected void onEnable()
    {
        register(new SmoothStoneBlock());
        register(new SmoothSandstoneBlock());
        register(new SmoothRedSandstoneBlock());
        register(new SmoothQuartzBlock());

        
        register(new CompleteLog("oak",    Material.LOG, 0, BBConfig.BLOCKS.LOGS.OAK));
        register(new CompleteLog("spruce", Material.LOG, 1, BBConfig.BLOCKS.LOGS.SPRUCE));
        register(new CompleteLog("birch",  Material.LOG, 2, BBConfig.BLOCKS.LOGS.BIRCH));
        register(new CompleteLog("jungle", Material.LOG, 3, BBConfig.BLOCKS.LOGS.JUNGLE));
        register(new CompleteLog("acacia",   Material.LOG_2, 0, BBConfig.BLOCKS.LOGS.ACACIA));
        register(new CompleteLog("dark-oak", Material.LOG_2, 1, BBConfig.BLOCKS.LOGS.DARK_OAK));

        register(new NetherPortalBlock());
    }
    
    @Override
    protected <T extends BelovedBlock> T register(T block)
    {
        if(block.isUncraftable()) CraftingRecipes.add(block.getUncraftingRecipe());
        return super.register(block);
    }

    /**
     * Returns the beloved block corresponding to the given block.
     *
     * @param block The block.
     *
     * @return The {@link BelovedBlock}; {@code null} if there isn't any beloved
     * block corresponding to this block.
     */
    public BelovedBlock getFromBlock(Block block)
    {
        for (BelovedBlock belovedBlock : getItems())
        {
            if (belovedBlock.getPlacedBlock(null).sameBlockAs(block))
                return belovedBlock;
        }

        return null;
    }
    
    public ItemStack getDropForBlock(Block block)
    {
        BelovedBlock belovedBlock = getFromBlock(block);
        if(belovedBlock == null) return null;
        return belovedBlock.makeItem(1);
    }
}
