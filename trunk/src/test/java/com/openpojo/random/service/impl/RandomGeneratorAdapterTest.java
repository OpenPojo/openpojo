/**
 * Copyright (C) 2012 Osman Shoukry
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
package com.openpojo.random.service.impl;

import org.junit.Test;

import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 *
 */
public class RandomGeneratorAdapterTest {

    @Test (expected = RandomGeneratorException.class)
    public void getTypesShouldThrowException() {
        final RandomGeneratorAdapter randomGeneratorAdapter = new RandomGeneratorAdapter(null, null, null);
        randomGeneratorAdapter.getTypes();
        }


    @Test (expected = RandomGeneratorException.class)
    public void doGenerateOnNonRegisteredTypeShouldThrowException() {
        final RandomGeneratorAdapter randomGeneratorAdapter = new RandomGeneratorAdapter(null, null, null);
        randomGeneratorAdapter.doGenerate(anyClass());

    }

    private Class<?> anyClass() {
        return this.getClass();
    }
}
