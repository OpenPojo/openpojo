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

package com.openpojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class StructuralTest {

    @Test
    public void allTestsMustEndWithTest() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively("com.openpojo", null);
        Set<PojoClass> testThatVoilateEndsWithTest = new HashSet<PojoClass>();
        for (PojoClass pojoClass : pojoClasses) {
            if (!pojoClass.getName().endsWith("Test")) {
                if (hasTestAnnotationOrParentHasTestAnnotation(pojoClass)) testThatVoilateEndsWithTest.add(pojoClass);
            }
        }
        String nonCompliantClasses = "\r\n";
        for (PojoClass pojoClass : testThatVoilateEndsWithTest) {
            nonCompliantClasses += "\t" + pojoClass.getName() + "\r\n";
        }
        Assert.assertEquals("Every test class must end with Test" + nonCompliantClasses, 0, testThatVoilateEndsWithTest.size());
    }

    private boolean hasTestAnnotationOrParentHasTestAnnotation(PojoClass pojoClass) {
        if (pojoClass == null) return false;

        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (pojoMethod.getAnnotation(Test.class) != null) return true;
        }

        return (hasTestAnnotationOrParentHasTestAnnotation(pojoClass.getSuperClass()));
    }
}
