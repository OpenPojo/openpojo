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

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.exception.ValidationException;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public class SimpleValidator {
    private PojoValidator pojoValidator;

    public SimpleValidator() {
        this(new PojoValidator());
    }

    public SimpleValidator(PojoValidator pojoValidator) {
        this.pojoValidator = pojoValidator;
    }

    public SimpleValidator with(Rule... rules) {
        failIfNull("Rule(s) can not be null", rules);

        for (Rule rule : rules)
            pojoValidator.addRule(rule);

        return this;
    }

    public SimpleValidator with(Tester... testers) {
        failIfNull("Tester(s) can not be null", testers);

        for (Tester tester : testers)
            pojoValidator.addTester(tester);

        return this;
    }

    private void failIfNull(String message, Object... objects) {
        if (objects == null)
            throw new IllegalArgumentException(message);

        for (Object o : objects)
            if (o == null)
                throw new IllegalArgumentException(message);
    }

    public void validate(List<PojoClass> pojoClasses) {

        if (pojoClasses == null)
            throw ValidationException.getInstance("pojoClasses can't be null");

        if (pojoClasses.size() == 0) {
            throw ValidationException.getInstance("Empty list of pojoClasses submitted for validation, nothing to do!!");
        }

        for (PojoClass pojoClass : pojoClasses)
            pojoValidator.runValidation(pojoClass);
    }
}
