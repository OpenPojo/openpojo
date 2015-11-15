/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation.rule.impl;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public final class CommonCode {

  public static void shouldPassRuleValidation(final Rule rule, final Class<?>[] passClasses) {
    for (Class<?> clazz : passClasses) {
      rule.evaluate(PojoClassFactory.getPojoClass(clazz));
    }
  }

  public static void shouldPassTesterValidation(final Tester tester, final Class<?>[] passClasses) {
    for (Class<?> clazz : passClasses) {
      tester.run(PojoClassFactory.getPojoClass(clazz));
    }
  }

  public static void shouldFailRuleValidation(final Rule rule, final Class<?>[] failClasses) {
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

  public static void shouldFailTesterValidation(final Tester tester, final Class<?>[] failClasses) {
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
