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

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassImpl;

/**
 * @author oshoukry
 */
public class CoberturaPojoClassAdapter implements PojoClassAdapter {

  private static final String COBERTURA_INJECTED = "__cobertura_";

  private CoberturaPojoClassAdapter() {

  }

  public PojoClass adapt(PojoClass pojoClass) {
    final List<PojoField> cleansedPojoFields = new ArrayList<PojoField>();
    for (final PojoField pojoField : pojoClass.getPojoFields()) {
      if (!pojoField.getName().startsWith(COBERTURA_INJECTED)) {
        cleansedPojoFields.add(pojoField);
      }
    }
    final List<PojoMethod> cleansedPojoMethods = new ArrayList<PojoMethod>();
    for (final PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (!pojoMethod.getName().startsWith(COBERTURA_INJECTED)) {
        cleansedPojoMethods.add(pojoMethod);
      }
    }
    return new PojoClassImpl(pojoClass.getClazz(), cleansedPojoFields, cleansedPojoMethods);
  }

  public static PojoClassAdapter getInstance() {
    return Instance.INSTANCE;
  }

  private static class Instance {
    private static final PojoClassAdapter INSTANCE = new CoberturaPojoClassAdapter();
  }

}
