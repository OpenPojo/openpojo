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

package com.openpojo.validation;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * @author oshoukry
 * @deprecated This class is deprecated, please use {@link ValidatorBuilder} instead.
 */
public class PojoValidator {
  private final List<Rule> rules = new LinkedList<Rule>();
  private final List<Tester> testers = new LinkedList<Tester>();

  public PojoValidator() {
    DeprecationWarning.getInstance();
  }

  /**
   * Add Rule to use for validation.
   *
   * @param rule
   *     The rule to Add.
   */
  public void addRule(final Rule rule) {
    rules.add(rule);
  }

  /**
   * Add Tester to use for validation.
   *
   * @param tester
   *     The Tester to Add.
   */
  public void addTester(final Tester tester) {
    testers.add(tester);
  }

  /**
   * Run the validation, this will invoke all the rule.evaluate as well as tester.run.
   *
   * @param pojoClass
   *     The PojoClass to validate.
   */
  public void runValidation(final PojoClass pojoClass) {
    ValidationHelper.runValidation(pojoClass, rules, testers);
  }


  private static class DeprecationWarning {
    private static final DeprecationWarning INSTANCE = new DeprecationWarning();

    private DeprecationWarning() {
      Logger logger = LoggerFactory.getLogger(PojoValidator.class);
      logger.warn("This class is deprecated, please use " + ValidatorBuilder.class.getName() + " instead.");
    }

    private static DeprecationWarning getInstance() {
      return INSTANCE;
    }
  }
}
