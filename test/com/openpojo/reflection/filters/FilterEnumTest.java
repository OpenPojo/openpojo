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
package com.openpojo.reflection.filters;

import java.util.List;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.sampleclasses.SampleEnum;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class FilterEnumTest {

    private final String sampleClassesPackage = this.getClass().getPackage().getName() + ".sampleclasses";
    private final PojoClassFilter enumFilter = new FilterEnum();
    private final List<PojoClass> filteredPojoClasses = PojoClassFactory.getPojoClasses(sampleClassesPackage,
            enumFilter);
    private final int EXPECTED_FILTERED_COUNT = 5;

    private final List<PojoClass> nonFilteredPojoClasses = PojoClassFactory.getPojoClasses(sampleClassesPackage);
    private final int EXPECTED_NON_FILTERED_COUNT = 6;

    @Test
    public void shouldConfirmSampleClassCount() {
        Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", sampleClassesPackage),
                EXPECTED_NON_FILTERED_COUNT, nonFilteredPojoClasses.size());
        Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", sampleClassesPackage),
                EXPECTED_FILTERED_COUNT, filteredPojoClasses.size());
    }

    @Test
    public void shouldIncludeNonEnum() {
        for (PojoClass pojoClass : filteredPojoClasses) {
            Affirm.affirmFalse(String.format("[%s] should have been filtered out!!", pojoClass), pojoClass.isEnum());
        }
    }

    @Test
    public final void shouldExcludeEnum() {
        Affirm.affirmFalse(String.format("[%s] didn't exclude enum!!", enumFilter), enumFilter.include(PojoClassFactory
                .getPojoClass(SampleEnum.class)));

    }

}
