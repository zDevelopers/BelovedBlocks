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

package eu.carrade.amaury.BelovedBlocks.utils;

import java.util.*;


public class Utils
{

	/**
	 * Constructs a new Set (HashSet technically) with the given content inside.
	 *
	 * @param content The content of the Set.
	 * @param <T> The content's type.
	 *
	 * @return A new {@link HashSet<T>} with the given content inside.
	 */
	@SafeVarargs
	public static <T> Set<T> set(T... content)
	{
		Set<T> set = new HashSet<>();
		Collections.addAll(set, content);

		return set;
	}

}
