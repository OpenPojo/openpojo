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

import java.util.ArrayList;
import java.util.List;

import com.openpojo.validation.exception.ValidationException;
import com.openpojo.validation.impl.DefaultValidator;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public class ValidatorBuilder {

  private List<Rule> rules = new ArrayList<Rule>();
  private List<Tester> testers = new ArrayList<Tester>();

  private ValidatorBuilder() {
  }

  public static ValidatorBuilder create() {
    return new ValidatorBuilder();
  }

  public ValidatorBuilder with(Rule... rules) {
    if (rules != null)
      for (Rule rule : rules) {
        if (rule != null)
          this.rules.add(rule);
      }
    return this;
  }

  public List<Rule> getRules() {
    return rules;
  }

  public ValidatorBuilder with(Tester... testers) {
    if (testers != null)
      for (Tester tester : testers) {
        if (tester != null)
          this.testers.add(tester);
      }
    return this;
  }

  public List<Tester> getTesters() {
    return testers;
  }

  public Validator build() {
    if (rules.size() == 0 && testers.size() == 0)
      throw ValidationException.getInstance("You must add at least 1 Rule or Tester before building Validator");
    return new DefaultValidator(rules, testers);
  }

}
