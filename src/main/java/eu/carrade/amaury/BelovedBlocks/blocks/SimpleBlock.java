/**
 * Plugin BelovedBlocks Copyright (C) 2014-2015 Amaury Carrade & Florian Cassayre <p/> This program
 * is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. <p/> This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. <p/> You should
 * have received a copy of the GNU General Public License along with this program.  If not, see
 * [http://www.gnu.org/licenses/].
 */

package eu.carrade.amaury.BelovedBlocks.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;


public class SimpleBlock
{
	private Material type;
	private byte dataValue;


	public SimpleBlock(Material type, byte dataValue)
	{
		this.type = type;
		this.dataValue = dataValue;
	}

	public SimpleBlock(Material type, int dataValue)
	{
		this(type, (byte) dataValue);
	}


	/**
	 * Updates the given block to be the same one as this block.
	 *
	 * @param block The block.
	 */
	public void updateBlock(Block block)
	{
		block.setType(getType());
		block.setData(getDataValue());
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
		return block.getType() == getType() && block.getData() == getDataValue();
	}


	public Material getType()
	{
		return type;
	}

	public void setType(Material type)
	{
		this.type = type;
	}

	public byte getDataValue()
	{
		return dataValue;
	}

	public void setDataValue(byte dataValue)
	{
		this.dataValue = dataValue;
	}
}
