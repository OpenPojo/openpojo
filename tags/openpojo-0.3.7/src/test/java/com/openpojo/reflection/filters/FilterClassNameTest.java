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
package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.sampleclasses.SampleEnum;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

public class FilterClassNameTest {

    @Test
    public void testShouldIncludeFileName() {
        PojoClassFilter filter = new FilterClassName(".+Enum");

        Affirm.affirmTrue(String.format("[%s] didn't include class!!", filter), filter.include(PojoClassFactory
                .getPojoClass(SampleEnum.class)));
    }

    @Test
    public void testShouldExncludeFileName() {
        PojoClassFilter filter = new FilterClassName(".+Sample$");

        Affirm.affirmFalse(String.format("[%s] didn't exclude class!!", filter), filter.include(PojoClassFactory
                .getPojoClass(SampleEnum.class)));
    }
}
