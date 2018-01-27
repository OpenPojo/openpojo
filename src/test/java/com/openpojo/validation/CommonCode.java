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

package com.openpojo.validation;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public final class CommonCode {

  public static void shouldPassRuleValidation(final Rule rule, final Class<?>... passClasses) {
    for (Class<?> clazz : passClasses) {
      rule.evaluate(PojoClassFactory.getPojoClass(clazz));
    }
  }

  public static void shouldPassTesterValidation(final Tester tester, final Class<?>... passClasses) {
    for (Class<?> clazz : passClasses) {
      tester.run(PojoClassFactory.getPojoClass(clazz));
    }
  }

  public static void shouldFailRuleValidation(final Rule rule, final Class<?>... failClasses) {
    for (Class<?> clazz : failClasses) {
      try {
        rule.evaluate(PojoClassFactory.getPojoClass(clazz));
        Affirm.fail(String.format("Rule = [%s] failed to detect error while evaluating class= [%s]", rule, clazz));
      } catch (AssertionError ae) {
        if (ae.getMessage().contains("Rule = [")) {
          throw ae;
        }
      }
    }
  }

  public static void shouldFailTesterValidation(final Tester tester, final Class<?>... failClasses) {
    for (Class<?> clazz : failClasses) {
      try {
        tester.run(PojoClassFactory.getPojoClass(clazz));
        Affirm.fail(String.format("Tester = [%s] failed to detect error while evaluating class= [%s]", tester, clazz));
      } catch (AssertionError ae) {
        if (ae.getMessage().contains("Tester = [")) {
          throw ae;
        }
      }
    }
  }
}
