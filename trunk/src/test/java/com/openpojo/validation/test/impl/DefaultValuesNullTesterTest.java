/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.validation.test.impl;

import org.junit.Test;

import com.openpojo.validation.rule.impl.CommonCode;
import com.openpojo.validation.rule.impl.sampleclasses.DefaultValueNotNullClass;
import com.openpojo.validation.rule.impl.sampleclasses.DefaultValueNullClass;
import com.openpojo.validation.test.Tester;

/**
 * @author oshoukry
 */
public class DefaultValuesNullTesterTest {

    Class<?>[] failClasses = new Class<?>[]{ DefaultValueNotNullClass.class };
    Class<?>[] passClasses = new Class<?>[]{ DefaultValueNullClass.class };
    Tester test = new DefaultValuesNullTester();

    @Test
    public void testEvaluate() {
        CommonCode.shouldPassTesterValidation(test, passClasses);
        CommonCode.shouldFailTesterValidation(test, failClasses);
    }
}
