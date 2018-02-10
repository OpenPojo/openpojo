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
   *     The value type used for caching.
   * @return returns an instance of TemporalCacheStorage.
   */
  public static <T> CacheStorage<T> getTemporalCacheStorage() {
    return new WeakHashMapCacheStorage<T>();
  }

  /**
   * Returns an instance of CacheStorage that will not garbage collect automatically.
   *
   * @param <T>
   *     The value type used for caching.
   * @return returns an instance of PersistentCacheStorage.
   */
  public static <T> CacheStorage<T> getPersistentCacheStorage() {
    return new StrongRefHashMapCacheStorage<T>();
  }

  private CacheStorageFactory() {
    throw new UnsupportedOperationException(CacheStorageFactory.class.getName() + " should not be constructed!");
  }
}
