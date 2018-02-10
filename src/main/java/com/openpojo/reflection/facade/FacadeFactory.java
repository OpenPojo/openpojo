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

package com.openpojo.reflection.facade;

import java.util.Arrays;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * This factory is responsible for looking up usable implementations of facade when multiple are possible.
 *
 * @author oshoukry
 */
public final class FacadeFactory {
  /**
   * This method returns the proper loaded facade PojoClass.
   * This method will throw a runtime ReflectionException if non of the facades given are active.
   *
   * @param facadeNames
   *     The fully qualified class names of the possible facades.
   * @return A PojoClass wrapper around the correctly identified ClassName.
   */
  public static PojoClass getLoadedFacadePojoClass(final String[] facadeNames) {
    return PojoClassFactory.getPojoClass(getLoadedFacadeClass(facadeNames));
  }

  public static Class<?> getLoadedFacadeClass(final String[] facadeNames) {
    for (String facadeName : facadeNames) {
      Class clazz = ClassUtil.loadClass(facadeName);
      if (clazz != null)
        return clazz;
    }
    throw ReflectionException.getInstance(String.format("Unable to find suitable implementation among [%s]", Arrays.toString
        (facadeNames)));
  }

  private FacadeFactory() {
    throw new UnsupportedOperationException(FacadeFactory.class.getName() +  " should not be constructed!");
  }
}
