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

import com.openpojo.cache.impl.StrongRefHashMapCacheStorage;
import com.openpojo.cache.impl.WeakHashMapCacheStorage;

/**
 * This factory creates a CacheStorage for a given category.
 *
 * @author oshoukry
 */
public class CacheStorageFactory {

    /**
     * Returns an instance of CacheStorage that will garbage collect automatically.
     *
     * @param <T>
     *          The value type used for caching.
     * @return
     *          returns an instance of TemporalCacheStorage.
     */
    public static <T> CacheStorage<T> getTemporalCacheStorage() {
        return new WeakHashMapCacheStorage<T>();
    }

    /**
     * Returns an instance of CacheStorage that will not garbage collect automatically.
     *
     * @param <T>
     *          The value type used for caching.
     * @return
     *          returns an instance of PersistentCacheStorage.
     */
    public static <T> CacheStorage<T> getPersistentCacheStorage() {
        return new StrongRefHashMapCacheStorage<T>();
    }
}
