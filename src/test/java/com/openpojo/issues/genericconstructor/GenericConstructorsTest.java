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

package com.openpojo.issues.genericconstructor;

import com.openpojo.issues.genericconstructor.sample.ClassWithGenericListIntegerConstructor;
import com.openpojo.issues.genericconstructor.sample.ClassWithGenericSetEnumConstructor;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 *
 */
public class GenericConstructorsTest {

    @Test
    public void shouldConstructClassWithGenericListIntegerConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithGenericListIntegerConstructor.class);
        ClassWithGenericListIntegerConstructor instance = (ClassWithGenericListIntegerConstructor) InstanceFactory.getMostCompleteInstance(pojoClass);
        Assert.assertNotNull(instance);
        Assert.assertNotNull(instance.getIntegers());
        Assert.assertTrue(instance.getIntegers().size() > 0);

        Assert.assertNotNull(instance.getMymap());
        Assert.assertNotNull(instance.getString());
    }

    @Test
    public void shouldConstructClassWithGenericSetEnumConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithGenericSetEnumConstructor.class);
        ClassWithGenericSetEnumConstructor instance = (ClassWithGenericSetEnumConstructor) InstanceFactory.getMostCompleteInstance(pojoClass);
        Assert.assertNotNull(instance);
        Assert.assertNotNull(instance.getDaysOfTheWeek());
    }

}
