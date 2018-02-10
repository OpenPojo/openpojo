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

package com.openpojo.reflection.adapt.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassImpl;

/**
 * This Adapter will strip out the fields and methods that related to jacoco.
 *
 * @author oshoukry
 */
public class JacocoPojoClassAdapter implements PojoClassAdapter {
  private static final String JACOCO_FIELD_NAME = "$jacocoData";
  private static final String JACOCO_METHOD_NAME = "$jacocoInit";

  private JacocoPojoClassAdapter() {
  }

  public static PojoClassAdapter getInstance() {
    return Instance.INSTANCE;
  }

  public PojoClass adapt(final PojoClass pojoClass) {
    final List<PojoField> cleansedPojoFields = new LinkedList<PojoField>();
    for (final PojoField pojoField : pojoClass.getPojoFields()) {
      if (!pojoField.getName().equals(JACOCO_FIELD_NAME)) {
        cleansedPojoFields.add(pojoField);
      }
    }
    final List<PojoMethod> cleansedPojoMethods = new LinkedList<PojoMethod>();
    for (final PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (!pojoMethod.getName().equals(JACOCO_METHOD_NAME)) {
        cleansedPojoMethods.add(pojoMethod);
      }
    }
    return new PojoClassImpl(pojoClass.getClazz(), cleansedPojoFields, cleansedPojoMethods);
  }

  private static class Instance {
    private static final PojoClassAdapter INSTANCE = new JacocoPojoClassAdapter();
  }
}
