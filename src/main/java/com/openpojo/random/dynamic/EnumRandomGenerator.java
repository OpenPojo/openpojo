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

package com.openpojo.random.dynamic;

import java.util.Date;
import java.util.Random;

import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This RandomGenerator handles retrieval of random Enum values from an enum.
 *
 * @author oshoukry
 */
public final class EnumRandomGenerator {
  private static final Random RANDOM = new Random(new Date().getTime());

  private EnumRandomGenerator() {
  }

  public static EnumRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Object doGenerate(final Class<?> type) {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(type);

    final Enum<?>[] values = getValues(pojoClass);
    if (values.length == 0)
      throw RandomGeneratorException.getInstance("Can't generate random value for Enum class [" + type + "] enum doesn't " +
          "define any values");
    return values[RANDOM.nextInt(values.length)];
  }

  private Enum<?>[] getValues(final PojoClass enumPojoClass) {
    Enum<?>[] values = null;
    for (final PojoMethod pojoMethod : enumPojoClass.getPojoMethods()) {
      if (pojoMethod.getName().equals("values") && pojoMethod.getPojoParameters().size() == 0) {
        values = (Enum<?>[]) pojoMethod.invoke(null, (Object[]) null);
        break;
      }
    }
    return values;
  }

  private static class Instance {
    private static final EnumRandomGenerator INSTANCE = new EnumRandomGenerator();
  }
}
