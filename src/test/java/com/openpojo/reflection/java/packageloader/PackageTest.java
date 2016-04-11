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

package com.openpojo.reflection.java.packageloader;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.BusinessKeyMustExistRule;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PackageTest {

  @Test
  public final void testIsValid() {
    Package javaPackage = new Package(RandomFactory.getRandomValue(String.class));
    Affirm.affirmFalse("Invalid package evaluated to as valid?!", javaPackage.isValid());

    javaPackage = new Package(this.getClass().getPackage().getName());
    Affirm.affirmTrue("Valid package evaluated to as invalid?!", javaPackage.isValid());
  }

  @Test
  public final void packageShouldDispatchEqualsAndHashCodeToBusinessIdentity() {
    final Validator pojoValidator = ValidatorBuilder.create()
        .with(new BusinessIdentityTester())
        .with(new BusinessKeyMustExistRule())
        .build();

    pojoValidator.validate(PojoClassFactory.getPojoClass(Package.class));
  }

}
