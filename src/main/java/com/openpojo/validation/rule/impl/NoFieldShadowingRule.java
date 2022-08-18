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

package com.openpojo.validation.rule.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

//@formatter:off
/**
 * This rule ensures that no subclass shadows fields defined in a parent class.
 *
 *{@code
 *  For example:
 *   Public class A {
 *     private String myString;
 *   }
 *
 *   Public class B extends A {
 *     private String myString;
 *   }
 *}
 *
 * @author oshoukry
 *
 */
//@formatter:on
public class NoFieldShadowingRule implements Rule {

  private static final String SERIAL_VERSION_UID_FIELD_NAME = "serialVersionUID";
  private static final Class<?> SERIAL_VERSION_UID_FIELD_TYPE = long.class;
  private List<String> fieldNamesToSkip;

  public NoFieldShadowingRule(String... fieldNamesToSkip) {
    this.fieldNamesToSkip = Arrays.asList(fieldNamesToSkip);
  }

  public void evaluate(final PojoClass pojoClass) {
    final List<PojoField> parentPojoFields = new LinkedList<PojoField>();
    PojoClass parentPojoClass = pojoClass.getSuperClass();
    while (parentPojoClass != null) {
      parentPojoFields.addAll(parentPojoClass.getPojoFields());
      parentPojoClass = parentPojoClass.getSuperClass();
    }

    final List<PojoField> childPojoFields = pojoClass.getPojoFields();
    for (final PojoField childPojoField : childPojoFields) {
      if (!childPojoField.isSynthetic() && !isSerializable(childPojoField, pojoClass) && !inSkipList(childPojoField))
        if (contains(childPojoField.getName(), parentPojoFields))
          Affirm.fail(MessageFormatter.format("Field=[{0}] shadows field with the same name in parent class=[{1}]",
              childPojoField, parentPojoFields));
    }

  }

  private boolean inSkipList(PojoField field) {
    for (String skipEntry : fieldNamesToSkip)
      if (skipEntry.equals(field.getName()))
        return true;

    return false;
  }

  private boolean isSerializable(PojoField field, PojoClass pojoClass) {
    return field.getName().equals(SERIAL_VERSION_UID_FIELD_NAME)
        && Serializable.class.isAssignableFrom(pojoClass.getClazz())
        && field.getType().equals(SERIAL_VERSION_UID_FIELD_TYPE);
  }

  private boolean contains(final String fieldName, final List<PojoField> pojoFields) {
    for (final PojoField pojoField : pojoFields) {
      if (pojoField.getName().equals(fieldName)) {
        return true;
      }
    }
    return false;
  }

}
