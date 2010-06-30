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
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDDoesClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDDoesntClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDInvalidCaseClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotFinalClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotPrimitiveClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotSerializableClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotStaticClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotlongClass;

/**
 * @author oshoukry
 */
public class SerializableMustHaveSerialVersionUIDRuleTest {
    Class<?>[] failClasses = new Class<?>[]{ SerializableMustHaveSerialVersionUIDDoesntClass.class,
            SerializableMustHaveSerialVersionUIDInvalidCaseClass.class,
            SerializableMustHaveSerialVersionUIDNotFinalClass.class,
            SerializableMustHaveSerialVersionUIDNotStaticClass.class,
            SerializableMustHaveSerialVersionUIDNotPrimitiveClass.class,
            SerializableMustHaveSerialVersionUIDNotlongClass.class };
    Class<?>[] passClasses = new Class<?>[]{ SerializableMustHaveSerialVersionUIDDoesClass.class,
            SerializableMustHaveSerialVersionUIDNotSerializableClass.class };
    Rule rule = new SerializableMustHaveSerialVersionUIDRule();

    /**
     * Test method for
     * {@link com.openpojo.validation.rule.impl.SerializableMustHaveSerialVersionUIDRule#evaluate(com.openpojo.reflection.PojoClass)}
     * .
     */
    @Test
    public void testEvaluate() {
        CommonCode.shouldPassRuleValidation(rule, passClasses);
        CommonCode.shouldFailRuleValidation(rule, failClasses);
    }

}
