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

package com.openpojo.validation.test.impl;

import com.openpojo.validation.CommonCode;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.ToStringTester_DispatchingToBusinessIdentity;
import com.openpojo.validation.test.impl.sampleclasses.ToStringTester_NotDispatchingToBusinessIdentity;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ToStringTesterTest {
  private Class<?>[] passClasses = new Class<?>[] { ToStringTester_DispatchingToBusinessIdentity.class };
  private Class<?>[] failClasses = new Class<?>[] { ToStringTester_NotDispatchingToBusinessIdentity.class };
  private Tester test = new ToStringTester();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassTesterValidation(test, passClasses);
    CommonCode.shouldFailTesterValidation(test, failClasses);
  }

}
