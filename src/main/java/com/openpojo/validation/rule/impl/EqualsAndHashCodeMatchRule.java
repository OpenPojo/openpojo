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
import com.openpojo.reflection.PojoMethod;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

/**
 * This rule ensures that equals and hashCode must either both be present or absent.
 * i.e. Never implement one without the other.
 *
 * @author oshoukry
 */
@SuppressWarnings("WeakerAccess")
public class EqualsAndHashCodeMatchRule implements Rule {

  public void evaluate(PojoClass pojoClass) {

    boolean hasEquals = hasEquals(pojoClass);
    boolean hasHashCode = hasHashCode(pojoClass);

    if (hasEquals && !hasHashCode)
      Affirm.fail("equals implemented but hashcode isn't in Pojo [" + pojoClass + "]");

    if (!hasEquals && hasHashCode)
      Affirm.fail("hashCode implemented but equals isn't in Pojo [" + pojoClass + "]");
  }

  private boolean hasHashCode(PojoClass pojoClass) {
    for (PojoMethod method : pojoClass.getPojoMethods())
      if (method.getName().equals("hashCode") && method.getPojoParameters().size() == 0)
        return true;
    return false;
  }

  private boolean hasEquals(PojoClass pojoClass) {
    for (PojoMethod method : pojoClass.getPojoMethods())
      if (method.getName().equals("equals") && method.getPojoParameters().size() == 1)
        return true;
    return false;
  }

}
