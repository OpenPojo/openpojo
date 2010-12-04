/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.facade;

import java.util.Arrays;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.facade.sampleclasses.FirstClass;
import com.openpojo.reflection.facade.sampleclasses.SecondClass;
import com.openpojo.validation.affirm.Affirm;

public class FacadeFactoryTest {
    Class<?> firstClass = FirstClass.class;
    Class<?> secondClass = SecondClass.class;

    @Test
    public final void shouldGetFirstClass() {
        checkReturnedFacade(firstClass, firstClass.getName(), secondClass.getName());
        checkReturnedFacade(secondClass, secondClass.getName(), firstClass.getName());
    }

    private final void checkReturnedFacade(final Class<?> expected, final String... facades) {
        PojoClass facade = FacadeFactory.getLoadedFacadePojoClass(facades);
        Affirm.affirmNotNull(String.format("Failed to load from the valid list of facades [%s]?!", Arrays.toString(facades)), facade);

        Affirm.affirmEquals("Wrong facade returned!!", expected, facade.getClazz());

    }

    @Test(expected = ReflectionException.class)
    public void shouldThrowErorr() {
        FacadeFactory.getLoadedFacadePojoClass(new String[]{ (String) RandomFactory.getRandomValue(String.class) });
    }
}
