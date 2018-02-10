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

package com.openpojo.random.collection.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.ParameterizableFactory;

/**
 * This Helper class populates the randomly generated collection with some random elements.<br>
 * It is configured to generate anywhere between 0 - 10 elements in the collection.
 *
 * @author oshoukry
 */
public class CollectionHelper {

  private static final Random RANDOM = new Random(new Date().getTime());
  public static final int MAX_RANDOM_ELEMENTS = 5;

  @SuppressWarnings("unchecked")
  public static Collection buildCollections(Collection collection, Type type) {
    if (type == null || collection == null)
      return collection;

    int counter = RANDOM.nextInt(MAX_RANDOM_ELEMENTS) + 1;

    collection.clear();
    while (counter-- > 0) {
      Object nextEntry = RandomFactory.getRandomValue(ParameterizableFactory.getInstance(type));
      collection.add(nextEntry);
    }
    return collection;
  }

  private CollectionHelper() {
    throw new UnsupportedOperationException(CollectionHelper.class.getName() + " should not be constructed!");
  }
}