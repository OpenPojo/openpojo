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
package com.openpojo.random.dynamic;

import java.util.Date;
import java.util.Random;

import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 */
public final class EnumRandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());

    public static EnumRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public Object doGenerate(final Class<?> type) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(type);
        if (!pojoClass.isEnum()) {
            throw RandomGeneratorException.getInstance(String.format("[%s] is not an enum!!", pojoClass));
        }

        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (pojoMethod.getName() == "values") {
                Enum[] values = (Enum[]) pojoMethod.invoke(null, (Object[]) null);
                return values[RANDOM.nextInt(values.length)];
            }
        }
        throw RandomGeneratorException
                .getInstance(String.format("values method not found on enum = [%s]!!", pojoClass));
    }

    private static class Instance {
        private static final EnumRandomGenerator INSTANCE = new EnumRandomGenerator();
    }
}
