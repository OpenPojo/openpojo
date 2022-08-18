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

/**
 * This Interface defines the CacheStorage contract.
 *
 * @author oshoukry
 */
public interface CacheStorage<T> {

  /**
   * Add an item to the cache.
   *
   * @param name
   *     The lookup key.
   * @param value
   *     The value to be cached.
   */
  void add(String name, T value);

  /**
   * Get an item from the cache.
   *
   * @param name
   *     The lookup key.
   * @return returns the cached value, or null if not found.
   */
  T get(String name);

  /**
   * The method clears the cache.
   */
  void clear();
}
