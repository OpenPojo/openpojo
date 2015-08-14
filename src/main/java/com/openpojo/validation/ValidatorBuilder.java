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
            for (Tester tester: testers) {
                if (tester != null)
                    this.testers.add(tester);
            }
        return this;
    }

    public Validator build() {
        if (rules.size() == 0 && testers.size() == 0)
            throw ValidationException.getInstance("You must add at least 1 Rule or Tester before building Validator");
        return new DefaultValidator(rules, testers);
    }

    public List<Tester> getTesters() {
        return testers;
    }
}
