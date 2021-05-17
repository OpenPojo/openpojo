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

/**
 * This rule ensures that there are no static fields unless they are final.
 * Another best practice, using static fields for memory sharing and allowing read/write
 * should be very tightly controlled, and generally don't belong in POJOs or other similar
 * class of data repositories.
 *
 * @author oshoukry
 */
public class NoStaticExceptFinalRule implements Rule {

  public void evaluate(final PojoClass pojoClass) {
    for (PojoField fieldEntry : pojoClass.getPojoFields()) {
      if (fieldEntry.isStatic() && !fieldEntry.isFinal() && !fieldEntry.isSynthetic()) {
        Affirm.fail(String.format("Static fields=[%s] not marked final are not allowed", fieldEntry));
      }
    }
  }
}
