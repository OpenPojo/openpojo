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

import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class CacheStorageFactoryTest {
    @Test
    public void shouldReturnTemporalCache() {
        CacheStorage<String> keyValuePairCache = CacheStorageFactory.getTemporalCacheStorage();
        String expectedKey = "SomeKey";
        String expectedValue = "SomeValue";
        keyValuePairCache.add(expectedKey, expectedValue);
        Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
        System.gc();
        Assert.assertNull(keyValuePairCache.get(expectedKey));
    }

    @Test
    public void shouldReturnPersistentCache() {
        CacheStorage<String> keyValuePairCache = CacheStorageFactory.getPersistentCacheStorage();
        String expectedKey = "SomeKey";
        String expectedValue = "SomeValue";
        keyValuePairCache.add(expectedKey, expectedValue);
        Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
        System.gc();
        Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
    }
}
