/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

import com.openpojo.cache.CacheStorage;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public abstract class CacheStorageTest {
    private CacheStorage<String> cache;

    @Before
    public void setup() {
        cache = getCacheStorage();
    }

    @Test
    public void canAddAndGetItem() {
        String key = "key";
        Assert.assertNull(cache.get(key));
        String value = "value";

        cache.add(key, value);
        Assert.assertEquals(value, cache.get(key));
    }

    @Test
    public void canClear() {
        String key = "key";
        String value = "value";
        cache.add(key, value);
        Assert.assertEquals(value, cache.get(key));
        cache.clear();
        Assert.assertNull(cache.get(key));
    }

    public abstract CacheStorage<String> getCacheStorage();
}
