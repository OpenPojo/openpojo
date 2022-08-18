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

package com.openpojo.business.cache;

import java.util.List;

import com.openpojo.cache.CacheStorage;
import com.openpojo.cache.CacheStorageFactory;

/**
 * This is the Cache to hold references for BusinessPojoFields.
 *
 * @author oshoukry
 */
public class BusinessKeyFieldCache {
  private CacheStorage<List<BusinessKeyField>> cache = CacheStorageFactory.getPersistentCacheStorage();

  /**
   * Retrieve a BusinessFields list from Cache.
   *
   * @param name
   *     The cache tag to use for cache lookup.
   * @return Cached PojoReference, or null if none found.
   */
  public List<BusinessKeyField> get(final String name) {
    return cache.get(name);
  }

  /**
   * Add a BusinessFields definition to the Cache.
   *
   * @param name
   *     A tag for to use for cache lookup
   * @param businessFields
   *     The list of businessFields to cache
   */
  public void add(final String name, final List<BusinessKeyField> businessFields) {
    cache.add(name, businessFields);
  }
}
