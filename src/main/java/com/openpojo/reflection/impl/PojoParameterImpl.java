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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.java.type.Resolver;

/**
 * @author oshoukry
 */
public class PojoParameterImpl implements PojoParameter {

  private final Type type;
  private final List<? extends Annotation> annotations;

  public PojoParameterImpl(Type type, Annotation[] annotations) {
    this.type = type;
    List<Annotation> tmpAnnotations = new ArrayList<Annotation>();
    if (annotations != null) {
      for (Annotation entry : annotations) {
        if (entry != null)
          tmpAnnotations.add(entry);
      }
    }
    this.annotations = Collections.unmodifiableList(tmpAnnotations);
  }

  public List<? extends Annotation> getAnnotations() {
    return annotations;
  }

  @SuppressWarnings("unchecked")
  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {

    for (Annotation entry : annotations) {
      if (entry.annotationType().equals(annotationClass)) {
        return (T) entry;
      }
    }
    return null;
  }

  public Class<?> getType() {
    return (Class<?>) Resolver.getEnclosingType(type);
  }

  public boolean isParameterized() {
    return (type instanceof ParameterizedType);
  }

  public List<Type> getParameterTypes() {
    return Arrays.asList(Resolver.getParameterTypes(type));
  }

  @Override
  public String toString() {
    return String.format("%s [@%s: Type=%s, Annotations=%s]", this.getClass().getName(), this.hashCode(), type, annotations);
  }
}
