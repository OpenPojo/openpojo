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

package com.openpojo.reflection;


import java.lang.reflect.Type;
import java.util.List;

/**
 * This class encapsulates the meta data definition of a method on a class.
 *
 * @author oshoukry
 */
public interface PojoMethod extends PojoElement, Accessible {
  /**
   * Invokes the underlying method represented by this Method object, on the specified object with the specified
   * parameters.
   *
   * Individual parameters are automatically unwrapped to match primitive formal parameters, and both primitive
   * and reference parameters are subject to method invocation conversions as necessary.
   *
   * If the underlying method is static, then the specified instance argument is ignored. It may be null.
   * If the number of formal parameters required by the underlying method is 0,
   * the supplied parameters array may be of length 0 or null.
   *
   * If the method completes normally, the value it returns is returned to the caller of invoke;
   * if the value has a primitive type, it is first appropriately wrapped in an object.
   * However, if the value has the type of an array of a primitive type, the elements of the array are not
   * wrapped in objects; in other words, an array of primitive type is returned.
   *
   * If the underlying method return type is void, the invocation returns null.
   *
   * note: If an exception occurs, it will be thrown as ReflectionException.
   *
   * @param instance
   *     The class instance to invoke the method on.
   * @param parameters
   *     The parameters to pass to the method upon invocation.
   * @return Value returned by the underlying method.
   */
  Object invoke(final Object instance, final Object... parameters);


  /**
   * Get method parameters as PojoParameters.
   * If method doesn't have any parameters an empty list is returned.
   *
   * @return a List of PojoParameters.
   */
  List<PojoParameter> getPojoParameters();

  /**
   * Get the method parameters.
   *
   * @return An array of parameterTypes.
   */
  Class<?>[] getParameterTypes();

  /**
   * Get the method generic parameter types
   * Note: Do not use, still in experimental mode.
   *
   * @return An array of generic parameter types.
   */
  Type[] getGenericParameterTypes();

  /**
   * @return True if this PojoMethod is final-ly defined on the enclosing class.
   */
  boolean isFinal();

  /**
   * @return true if this PojoMethod is synthetic (i.e. jdk compiler generated).
   */
  boolean isSynthetic();

  /**
   * @return True if this PojoMethod is static-ly defined on the enclosing class.
   */
  boolean isStatic();

  /**
   * @return True if this method is a constructor method and returns a new instance.
   */
  boolean isConstructor();

  /**
   * @return True if this method is abstract-ly defined on the enclosing class.
   */
  boolean isAbstract();

  /**
   * @return Returns the return type.
   */
  Class<?> getReturnType();
}
