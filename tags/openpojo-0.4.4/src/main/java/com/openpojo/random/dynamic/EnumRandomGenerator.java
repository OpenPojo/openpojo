/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.random.dynamic;

import java.util.Date;
import java.util.Random;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This RandomGenerator handles retrieval of random Enum values from an enum.
 *
 * @author oshoukry
 */
public final class EnumRandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());

    private EnumRandomGenerator() {
    }

    public static EnumRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(final Class<?> type) {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(type);

        final Enum<?>[] values = getValues(pojoClass);
        return values[RANDOM.nextInt(values.length)];
    }

    private Enum<?>[] getValues(final PojoClass enumPojoClass) {
        Enum<?>[] values = null;
        for (final PojoMethod pojoMethod : enumPojoClass.getPojoMethods()) {
            if (pojoMethod.getName().equals("values")) {
                values = (Enum<?>[]) pojoMethod.invoke(null, (Object[]) null);
                break;
            }
        }
        return values;
    }

    private static class Instance {
        private static final EnumRandomGenerator INSTANCE = new EnumRandomGenerator();
    }
}
