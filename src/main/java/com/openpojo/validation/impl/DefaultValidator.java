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

package com.openpojo.validation.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * @author oshoukry
 */
public class DefaultValidator implements Validator {
  private final List<Rule> rules = new LinkedList<Rule>();
  private final List<Tester> testers = new LinkedList<Tester>();

  public DefaultValidator(List<Rule> rules, List<Tester> testers) {
    this.rules.addAll(rules);
    this.testers.addAll(testers);
  }

  public void validate(PojoClass pojoClass) {
    ValidationHelper.runValidation(pojoClass, this.rules, this.testers);
  }

  public void validate(List<PojoClass> pojoClasses) {
    for (PojoClass pojoClass : pojoClasses)
      validate(pojoClass);
  }

  public List<PojoClass> validate(String packageName, PojoClassFilter... filters) {
    PojoClassFilter pojoClassFilter = new FilterChain(filters);
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(packageName, pojoClassFilter);
    validate(pojoClasses);
    return pojoClasses;
  }

  public List<PojoClass> validateRecursively(String packageName, PojoClassFilter... filters) {
    PojoClassFilter pojoClassFilter = new FilterChain(filters);
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively(packageName, pojoClassFilter);
    validate(pojoClasses);
    return pojoClasses;
  }
}
