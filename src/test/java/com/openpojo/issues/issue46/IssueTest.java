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

package com.openpojo.issues.issue46;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {
  private List<PojoClass> pojoClasses;
  private Validator pojoValidator;

  @Before
  public void setup() {
    pojoClasses = PojoClassFactory.getPojoClassesRecursively("com.openpojo.issues.issue46.sample", null);
    pojoValidator = ValidatorBuilder.create().with(new DefaultValuesNullTester()).build();
  }

  @Test
  public void getAllClasses() {
    pojoValidator.validate(pojoClasses);
  }
}
