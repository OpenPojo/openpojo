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

package com.openpojo.reflection.java.type.impl;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.type.TypeResolver;

/**
 * @author oshoukry
 */
public class WildcardTypeResolver implements TypeResolver<WildcardType> {

  public Type getEnclosingType(WildcardType type) {
    return resolveType(type);
  }

  public Type resolveType(WildcardType wildcardType) {

    Type[] lowerBounds = wildcardType.getLowerBounds();
    Type[] upperBounds = wildcardType.getUpperBounds();

    ensureAValidBoundaryExists(lowerBounds, upperBounds);

    return getParameterTypes(wildcardType)[0];
  }

  public Type[] getParameterTypes(WildcardType wildcardType) {
    Type[] bounds = wildcardType.getLowerBounds();
    if (bounds == null || bounds.length == 0)
      return wildcardType.getUpperBounds();
    return bounds;
  }

  private void ensureAValidBoundaryExists(Type[] lowerBounds, Type[] upperBounds) {
    if (lowerBounds.length > 1 || upperBounds.length > 1 || (upperBounds.length == 0 && lowerBounds.length == 0))
      throw ReflectionException.getInstance("Unable to identify proper resolution for type, " +
          "multiple UpperBounds[" + Arrays.toString(upperBounds) + "] Or LowerBounds[" + Arrays.toString(lowerBounds) + "]");
  }

}
