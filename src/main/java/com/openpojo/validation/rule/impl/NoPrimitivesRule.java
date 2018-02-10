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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This Rule ensures that you aren't using any primitive type fields.
 * This is a best practice, ideally, you want to know the difference between "set/unset" and value.
 *
 * @author oshoukry
 */
public class NoPrimitivesRule implements Rule {

  public void evaluate(final PojoClass pojoClass) {
    for (PojoField fieldEntry : pojoClass.getPojoFields()) {
      if (fieldEntry.isPrimitive() && !ValidationHelper.isStaticFinal(fieldEntry)) {
        Affirm.fail(String.format(
            "Primitive fields (byte, short, int, long, float, double, boolean, char) not allowed [%s]", fieldEntry));
      }
    }
  }

}
