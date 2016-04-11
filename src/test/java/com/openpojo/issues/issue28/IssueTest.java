/*
 * Copyright (c) 2010-2016 Osman Shoukry
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

package com.openpojo.issues.issue28;

import com.openpojo.issues.issue28.sample.AChildOfAnotherChildClass;
import com.openpojo.issues.issue28.sample.AnotherChildClass;
import com.openpojo.issues.issue28.sample.ChildClass;
import com.openpojo.issues.issue28.sample.ParentClass;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {
  private Validator pojoValidator;

  @Before
  public void setup() {
    pojoValidator = ValidatorBuilder.create().with(new NoFieldShadowingRule()).build();
  }

  @Test(expected = AssertionError.class)
  public void shouldFailBecauseShadowingParentField() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(ChildClass.class /* ParentClass.class is parent */);
    pojoValidator.validate(pojoClass);
  }

  @Test(expected = AssertionError.class)
  public void shouldFailBecauseShadowingParentsParentField() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(AChildOfAnotherChildClass.class /* AnotherChildClass.class
    is parent */);
    pojoValidator.validate(pojoClass);
  }

  @Test
  public void shouldPassNoShadowing() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(AnotherChildClass.class /* Object is parent */);
    pojoValidator.validate(pojoClass);
  }

  @Test
  public void shouldPassBecauseOfDefaultObjectParent() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(ParentClass.class /* Object is parent */);
    pojoValidator.validate(pojoClass);
  }

  @Test
  public void shouldPassBecauseNoParentDefined() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(Object.class /* No Parent */);
    pojoValidator.validate(pojoClass);
  }

}
