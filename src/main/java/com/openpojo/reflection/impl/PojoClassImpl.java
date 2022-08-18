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
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.packageloader.impl.URLToFileSystemAdapter;
import com.openpojo.reflection.utils.ToStringHelper;

/**
 * This class is the default implementation for the PojoClass Interface, created through the PojoClassFactory.
 *
 * @author oshoukry
 */
public class PojoClassImpl implements PojoClass {

  private final String name;
  private final Class<?> clazz;
  private final List<PojoField> pojoFields;
  private final List<PojoMethod> pojoMethods;

  public PojoClassImpl(final Class<?> clazz, final List<PojoField> pojoFields, final List<PojoMethod> pojoMethods) {
    this.clazz = clazz;
    this.name = clazz.getName();
    this.pojoFields = Collections.unmodifiableList(pojoFields);
    this.pojoMethods = Collections.unmodifiableList(pojoMethods);
  }

  public boolean isInterface() {
    return clazz.isInterface();
  }

  public boolean isAbstract() {
    // Java returns true on Abstract call for Interfaces.
    return Modifier.isAbstract(clazz.getModifiers()) && !isInterface();
  }

  public boolean isConcrete() {
    return !(isAbstract() || isInterface() || isEnum());
  }

  public boolean isEnum() {
    return clazz.isEnum();
  }

  public boolean isArray() {
    return clazz.isArray();
  }

  public boolean isFinal() {
    return Modifier.isFinal(clazz.getModifiers());
  }

  public boolean isSynthetic() {
    return clazz.isSynthetic();
  }

  public List<PojoField> getPojoFields() {
    return pojoFields;
  }

  public List<PojoField> getPojoFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    List<PojoField> returnedFields = new LinkedList<PojoField>();
    for (PojoField pojoField : pojoFields) {
      if (pojoField.getAnnotation(annotation) != null)
        returnedFields.add(pojoField);
    }
    return returnedFields;
  }

  public List<PojoMethod> getPojoMethods() {
    return pojoMethods;
  }

  public List<PojoMethod> getPojoMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
    List<PojoMethod> returnedMethods = new LinkedList<PojoMethod>();
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getAnnotation(annotation) != null)
        returnedMethods.add(pojoMethod);
    }
    return returnedMethods;
  }

  public List<PojoMethod> getPojoConstructors() {
    final List<PojoMethod> constructors = new LinkedList<PojoMethod>();
    for (final PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.isConstructor()) {
        constructors.add(pojoMethod);
      }
    }
    return constructors;
  }

  public PojoClass getEnclosingClass() {
    if (isNestedClass())
      return PojoClassFactory.getPojoClass(clazz.getEnclosingClass());

    return null;
  }

  public String getName() {
    return name;
  }

  public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    return clazz.getAnnotation(annotationClass);
  }

  public List<? extends Annotation> getAnnotations() {
    return Arrays.asList(clazz.getAnnotations());
  }

  public boolean extendz(final Class<?> type) {
    return type.isAssignableFrom(clazz);
  }

  public boolean isNestedClass() {
    return clazz.getEnclosingClass() != null;
  }

  public boolean isStatic() {
    return Modifier.isStatic(clazz.getModifiers());
  }

  public void copy(final Object from, final Object to) {
    for (final PojoField pojoField : pojoFields) {
      if (pojoField.hasGetter() && pojoField.hasSetter()) {
        final Object value = pojoField.invokeGetter(from);
        pojoField.invokeSetter(to, value);
      }
    }
  }

  public Class<?> getClazz() {
    return clazz;
  }

  @Override
  public String toString() {
    return String.format(this.getClass().getName() + " [clazz=%s, pojoFields=%s, pojoMethods=%s]", clazz, pojoFields,
        pojoMethods);
  }

  public String toString(final Object instance) {
    return ToStringHelper.pojoClassToString(this, instance);
  }

  public PojoClass getSuperClass() {
    if (clazz.getSuperclass() != null) {
      return PojoClassFactory.getPojoClass(clazz.getSuperclass());
    }
    return null;
  }

  public List<PojoClass> getInterfaces() {
    final List<PojoClass> interfaces = new LinkedList<PojoClass>();
    for (final Class<?> interfaze : clazz.getInterfaces()) {
      interfaces.add(PojoClassFactory.getPojoClass(interfaze));
    }
    return interfaces;
  }

  public PojoPackage getPackage() {
    return PojoPackageFactory.getPojoPackage(clazz.getPackage().getName());
  }

  public String getSourcePath() {
    try {
      final ClassLoader cl = this.getClazz().getClassLoader();
      final URL location = cl.getResource(getClazz().getName().replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER)
          + Java.CLASS_EXTENSION);
      return new URLToFileSystemAdapter(location).getAsURI().toString();
    } catch (final Exception e) {
      throw ReflectionException.getInstance(e.getMessage(), e);
    }
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null || !other.getClass().equals(this.getClass())) {
      return false;
    }
    PojoClassImpl otherPojoClass = (PojoClassImpl) other;
    return name.equals(otherPojoClass.name);
  }

  public boolean isPrivate() {
    return Modifier.isPrivate(clazz.getModifiers());
  }

  public boolean isPackagePrivate() {
    int modifiers = clazz.getModifiers();

    return (Modifier.PUBLIC & modifiers
        | Modifier.PROTECTED & modifiers
        | Modifier.PRIVATE & modifiers) == 0;
  }

  public boolean isProtected() {
    return Modifier.isProtected(clazz.getModifiers());
  }

  public boolean isPublic() {
    return Modifier.isPublic(clazz.getModifiers());
  }
}
