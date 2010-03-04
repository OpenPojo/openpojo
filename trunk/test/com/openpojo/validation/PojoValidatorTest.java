/**
 * Copyright (C) 2010 Osman Shoukry
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public class PojoValidatorTest {

    @Test
    public void testRunValidation() {
        PojoValidator pojoValidator = new PojoValidator();
        LoggingRule loggingRule = new LoggingRule();
        pojoValidator.addRule(loggingRule);

        LoggingTester loggingTester = new LoggingTester();
        pojoValidator.addTester(loggingTester);
        pojoValidator.runValidation(PojoClassFactory.getPojoClass(PojoValidatorTest.class));
        Assert.assertEquals(1, loggingRule.getLogs().size());
        Assert.assertEquals(1, loggingTester.getLogs().size());
    }

    class LoggingRule implements Rule {
        private List<PojoClass> callLogs = new LinkedList<PojoClass>();

        @Override
        public void evaluate(PojoClass pojoClass) {
            callLogs.add(pojoClass);
        }

        public List<PojoClass> getLogs() {
            return callLogs;
        }
    }

    class LoggingTester implements Tester {
        private List<PojoClass> callLogs = new LinkedList<PojoClass>();

        @Override
        public void run(PojoClass pojoClass) {
            callLogs.add(pojoClass);
        }

        public List<PojoClass> getLogs() {
            return callLogs;
        }

    }
}
