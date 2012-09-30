/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.business.cache;

import java.util.List;

import com.openpojo.cache.CacheStorage;
import com.openpojo.cache.CacheStorageFactory;
import com.openpojo.reflection.PojoField;

/**
 * This is the Cache to hold references for BusinessPojoFields.
 *
 * @author oshoukry
 */
public class BusinessFieldCache {
    private static CacheStorage<List<PojoField>> cache = CacheStorageFactory.getCacheStorage(BusinessFieldCache.class.getName());

    /**
     * Retrieve a BusinessFields list from Cache.
     *
     * @param name
     *            The cache tag to use for cache lookup.
     * @return Cached PojoReference, or null if none found.
     */
    public static List<PojoField> getBusinessFields(final String name) {
        return cache.get(name);
    }

    /**
     * Add a BusinessFields definition to the Cache.
     *
     * @param name
     *            A tag for to use for cache lookup
     * @param businessFields
     *            The list of businessFields to cache
     */
    public static void addBusinessFields(final String name, final List<PojoField> businessFields) {
        cache.add(name, businessFields);
    }

}
