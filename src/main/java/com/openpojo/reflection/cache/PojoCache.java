/*
 * Copyright (c) 2010-2018 Osman Shoukry
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
  private static CacheStorage<PojoClass> pojoClassCache = CacheStorageFactory.getTemporalCacheStorage();

  /**
   * Retrieve an implementation from Cache.
   *
   * @param name
   *     Fully Qualified Class Name.
   * @return Cached PojoReference, or null if none found.
   */
  public static PojoClass getPojoClass(final String name) {
    return pojoClassCache.get(name);
  }

  /**
   * Add a PojoClass definition to the Cache.
   *
   * @param name
   *     Fully Qualified Class Name.
   * @param pojoClass
   *     The entry to add to the cache.
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

  private PojoCache() {
    throw new UnsupportedOperationException(PojoCache.class.getName() + " should not be constructed!");
  }
}
