/**
 * Copyright (C) 2011 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.cache;

import com.openpojo.cache.impl.WeakHashMapCacheStorage;

/**
 * This factory creates a CacheStorage for a given category.
 *
 * @author oshoukry
 */
public class CacheStorageFactory {
    /**
     * Returns an instance of CacheStorage for use.
     *
     * @param category
     *            The category to use for caching, its best to provide the fully qualified class name of the
     *            CacheStorage user to allow multiple implementations to coexist based one requester.
     * @return returns an instance of CacheStorage.
     */
    public static <T> CacheStorage<T> getCacheStorage(String category) {
        return new WeakHashMapCacheStorage<T>();
    }
}
