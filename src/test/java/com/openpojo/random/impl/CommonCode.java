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

package com.openpojo.random.impl;

import org.junit.Assert;

import com.openpojo.random.RandomGenerator;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class CommonCode {
    /**
     * Utility method to assist with testing all non-Primitive classes.
     *
     * @param type
     *            The class type to test.
     */
    public static void testDoGenerateForClass(final RandomGenerator randomGenerator, final Class<? extends Object> type) {
        Affirm.affirmTrue(String.format("Failed to get the requested type=[%s] from [%s] recieved=[%s] instead", type,
                randomGenerator, randomGenerator.doGenerate(type)), type.isInstance(randomGenerator.doGenerate(type)));
        Object object = type.cast(randomGenerator.doGenerate(type));

        Affirm.affirmNotNull(String.format("Request to registered type [%s] must return non-null value!!", type),
                object);

        Object anotherObject = randomGenerator.doGenerate(type);
        if (object.equals(anotherObject)) { // Just incase they are the same
            anotherObject = randomGenerator.doGenerate(type);
            // if Boolean, try for 20 times, since there is a 50% chance we land on the same value.
            if (object.equals(anotherObject) && type == Boolean.class) {
                for (int counter = 0; counter < 20; counter++) {
                    anotherObject = randomGenerator.doGenerate(type);
                    if (!object.equals(anotherObject)) {
                        break;
                    }
                }
            }
        }
        Affirm.affirmFalse(String.format("[%s] generating the same values for type=[%s]", randomGenerator, type),
                object.equals(anotherObject));
    }

    public static void testGetType(final RandomGenerator randomGenerator, final Class<?> type, final int expectedCount) {
        Assert.assertEquals(String.format("[%s] added/removed Types?", randomGenerator), expectedCount, randomGenerator
                .getTypes().size());
        Assert.assertTrue(String.format("[%s] doesn't report responsibility for [%s]?!!", randomGenerator.getClass(),
                type), randomGenerator.getTypes().contains(type));
    }
}
