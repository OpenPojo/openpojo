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

package com.openpojo.reflection.java.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import com.openpojo.reflection.java.type.impl.GenericArrayTypeResolver;
import com.openpojo.reflection.java.type.impl.NoResolveTypeResolver;
import com.openpojo.reflection.java.type.impl.ParameterizedTypeResolver;
import com.openpojo.reflection.java.type.impl.TypeVariableResolver;
import com.openpojo.reflection.java.type.impl.WildcardTypeResolver;

/**
 * @author oshoukry
 */
public class Resolver {
  private static final WildcardTypeResolver WILDCARD_TYPE_RESOLVER = new WildcardTypeResolver();
  private static final ParameterizedTypeResolver PARAMETERIZED_TYPE_RESOLVER = new ParameterizedTypeResolver();
  private static final TypeVariableResolver TYPE_VARIABLE_RESOLVER = new TypeVariableResolver();
  private static final GenericArrayTypeResolver GENERIC_ARRAY_TYPE_RESOLVER = new GenericArrayTypeResolver();
  private static final NoResolveTypeResolver NO_RESOLVE_TYPE_RESOLVER = new NoResolveTypeResolver();

  @SuppressWarnings("unchecked")
  public static Type resolve(Type type) {
    return getTypeResolver(type).resolveType(type);
  }

  @SuppressWarnings("unchecked")
  public static Type getEnclosingType(Type type) {
    return getTypeResolver(type).getEnclosingType(type);
  }

  @SuppressWarnings("unchecked")
  public static Type[] getParameterTypes(Type type) {
    return getTypeResolver(type).getParameterTypes(type);
  }

  private static <T> TypeResolver getTypeResolver(T type) {
    if (type instanceof WildcardType)
      return WILDCARD_TYPE_RESOLVER;
    if (type instanceof ParameterizedType)
      return PARAMETERIZED_TYPE_RESOLVER;
    if (type instanceof TypeVariable)
      return TYPE_VARIABLE_RESOLVER;
    if (type instanceof GenericArrayType)
      return GENERIC_ARRAY_TYPE_RESOLVER;
    return NO_RESOLVE_TYPE_RESOLVER;
  }

  private Resolver() {
    throw new UnsupportedOperationException(Resolver.class.getName() +  " should not be constructed!");
  }
}
