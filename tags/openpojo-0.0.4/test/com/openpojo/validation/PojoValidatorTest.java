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

import junit.framework.Assert;

import org.junit.Test;

import utils.validation.LoggingRule;
import utils.validation.LoggingTester;

import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is a logging tester used for testing.
 * 
 * @author oshoukry
 */
public class PojoValidatorTest {

    @Test
    public void testRunValidation() {
        PojoValidator pojoValidator = new PojoValidator();

        LoggingRule loggingRule = new LoggingRule();
        LoggingTester loggingTester = new LoggingTester();

        pojoValidator.addRule(loggingRule);
        pojoValidator.addTester(loggingTester);

        Assert.assertEquals(0, loggingRule.getLogs().size());
        Assert.assertEquals(0, loggingTester.getLogs().size());

        pojoValidator.runValidation(PojoClassFactory.getPojoClass(PojoValidatorTest.class));

        Assert.assertEquals(1, loggingRule.getLogs().size());
        Assert.assertEquals(1, loggingTester.getLogs().size());
    }
}
