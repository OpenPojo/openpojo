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

package com.openpojo.validation.test.impl;

import com.openpojo.validation.rule.impl.CommonCode;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoDispatchingHashCodeAndEquals;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingEquals;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingHashCode;
import org.junit.Test;

public class BusinessIdentityTesterTest {
  Class<?>[] failClasses = new Class<?>[] { ABusinessPojoNotDispatchingHashCode.class, ABusinessPojoNotDispatchingEquals.class };
  Class<?>[] passClasses = new Class<?>[] { ABusinessPojoDispatchingHashCodeAndEquals.class };
  Tester test = new BusinessIdentityTester();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassTesterValidation(test, passClasses);
    CommonCode.shouldFailTesterValidation(test, failClasses);
  }

}
