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

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 */
public class PojoMethodImpl implements PojoMethod {
  private final AccessibleObject accessibleObject;

  PojoMethodImpl(final Method method) {
    this((AccessibleObject) method);
  }

  PojoMethodImpl(final Constructor<?> constructor) {
    this((AccessibleObject) constructor);
  }

  private PojoMethodImpl(final AccessibleObject accessibleObject) {
    this.accessibleObject = accessibleObject;
  }

  private void allowAccessibility() {
    accessibleObject.setAccessible(true);
  }

  public String getName() {
    if (isConstructor()) {
      return getAsConstructor().getName();
    }
    return getAsMethod().getName();
  }

  public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    return accessibleObject.getAnnotation(annotationClass);
  }

  public List<? extends Annotation> getAnnotations() {
    return Arrays.asList(accessibleObject.getAnnotations());
  }

  public Object invoke(final Object instance, final Object... parameters) {
    allowAccessibility();
    if (isConstructor()) {
      try {
        return getAsConstructor().newInstance(parameters);
      } catch (final IllegalArgumentException e) {
        throw ReflectionException.getInstance(e.getMessage(), e);
      } catch (final InstantiationException e) {
        throw ReflectionException.getInstance(e.getMessage(), e);
      } catch (final IllegalAccessException e) {
        throw ReflectionException.getInstance(e.getMessage(), e);
      } catch (final InvocationTargetException e) {
        throw ReflectionException.getInstance(e.getMessage(), e);
      }
    }

    try {
      return getAsMethod().invoke(instance, parameters);
    } catch (final IllegalArgumentException e) {
      throw ReflectionException.getInstance(e.getMessage(), e);
    } catch (final IllegalAccessException e) {
      throw ReflectionException.getInstance(e.getMessage(), e);
    } catch (final InvocationTargetException e) {
      throw ReflectionException.getInstance(e.getMessage(), e);
    }
  }

  public List<PojoParameter> getPojoParameters() {
    List<PojoParameter> parameters = new ArrayList<PojoParameter>();

    Annotation[][] parameterAnnotations;
    Type[] parameterTypes;
    Class<?>[] parameterClasses;

    if (isConstructor()) {
      parameterAnnotations = getAsConstructor().getParameterAnnotations();
      parameterClasses = getAsConstructor().getParameterTypes();
      parameterTypes = getConstructorGenericParameterTypes(getAsConstructor(), parameterClasses);
    } else {
      parameterAnnotations = getAsMethod().getParameterAnnotations();
      parameterTypes = getAsMethod().getGenericParameterTypes();
      parameterClasses = getAsMethod().getParameterTypes();
    }

    // If parameters aren't of Generic type, then default their types to the class params.
    if (parameterTypes.length == 0)
      parameterTypes = parameterClasses;

    for (int idx = 0; idx < parameterClasses.length; idx++) {
      parameters.add(PojoParameterFactory.getPojoParameter(parameterTypes[idx], parameterAnnotations[idx]));
    }

    return parameters;
  }

  public boolean isFinal() {
    return Modifier.isFinal(getModifiers());
  }

  public boolean isPrivate() {
    return Modifier.isPrivate(getModifiers());
  }

  public boolean isPackagePrivate() {
    int modifiers = getModifiers();
    return (Modifier.PRIVATE & modifiers
        | Modifier.PROTECTED & modifiers
        | Modifier.PUBLIC & modifiers) == 0;
  }

  public boolean isProtected() {
    return Modifier.isProtected(getModifiers());
  }

  public boolean isPublic() {
    return Modifier.isPublic(getModifiers());
  }

  public boolean isStatic() {
    return Modifier.isStatic(getModifiers());
  }

  public boolean isSynthetic() {
    if (isConstructor()) {
      return getAsConstructor().isSynthetic();
    }
    return getAsMethod().isSynthetic();
  }

  public boolean isConstructor() {
    return accessibleObject instanceof Constructor<?>;
  }

  public boolean isAbstract() {
    if (isConstructor()) {
      return Modifier.isAbstract(getAsConstructor().getModifiers()); // should never return true
    }

    return Modifier.isAbstract(getAsMethod().getModifiers());
  }

  public Type[] getGenericParameterTypes() {
    if (isConstructor()) {
      return getAsConstructor().getGenericParameterTypes();
    }
    return getAsMethod().getGenericParameterTypes();
  }

  public Class<?>[] getParameterTypes() {
    if (isConstructor()) {
      return getAsConstructor().getParameterTypes();
    }
    return getAsMethod().getParameterTypes();
  }

  private Type[] getConstructorGenericParameterTypes(Constructor<?> asConstructor, Class<?>[] parameterClasses) {
    Type[] genericParameterTypes = asConstructor.getGenericParameterTypes();

    Class<?> declaringClass = asConstructor.getDeclaringClass();
    genericParameterTypes = bug_5087240_workaround(declaringClass, genericParameterTypes, parameterClasses);

    return genericParameterTypes;
  }

  /**
   * See: http://bugs.java.com/view_bug.do?bug_id=5087240
   */
  private Type[] bug_5087240_workaround(Class<?> clazz, Type[] genericParameterTypes,
                                        Class<?>[] parameterClasses) {
    Type[] fixedGenericParameterType = genericParameterTypes;

    if (isNestedNonStaticClass(clazz) && missingSyntheticParameter(genericParameterTypes, parameterClasses)) {
      fixedGenericParameterType = new Type[parameterClasses.length];
      fixedGenericParameterType[0] = parameterClasses[0];
      System.arraycopy(genericParameterTypes, 0, fixedGenericParameterType, 1, genericParameterTypes.length);
    }

    return fixedGenericParameterType;
  }

  private boolean missingSyntheticParameter(Type[] genericParameterTypes, Class<?>[] parameterClasses) {
    return (genericParameterTypes.length + 1) == parameterClasses.length;
  }

  private boolean isNestedNonStaticClass(Class<?> clazz) {
    return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
  }

  public Class<?> getReturnType() {
    if (isConstructor()) {
      return getAsConstructor().getDeclaringClass();
    }
    return getAsMethod().getReturnType();
  }

  private Method getAsMethod() {
    return (Method) accessibleObject;
  }

  private int getModifiers() {
    if (isConstructor()) {
      return getAsConstructor().getModifiers();
    }
    return getAsMethod().getModifiers();
  }

  private Constructor<?> getAsConstructor() {
    return (Constructor<?>) accessibleObject;
  }

  @Override
  public String toString() {
    final String tag = isConstructor() ? "constructor" : "method";
    return String.format("PojoMethodImpl [%s=%s args=%s return=%s]", tag, getName(),
        Arrays.toString(getParameterTypes()), getReturnType());
  }
}
