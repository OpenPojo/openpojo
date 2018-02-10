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
 * This rule ensures that no fields declared with public visibility unless they
 * are static and final It is best to non-POJO classes (Interfaces, Enums,
 * ...etc.) to hold those instead of mixing responsibilities.
 *
 * @author oshoukry
 */
public final class NoPublicFieldsExceptStaticFinalRule implements Rule {
  public void evaluate(final PojoClass pojoClass) {
    for (PojoField fieldEntry : pojoClass.getPojoFields()) {
      if (fieldEntry.isPublic() && !ValidationHelper.isStaticFinal(fieldEntry)) {
        Affirm.fail(String.format("Non 'static final' Public fields=[%s] not allowed", fieldEntry));
      }
    }
  }

}
