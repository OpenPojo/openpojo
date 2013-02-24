/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public class PojoValidator {
    private final Logger logger = LoggerFactory.getLogger(PojoValidator.class);
    private final List<Rule> rules = new LinkedList<Rule>();
    private final List<Tester> testers = new LinkedList<Tester>();

    /**
     * Add Rule to use for validation.
     *
     * @param rule
     *            The rule to Add.
     */
    public void addRule(final Rule rule) {
        rules.add(rule);
    }

    /**
     * Add Tester to use for validation.
     *
     * @param tester
     *            The Tester to Add.
     */
    public void addTester(final Tester tester) {

        testers.add(tester);
    }

    /**
     * Run the validation, this will invoke all the rule.evaluate as well as tester.run.
     *
     * @param pojoClass
     *            The PojoClass to validate.
     */
    public void runValidation(final PojoClass pojoClass) {
        for (final Rule rule : rules) {
            rule.evaluate(pojoClass);
        }

        if (!pojoClass.isConcrete()) {
            logger.warn("Attempt to execute behavioural test on non-concrete class=[{0}] ignored,"
                    + " consider using FilterNonConcrete class when calling PojoClassFactory", pojoClass);
        } else {
            for (final Tester tester : testers) {
                tester.run(pojoClass);
            }
        }
    }

}
