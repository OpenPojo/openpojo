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

package com.openpojo.cache.impl;

import java.util.Map;
import java.util.WeakHashMap;

import com.openpojo.cache.CacheStorage;

/**
 * This simple implementation of CacheStorage uses WeakHashMap as the underlying storage mechanism and only well suited
 * for temporal short bursts of cache are needed and GC removal of cached items is acceptable.
 *
 * @author oshoukry
 *
 */
public class WeakHashMapCacheStorage<T> implements CacheStorage<T> {

    private final Map<String, T> repository = new WeakHashMap<String, T>();

    public void clear() {
        repository.clear();
    }

    public void add(String name, T value) {
        // Ensure that we don't have a "Strong" reference to the key in the map, otherwise no cleanup will occur.
        repository.put(new String(name), value);
    }

    public T get(String name) {
        return repository.get(name);
    }

}
