/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
