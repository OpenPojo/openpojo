/**
 * Copyright (C) 2011 Osman Shoukry
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 *
 */
public class DefaultRandomGeneratorServiceTest {
    private DefaultRandomGeneratorService defaultRandomGeneratorService;

    @Before
    public void setup() {
        defaultRandomGeneratorService = new DefaultRandomGeneratorService();
    }

    @Test
    public void shouldReturnNameBasedOnClassName() {
        Affirm.affirmEquals("Name returned doesn't match class name", DefaultRandomGeneratorService.class.getName(), defaultRandomGeneratorService.getName());
    }

    @Test
    public void defaultRandomGeneratorMustNotBeInitialized() {
        Affirm.affirmNull(String.format("defaultRandomGenerator must be initialized to null for [%s]",
                                        defaultRandomGeneratorService),
                          defaultRandomGeneratorService.getDefaultRandomGenerator());
    }

    @Test
    public void shouldSetAndGetDefaultRandomGenerator() {
        RandomGenerator randomGenerator = (RandomGenerator) RandomFactory.getRandomValue(RandomGenerator.class);
        defaultRandomGeneratorService.setDefaultRandomGenerator(randomGenerator);

        Affirm.affirmEquals("Setter & Getter must match passed in value", randomGenerator,
                            defaultRandomGeneratorService.getDefaultRandomGenerator());
    }

    @Test
    public void shouldGetTypeBasedOnRegisteredRandomGenerator() {
        Class<?> type = DefaultRandomGeneratorServiceTest.class;

        Affirm.affirmNull("Should not have recieved a valid random generator for non registered type",
                          defaultRandomGeneratorService.getRandomGeneratorByType(type));

        DummyRandomGenerator dummyRandomGenerator = new DummyRandomGenerator();
        dummyRandomGenerator.setTypes(new Class<?>[] { type });
        defaultRandomGeneratorService.registerRandomGenerator(dummyRandomGenerator);

        Affirm.affirmEquals("Incorrect random generator returned", dummyRandomGenerator,
                            defaultRandomGeneratorService.getRandomGeneratorByType(type));

    }

    @Test
    public void shouldGetRandomGeneratorBasedOnAssignability() {
        Class<?> type = LinkedList.class;

        DummyRandomGenerator dummyRandomGenerator = new DummyRandomGenerator();
        dummyRandomGenerator.setTypes(new Class<?>[] { type });
        defaultRandomGeneratorService.registerRandomGenerator(dummyRandomGenerator);

        Affirm.affirmEquals("Incorrect random generator returned", dummyRandomGenerator,
                            defaultRandomGeneratorService.getRandomGeneratorByType(List.class));
    }

    @Test
    public void shouldRandomizeWhichRandomGeneratorReturnedBasedOnAssignability() {
        Class<?> type = LinkedList.class;
        Class<?> anotherType = ArrayList.class;

        DummyRandomGenerator dummyRandomGenerator = new DummyRandomGenerator();
        dummyRandomGenerator.setTypes(new Class<?>[] { type });
        defaultRandomGeneratorService.registerRandomGenerator(dummyRandomGenerator);

        DummyRandomGenerator anotherRandomGenerator = new DummyRandomGenerator();
        anotherRandomGenerator.setTypes(new Class<?>[] { anotherType });
        defaultRandomGeneratorService.registerRandomGenerator(anotherRandomGenerator);

        List<RandomGenerator> expectedRandomGenerators = new LinkedList<RandomGenerator>();
        expectedRandomGenerators.add(dummyRandomGenerator);
        expectedRandomGenerators.add(anotherRandomGenerator);

        int tolerance=20;

        while (tolerance > 0 && expectedRandomGenerators.size() > 0) {
            expectedRandomGenerators.remove(defaultRandomGeneratorService.getRandomGeneratorByType(List.class));
            tolerance--;
        }

        Affirm.affirmEquals("Failed to return all possible valid random generators based on assignability", 0, expectedRandomGenerators.size());

    }

    private static class DummyRandomGenerator implements RandomGenerator {
        private Class<?>[] types;

        private void setTypes(final Class<?>[] types) {
            this.types = types;
        }

        public Collection<Class<?>> getTypes() {
            return Arrays.asList(types);
        }

        public Object doGenerate(Class<?> type) {
            throw new IllegalStateException("Not Impelemented");
        }
    }
}
