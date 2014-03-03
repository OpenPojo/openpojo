/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.validation.rule.impl;

import org.junit.Test;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.NoPrimitivesDoesClass;
import com.openpojo.validation.rule.impl.sampleclasses.NoPrimitivesDoesntClass;

/**
 * @author oshoukry
 *
 */
public class NoPrimitivesRuleTest {

    Class<?>[] failClasses = new Class<?>[]{ NoPrimitivesDoesntClass.class };
    Class<?>[] passClasses = new Class<?>[]{ NoPrimitivesDoesClass.class };
    Rule rule = new NoPrimitivesRule();

    @Test
    public void testEvaluate() {
        CommonCode.shouldPassRuleValidation(rule, passClasses);
        CommonCode.shouldFailRuleValidation(rule, failClasses);
    }

}
