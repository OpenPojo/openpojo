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

package com.openpojo.random.array;

import java.lang.reflect.Array;

import com.openpojo.random.RandomFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class RandomFactoryArrayTest {

  @Test
  public void shouldCreateRandomArray() {
    final Class<?> type = anyArrayType();
    final Object firstInstance = RandomFactory.getRandomValue(type);
    Affirm.affirmNotNull("Failed to get random array", firstInstance);

    final Object secondInstance = RandomFactory.getRandomValue(type);

    if (Array.getLength(firstInstance) == Array.getLength(secondInstance)) {
      boolean equals = true;
      for (int idx = 0; idx < Array.getLength(firstInstance); idx++) {
        final Object firstInstanceElement = Array.get(firstInstance, idx);
        final Object secondInstanceElement = Array.get(secondInstance, idx);
        equals = equals && ((firstInstanceElement != null && firstInstanceElement.equals(secondInstanceElement)) ||
            (firstInstanceElement == null && secondInstanceElement == null));
      }
    }
  }

  private Class<?> anyArrayType() {
    return Integer[].class;
  }
}
