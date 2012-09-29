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

package com.openpojo.validation.rule.impl;

import org.junit.Test;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.BusinessClassWithComposite;
import com.openpojo.validation.rule.impl.sampleclasses.BusinessClassWithNoRequired;
import com.openpojo.validation.rule.impl.sampleclasses.BusinessClassWithRequired;

/**
 * @author oshoukry
 *
 */
public class BusinessKeyMustExistRuleTest {

    Class<?>[] failClasses = new Class<?>[]{ BusinessClassWithNoRequired.class };
    Class<?>[] passClasses = new Class<?>[]{ BusinessClassWithRequired.class, BusinessClassWithComposite.class };
    Rule rule = new BusinessKeyMustExistRule();

    @Test
    public void testEvaluate() {
        CommonCode.shouldPassRuleValidation(rule, passClasses);
        CommonCode.shouldFailRuleValidation(rule, failClasses);
    }

}
