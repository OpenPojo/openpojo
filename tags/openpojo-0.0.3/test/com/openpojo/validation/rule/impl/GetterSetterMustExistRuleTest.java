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
package com.openpojo.validation.rule.impl;

import org.junit.Test;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.GetterSetterDoesExistClass;
import com.openpojo.validation.rule.impl.sampleclasses.GetterSetterDoesntExistClass;

/**
 * @author oshoukry
 *
 */
@SuppressWarnings("deprecation")
public class GetterSetterMustExistRuleTest {

    Class<?>[] failClasses = new Class<?>[]{ GetterSetterDoesntExistClass.class };
    Class<?>[] passClasses = new Class<?>[]{ GetterSetterDoesExistClass.class };
    Rule rule = new GetterSetterMustExistRule();

    @Test
    public void testEvaluate() {
        CommonCode.shouldPassRuleValidation(rule, passClasses);
        CommonCode.shouldFailRuleValidation(rule, failClasses);
    }

}
