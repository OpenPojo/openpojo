/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.cache;

/**
 * This Interface defines the CacheStorage contract.
 *
 * @author oshoukry
 */
public interface CacheStorage<T> {

    /**
     * Add an item to the cache.
     *
     * @param name
     *            The lookup key.
     * @param value
     *            The value to be cached.
     */
    void add(String name, T value);

    /**
     * Get an item from the cache.
     *
     * @param name
     *            The lookup key.
     * @return returns the cached value, or null if not found.
     */
    T get(String name);

    /**
     * The method clears the cache.
     */
    void clear();
}
