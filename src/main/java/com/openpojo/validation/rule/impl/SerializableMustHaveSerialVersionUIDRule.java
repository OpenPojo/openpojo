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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

/**
 * This Rule ensures that all Serializable classes define "serialVersionUID".
 *
 * @author oshoukry
 */
public class SerializableMustHaveSerialVersionUIDRule implements Rule {
  private static final String SERIAL_VERSION_UID = "serialVersionUID";

  public void evaluate(final PojoClass pojoClass) {
    if (pojoClass.extendz(Serializable.class) && !pojoClass.isInterface()) {
      for (PojoField pojoField : pojoClass.getPojoFields()) {
        if (pojoField.getName().equalsIgnoreCase(SERIAL_VERSION_UID)) {
          if (!pojoField.getName().equals(SERIAL_VERSION_UID)) {
            Affirm.affirmEquals(String.format("Case miss-match on serialVersionUID field on Serializable class [%s]", pojoClass),
                SERIAL_VERSION_UID, pojoField.getName());
          }
          if (!(pojoField.isStatic() && pojoField.isFinal())) {
            Affirm.fail(String.format("[%s] must be defined as [static final] on Serializable class [%s]",
                SERIAL_VERSION_UID, pojoClass));
          }
          if (pojoField.getType() != long.class) {
            Affirm.fail(String.format("[%s] must be defined as [long] on Serializable class [%s]",
                SERIAL_VERSION_UID, pojoClass));
          }
          return;
        }
      }
      Affirm.fail(String.format("No [%s] field defined on Serializable class [%s]", SERIAL_VERSION_UID, pojoClass));
    }
  }
}
