/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection.construct;

import java.util.Arrays;
import java.util.List;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.construct.utils.ArrayLengthBasedComparator;
import com.openpojo.reflection.construct.utils.GreaterThan;
import com.openpojo.reflection.construct.utils.LessThan;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.bytecode.ByteCodeFactory;

/**
 * This Factory has the ability to create an instance of any PojoClass.
 * This Factory can create a Pojo using exact argument list, or to randomly create an instance
 * using ANY available constructor.
 *
 * @author oshoukry
 */
public class InstanceFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(InstanceFactory.class);

  private InstanceFactory() {
    throw new RuntimeException("Should not be constructed");
  }

  /**
   * This method returns a new instance created using default constructor.
   * It is identical to calling getInstance(PojoClass, null).
   *
   * @param pojoClass
   *     The PojoClass to instantiate.
   * @return a newly created instance of the class represented in the pojoClass.
   */
  public static Object getInstance(final PojoClass pojoClass) {
    return InstanceFactory.getInstance(pojoClass, (Object[]) null);
  }

  /**
   * This method returns a new instance created using the parameters given.
   * If parameters array is null or not null but is of length zero, then the getInstance will call
   * the no arg constructor.
   * If you want to pass null to a single/multiple parameter constructor, then create an array of the correct size
   * matching the parameters.
   *
   * @param pojoClass
   *     The pojoClass to instantiate.
   * @param parameters
   *     The parameters to pass to the constructor.
   * @return a newly created instance of the class represented in the pojoClass.
   */
  public static Object getInstance(final PojoClass pojoClass, final Object... parameters) {
    if (pojoClass.isAbstract())
      return doGetInstance(wrapAbstractClass(pojoClass), parameters);
    return doGetInstance(pojoClass, parameters);
  }

  private static PojoClass wrapAbstractClass(final PojoClass pojoClass) {
    return PojoClassFactory.getPojoClass(ByteCodeFactory.getSubClass(pojoClass.getClazz()));
  }

  private static Object doGetInstance(PojoClass pojoClass, Object[] parameters) {
    if (!pojoClass.isConcrete()) {
      throw ReflectionException.getInstance(String.format("[%s] is not a concrete class, can't create new instance", pojoClass));
    }

    final List<PojoMethod> constructors = pojoClass.getPojoConstructors();
    for (final PojoMethod constructor : constructors) {
      if (areEquivalentParameters(upCast(constructor.getParameterTypes()), getTypes(parameters))) {
        return constructor.invoke(null, parameters);
      }
    }
    throw ReflectionException.getInstance(String.format("No matching constructor for [%s] found using parameters[%s]",
        pojoClass.getClazz(), Arrays.toString(getTypes(parameters))));
  }

  /**
   * This method will upCast Native parameters to their equivalent Class-es.
   *
   * @param parameterTypes
   *     The array of parameters needed to be upCast.
   * @return An upcasted array of parameters (i.e. short -> Short, int -> Int, String doesn't change).
   */
  private static Class<?>[] upCast(final Class<?>[] parameterTypes) {
    final Class<?>[] upCastedParameters = new Class[parameterTypes.length];
    for (int idx = 0; idx < parameterTypes.length; idx++) {
      upCastedParameters[idx] = parameterTypes[idx];
      if (parameterTypes[idx].isPrimitive()) {
        if (parameterTypes[idx] == boolean.class) {
          upCastedParameters[idx] = Boolean.class;
        }
        if (parameterTypes[idx] == int.class) {
          upCastedParameters[idx] = Integer.class;
        }
        if (parameterTypes[idx] == float.class) {
          upCastedParameters[idx] = Float.class;
        }
        if (parameterTypes[idx] == double.class) {
          upCastedParameters[idx] = Double.class;
        }
        if (parameterTypes[idx] == long.class) {
          upCastedParameters[idx] = Long.class;
        }
        if (parameterTypes[idx] == short.class) {
          upCastedParameters[idx] = Short.class;
        }
        if (parameterTypes[idx] == byte.class) {
          upCastedParameters[idx] = Byte.class;
        }
        if (parameterTypes[idx] == char.class) {
          upCastedParameters[idx] = Character.class;
        }
      }
    }
    return upCastedParameters;
  }

  /**
   * This method returns a new instance using the constructor with the most parameters.
   * The values for the constructor will come from RandomFactory.
   *
   * @param pojoClass
   *     The pojoClass to instantiate.
   * @return a newly created instance of the class represented in the pojoClass.
   */
  public static Object getMostCompleteInstance(final PojoClass pojoClass) {
    final PojoMethod constructor = getConstructorByCriteria(pojoClass, new GreaterThan());
    return createInstance(pojoClass, constructor);
  }

  /**
   * This method returns a new instance using the constructor with the least parameters.
   * The values for the constructor will come from RandomFactory.
   *
   * @param pojoClass
   *     The pojoClass to instantiate.
   * @return a newly created instance of the class represented in the pojoClass.
   */
  public static Object getLeastCompleteInstance(final PojoClass pojoClass) {
    final PojoMethod constructor = getConstructorByCriteria(pojoClass, new LessThan());
    return createInstance(pojoClass, constructor);
  }

  /**
   * This method evaluates if the parameters are equivalent / compatible.
   * Null values in the givenTypes are treated as compatible.
   *
   * @param expectedTypes
   *     The expected types.
   * @param givenTypes
   *     The given types to compare with.
   * @return True if the given can be used as argument list for expected.
   */
  private static boolean areEquivalentParameters(final Class<?>[] expectedTypes, final Class<?>[] givenTypes) {
    if (expectedTypes.length == 0) {
      return givenTypes.length == 0;
    }

    if (givenTypes.length != expectedTypes.length) {
      return false;
    }

    for (int idx = 0; idx < expectedTypes.length; idx++) {
      if (!isAssignableFrom(expectedTypes[idx], givenTypes[idx])) {
        return false;
      }
    }
    return true;
  }

  private static boolean isAssignableFrom(final Class<?> expectedType, final Class<?> givenType) {
    return givenType == null || expectedType.isAssignableFrom(givenType);
  }

  private static Class<?>[] getTypes(final Object... parameters) {

    if (parameters == null || parameters.length == 0) {
      return new Class<?>[0];
    }

    final Class<?>[] types = new Class<?>[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i] != null) {
        types[i] = parameters[i].getClass();
      }
    }
    return types;
  }

  private static Object createInstance(final PojoClass pojoClass, final PojoMethod constructor) {

    try {
      List<PojoParameter> pojoParameterTypes = constructor.getPojoParameters();

      final Object[] parameters = new Object[pojoParameterTypes.size()];

      for (int i = 0; i < pojoParameterTypes.size(); i++) {
        parameters[i] = RandomFactory.getRandomValue(pojoParameterTypes.get(i));
      }

      return getInstance(pojoClass, parameters);
    } catch (RuntimeException re) {
      throw ReflectionException.getInstance("Failed to create instance for class [" + pojoClass + "] using constructor [" +
          constructor + "]", re);
    }
  }

  private static PojoMethod getConstructorByCriteria(final PojoClass pojoClass, final ArrayLengthBasedComparator comparator) {
    PojoMethod constructor = null;
    for (final PojoMethod pojoConstructor : pojoClass.getPojoConstructors()) {
      if (!pojoConstructor.isSynthetic())
        if (constructor == null)
          constructor = pojoConstructor;
        else {
          if (comparator.compare(pojoConstructor.getParameterTypes(), constructor.getParameterTypes()))
            constructor = pojoConstructor;
        }
    }
    return constructor;
  }
}
