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

package com.openpojo.validation.rule.impl;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.NoPublicFieldsExceptStaticFinalDoesClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoPublicFieldsExceptStaticFinalDoesntClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoPublicFieldsExceptStaticFinalDoesntPublicFinalClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoPublicFieldsExceptStaticFinalDoesntPublicStaticClass;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class NoPublicFieldsExceptStaticFinalRuleTest {
  Class<?>[] failClasses = new Class<?>[] {
      NoPublicFieldsExceptStaticFinalDoesntClass.class,
      NoPublicFieldsExceptStaticFinalDoesntPublicStaticClass.class,
      NoPublicFieldsExceptStaticFinalDoesntPublicFinalClass.class };

  Class<?>[] passClasses = new Class<?>[] { NoPublicFieldsExceptStaticFinalDoesClass.class };
  Rule rule = new NoPublicFieldsExceptStaticFinalRule();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassRuleValidation(rule, passClasses);
    CommonCode.shouldFailRuleValidation(rule, failClasses);
  }

}
