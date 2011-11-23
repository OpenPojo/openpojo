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
package com.openpojo.random.dynamic;

import java.util.Date;
import java.util.Random;

import com.openpojo.log.utils.MessageFormatter;
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

    public Object doGenerate(final Class<?> type) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(type);

        Enum<?>[] values = getValues(pojoClass);
        if (values == null) {
            throw RandomGeneratorException.getInstance(MessageFormatter.format("Failed to enumerate possible values of Enum [{0}]", type));
        }

        return values[RANDOM.nextInt(values.length)];
    }

    private Enum<?>[] getValues(PojoClass enumPojoClass) {
        for (PojoMethod pojoMethod : enumPojoClass.getPojoMethods()) {
            if (pojoMethod.getName().equals("values")) {
                return (Enum<?>[]) pojoMethod.invoke(null, (Object[]) null);
            }
        }
        return null;
    }

    private static class Instance {
        private static final EnumRandomGenerator INSTANCE = new EnumRandomGenerator();
    }
}
