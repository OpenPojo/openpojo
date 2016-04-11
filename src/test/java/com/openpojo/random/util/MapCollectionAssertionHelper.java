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

package com.openpojo.random.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * @author oshoukry
 */
public class MapCollectionAssertionHelper {

  /*
  Input for expected type is an array representing the nested tree for the input structure.
      Map
          k: Map
              k: Map
                  k: Integer
                  v: String
              v: Map
                  k: Integer
                  v: String
          v: Map
              k: Map
                  k: Integer
                  v: String
              v: Map
                  k: Integer
                  v: String

      Map
          k: Map
          v: Map
              kk: Map
              kv: Map
              vk: Map
              vv: Map
                  kkk: Integer
                  kkv: String
                  kvk: Integer
                  kvv: String
                  vkk: Integer
                  vkv: String
                  vvk: Integer
                  vvv: String

   */
  public static void assertParametersStructure(Object input, Class<?>[] expectedTypes) {
    ensureBagHasTheSameClassType(input);
    assertStructure(input, new LinkedList<Class<?>>(Arrays.asList(expectedTypes)));
  }

  public static void assertStructure(Object input, LinkedList<Class<?>> queue) {
    assertTypeIsAssignable(input, queue.poll());

    if (isACollection(input)) {
      validateCollectionHasCorrectTypes((Collection) input, queue.poll());
    }

    if (isAMap(input)) {

      Map inputMap = (Map) input;
      Assert.assertThat(inputMap.size(), is(greaterThan(0)));
      validateMapKeysAndValuesAreCorrectTypes(queue, getAnEntry(inputMap));
    }
  }

  private static boolean isAMap(Object input) {
    return input instanceof Map;
  }

  private static boolean isACollection(Object input) {
    return input instanceof Collection;
  }

  private static void ensureBagHasTheSameClassType(Object bag) {
    if (isAMap(bag)) {
      ensureThatAllKeysAndValuesAreSameClassType((Map<?, ?>) bag);
    }
    if (isACollection(bag)) {
      ensureThatAllCollectionEntriesAreSameClassType((Collection) bag);
    }
  }

  private static void ensureThatAllKeysAndValuesAreSameClassType(Map<?, ?> inputMap) {
    Map.Entry referenceEntry = (Map.Entry) inputMap.entrySet().toArray()[0];
    for (Map.Entry entry : inputMap.entrySet()) {
      Assert.assertEquals(entry.getKey().getClass(), referenceEntry.getKey().getClass());
      Assert.assertEquals(entry.getValue().getClass(), referenceEntry.getValue().getClass());
      ensureBagHasTheSameClassType(entry.getKey());
      ensureBagHasTheSameClassType(entry.getValue());
    }
  }

  private static void ensureThatAllCollectionEntriesAreSameClassType(Collection collection) {
    if (collection instanceof SynchronousQueue) // This can't have anything in it.
      return;
    Object referenceEntry = collection.iterator().next();
    for (Object entry : collection) {
      Assert.assertEquals(referenceEntry.getClass(), entry.getClass());
      ensureBagHasTheSameClassType(entry);
    }
  }

  private static Map.Entry getAnEntry(Map map) {
    Map.Entry entry = (Map.Entry) map.entrySet().toArray()[0];
    map.remove(entry.getKey());
    return entry;
  }

  private static void validateMapKeysAndValuesAreCorrectTypes(LinkedList<Class<?>> queue, Map.Entry entry) {
    assertStructure(entry.getKey(), queue);
    assertStructure(entry.getValue(), queue);
  }

  private static void validateCollectionHasCorrectTypes(Collection input, Class<?> listItemType) {
    for (Object entry : input) {
      assertTypeIsAssignable(entry, listItemType);
    }
  }

  private static void assertTypeIsAssignable(Object input, Class<?> type) {
    Assert.assertTrue("Type [" + type + " cannot be assigned to input [" + input.getClass() + "]", type.isAssignableFrom(input
        .getClass()));
  }
}
