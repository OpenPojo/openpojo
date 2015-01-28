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

package com.openpojo.random.array;

import java.lang.reflect.Array;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 *
 */
public class RandomFactoryArrayTest {

    @Test
    public void shouldCreateRandomArray() {
        final Class<?> type = anyArrayType();
        final Object firstInstance = RandomFactory.getRandomValue(type);
        Affirm.affirmNotNull("Failed to get random array", firstInstance);

        final Object secondInstance = RandomFactory.getRandomValue(type);

        if (Array.getLength(firstInstance) == Array.getLength(secondInstance)) {
            boolean equals = true;
            for (int idx = 0; idx < Array.getLength(firstInstance); idx++) {
                final Object firstInstanceElement = Array.get(firstInstance, idx);
                final Object secondInstanceElement = Array.get(secondInstance, idx);
                equals = equals
                        && ((firstInstanceElement != null && firstInstanceElement.equals(secondInstanceElement)) || (firstInstanceElement == null && secondInstanceElement == null));
            }
        }
    }

    private Class<?> anyArrayType() {
        return Integer[].class;
    }
}
