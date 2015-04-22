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

package com.openpojo.issues.issue55;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

    @Test
    public void ensureEnumStaysIntact() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(SomeEnumWithValuesMethod.class);
        boolean valuesMethodExists = false;
        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (pojoMethod.getName().equals("values") && pojoMethod.getPojoParameters().size() > 0 && pojoMethod.isStatic())
                valuesMethodExists = true;
        }
        Affirm.affirmTrue("values method must exist, Enum Class Changed?!", valuesMethodExists);
    }

    @Test
    public void shouldCallEnumValuesWithNoParameters() {
        SomeEnumWithValuesMethod randomEntry = RandomFactory.getRandomValue(SomeEnumWithValuesMethod.class);
        Affirm.affirmTrue("Should have generated using proper values method", SomeEnumWithValuesMethod.VALUE1.equals(randomEntry) ||
                SomeEnumWithValuesMethod.VALUE2.equals(randomEntry));
    }
}
