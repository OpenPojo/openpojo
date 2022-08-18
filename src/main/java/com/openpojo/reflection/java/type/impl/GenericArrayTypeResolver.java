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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import com.openpojo.reflection.java.type.TypeResolver;

/**
 * This resolver is used for GenericArrayType, which is returned when the underlying declaration is parameterized with an
 * array.  For example, if you have Set of SomeType[].  In Java 7 and 8, this is no longer the case.
 * TODO: Remove when JDK 1.5 and 1.6 are no longer supported.
 *
 * @author oshoukry
 */
public class GenericArrayTypeResolver implements TypeResolver<GenericArrayType> {

  public Type resolveType(GenericArrayType type) {
    Type returnedType = type.getGenericComponentType();
    return getArrayClassOfType(returnedType);
  }

  /**
   * There is no clean way of generating a "Class" in java in runtime that represents an Array.
   * The only way is to _create_ an array then return the type of that instance.
   */
  private Type getArrayClassOfType(Type returnedType) {
    return Array.newInstance((Class) returnedType, 0).getClass();
  }

  public Type getEnclosingType(GenericArrayType type) {
    throw new UnsupportedOperationException(
        "getEnclosingType(" + type + ")"
            + " - This operation is Not Supported, if you ran into this please report this issue @ http://openpojo.com");
  }

  public Type[] getParameterTypes(GenericArrayType type) {
    throw new UnsupportedOperationException(
        "getParameterTypes(" + type + ")"
            + " - This operation is Not Supported, if you ran into this please report this issue @ http://openpojo.com");
  }
}
