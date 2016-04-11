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

package com.openpojo.issues.issue33;

import com.openpojo.issues.issue33.sample.AbstractClassRandomGenerator;
import com.openpojo.issues.issue33.sample.ClassAggregatingAbstractClass;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

  private Validator pojoValidator;
  private PojoClass pojoClass;

  @Before
  public void setUp() throws Exception {
    pojoClass = PojoClassFactory.getPojoClass(ClassAggregatingAbstractClass.class);
    RandomGeneratorService randomGeneratorService = ServiceRegistrar.getInstance().getRandomGeneratorService();
    randomGeneratorService.registerRandomGenerator(new AbstractClassRandomGenerator());
  }

  @Test
  public void testGetter() {
    pojoValidator = ValidatorBuilder.create().with(new GetterTester()).build();
    pojoValidator.validate(pojoClass);
  }

  @Test
  public void testSetter() {
    pojoValidator = ValidatorBuilder.create().with(new SetterTester()).build();
    pojoValidator.validate(pojoClass);
  }


}
