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

package com.openpojo.issues.issue56;

import com.openpojo.issues.issue56.sample.TestClass;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class TestCase {
    @Test
    public void testSetGenerator() {
        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addTester(new SetterTester());

        PojoClass pojoClass = PojoClassFactory.getPojoClass(TestClass.class);

        pojoValidator.runValidation(pojoClass);
    }
}
