/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.reflection.cache;

import com.openpojo.cache.CacheStorage;
import com.openpojo.cache.CacheStorageFactory;
import com.openpojo.reflection.PojoClass;

/**
 * This is the Cache to hold references for PojoClasses, to prevent looking them up over and over.
 *
 * @author oshoukry
 */
public class PojoCache {
    private static CacheStorage<PojoClass> pojoClassCache = CacheStorageFactory.getCacheStorage(PojoCache.class
            .getName());

    /**
     * Retrieve an implementation from Cache.
     *
     * @param name
     *         Fully Qualified Class Name.
     * @return Cached PojoReference, or null if none found.
     */
    public static PojoClass getPojoClass(final String name) {
        return pojoClassCache.get(name);
    }

    /**
     * Add a PojoClass definition to the Cache.
     *
     * @param name
     *         Fully Qualified Class Name.
     * @param pojoClass
     *         The entry to add to the cache.
     */
    public static void addPojoClass(final String name, final PojoClass pojoClass) {
        pojoClassCache.add(name, pojoClass);
    }

    /**
     * This method will clear the cache, which is only needed when testing.
     * Note: Calling this under a heavy loads can have negatively impact performance.
     */
    public static void clear() {
        pojoClassCache.clear();
    }
}
