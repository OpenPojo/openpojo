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
package com.openpojo.reflection.impl;

import java.util.List;

import org.junit.Test;

import utils.dummypackage.Persistable;
import utils.dummypackage.Person;
import utils.filter.LoggingPojoClassFilter;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class PojoClassFactoryTest {
    private static final String DUMMY_PACKAGE = "utils.dummypackage";
    private static final Class<?>[] DUMMY_PACKAGE_CLASSES = new Class<?>[]{ Persistable.class, Person.class };

    /**
     * Test that the factory is able to create a pojoClass correctly mapping back to PojoClassFactoryTest.
     */
    @Test
    public void testGetPojoClass() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(this.getClass());
        Affirm.affirmNotNull(String.format("PojoClassFactory failed to create PojoClass for [%s]", this.getClass()), pojoClass);
        Affirm.affirmEquals(String.format("PojoClassFactory returned invalid meta-definition PojoClass for [%s]", this.getClass()), this.getClass(), pojoClass.getClazz());
    }

    /**
     * Test that the factory gets classes from a package correctly.
     */
    @Test
    public void testGetPojoClasses() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE);
        Affirm.affirmNotNull(String.format("PojoClassFactory returned null list while getting list for package=[%s]", DUMMY_PACKAGE), pojoClasses);
        Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", DUMMY_PACKAGE), 2, pojoClasses.size());
        for (Class<?> clazz : DUMMY_PACKAGE_CLASSES) {
            Affirm.affirmTrue(pojoClasses.contains(PojoClassFactory.getPojoClass(clazz)));
        }
    }

    /**
     * Test that the factory utilizes pojo filter.
     */
    @Test
    public void testGetFilteredPojoClasses() {
        LoggingPojoClassFilter loggingPojoClassFilter = new LoggingPojoClassFilter();
        // Set Filter to reject all
        loggingPojoClassFilter.setReturnValue(false);
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE, loggingPojoClassFilter);
        Affirm.affirmNotNull(String.format("PojoClassFactory returned null list while getting list for package=[%s] using filter=[%s] in filter out all mode!!", DUMMY_PACKAGE, loggingPojoClassFilter.getClass()), pojoClasses);
        Affirm.affirmEquals(String.format("PojoClassFactory returned non-empty list for package=[%s] using filter=[%s] in filter out all mode!!", DUMMY_PACKAGE, loggingPojoClassFilter.getClass()), 0, pojoClasses.size());

        Affirm.affirmEquals(String.format("Wrong number of times filter was triggered while in filter-out all mode!! Classes added/removed from package=[%s]?", DUMMY_PACKAGE), 2, loggingPojoClassFilter.getPojoClassCallLogs().size());

        for (PojoClass pojoClass : pojoClasses) {
            Affirm.affirmTrue(loggingPojoClassFilter.getPojoClassCallLogs().contains(pojoClass));
        }

        // Set Filter to allow all
        loggingPojoClassFilter.setReturnValue(true);
        pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE, loggingPojoClassFilter);
        Affirm.affirmNotNull(String.format("PojoClassFactory returned null for package=[%s] using filter=[%s] in allow all mode!!", DUMMY_PACKAGE, loggingPojoClassFilter.getClass()), pojoClasses);
        Affirm.affirmEquals(String.format("Wrong number of classes retrieved!! Classes added/removed from package=[%s]?", DUMMY_PACKAGE), 2, pojoClasses.size());
    }

    /**
     * Test getting classes from a higherarchy.
     */
    @Test
    public void testGetPojoClassesRecursively() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively(DUMMY_PACKAGE, null);
        Affirm.affirmEquals(pojoClasses.toString(), 3, pojoClasses.size());
    }

    /**
     * Test getting classes by extending type (implementing interface, or extending abstract or another class).
     */
    @Test
    public void testEnumerateClassesByExtendingType() {
        List<PojoClass> pojoClasses = PojoClassFactory.enumerateClassesByExtendingType(DUMMY_PACKAGE,
                Persistable.class, null);
        Affirm.affirmEquals(pojoClasses.toString(), 2, pojoClasses.size());
    }

}
