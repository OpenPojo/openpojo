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

package com.openpojo.reflection.adapt.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.impl.sampleclasses.CloverInstrumentedClass;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.affirm.Affirm;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class CloverPojoClassAdapterTest {

    PojoClass cloverInstrumentedPojoClass;
    PojoClass cloverCleanedPojoClass;

    private static Boolean cloverWasConfigured;

    @BeforeClass
    public static void initialSetup() {
        PojoClassAdaptationService adaptationService = ServiceRegistrar.getInstance().getPojoClassAdaptationService();
        cloverWasConfigured = adaptationService.getRegisteredPojoAdapterClasses().contains(CloverPojoClassAdapter.getInstance());
        adaptationService.unRegisterPojoClassAdapter(CloverPojoClassAdapter.getInstance());
    }

    @Before
    public void setup() {
        cloverInstrumentedPojoClass = PojoClassFactory.getPojoClass(CloverInstrumentedClass.class);
        cloverCleanedPojoClass = CloverPojoClassAdapter.getInstance().adapt(cloverInstrumentedPojoClass);
    }

    @AfterClass
    public static void reInstateInitialSetup() {
        if (cloverWasConfigured) {
            ServiceRegistrar.getInstance().getPojoClassAdaptationService().registerPojoClassAdapter(CloverPojoClassAdapter.getInstance());
        }
    }

    @Test
    public void ensureCoberturaInstrumentedClassNotChanged() {
        System.out.println(cloverInstrumentedPojoClass.toString());
        int expectedFields = 4;
        if (cloverWasConfigured)
            expectedFields++;
        Affirm.affirmEquals("Fields added/removed?", expectedFields, cloverInstrumentedPojoClass.getPojoFields().size());
        Affirm.affirmEquals("Methods added/removed?", 3, cloverInstrumentedPojoClass.getPojoMethods().size());
    }

    @Test
    public void shouldSkipFieldsStartingWith__cobertura_() {
        Affirm.affirmEquals("Cobertura fields not filtered?", 2, cloverCleanedPojoClass.getPojoFields().size());
    }

    @Test
    public void shouldSkipMethodsStartingWith__covertura_() {
        Affirm.affirmEquals("Cobertura methods not filtered?", 3, cloverCleanedPojoClass.getPojoMethods().size());
    }

}
