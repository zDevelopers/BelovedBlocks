/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p/>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see [http://www.gnu.org/licenses/].
 */
package eu.carrade.amaury.BelovedBlocks.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import fr.zcraft.zlib.tools.PluginLogger;


public class WorldBlock
{
    private final Material type;
    private BlockData dataValue;

    public WorldBlock(Material type, BlockData dataValue)
    {
        this.type = type;
        this.dataValue = dataValue;
    }
    public WorldBlock(Material type)
    {
        this.type = type;
        dataValue=getType().createBlockData();
    }

    public void setData(BlockData dataValue){
    	this.dataValue=dataValue;
    }
    /**
     * Updates the given block to be the same one as this block.
     *
     * @param block The block.
     */
    public void updateBlock(Block block)
    {
        block.setType(getType());
        PluginLogger.info("update type {0}  {1}", getType().name(),block.getType().name());
        block.setBlockData(getDataValue());
    }

    /**
     * Checks if this block is the same as the given block in the world.
     *
     * @param block The block to compare.
     *
     * @return {@code true} if it's the same one.
     */
    public boolean sameBlockAs(Block block)
    {
        return block.getType().equals(getType());// && block.getBlockData().equals(getDataValue());
    }

    public Material getType()
    {
        return type;
    }

    public BlockData getDataValue()
    {
        return dataValue;
    }
}
