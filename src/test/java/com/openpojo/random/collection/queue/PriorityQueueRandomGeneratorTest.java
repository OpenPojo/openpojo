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

package com.openpojo.random.collection.queue;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.Parameterizable;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PriorityQueueRandomGeneratorTest {

    @Test
    public void shouldBeAbleToCreate() {
        Assert.assertNotNull(PriorityQueueRandomGenerator.getInstance());
        Assert.assertEquals(PriorityQueueRandomGenerator.class, PriorityQueueRandomGenerator.getInstance().getClass());
    }

    @Test
    public void shouldGenerateForPriorityQueueOnly() {
        Collection<Class<?>> types = PriorityQueueRandomGenerator.getInstance().getTypes();
        Assert.assertEquals(1, types.size());
        Assert.assertEquals(PriorityQueue.class, types.iterator().next());
    }

    @Test (expected = RandomGeneratorException.class)
    public void whenGenerateWithNonPriorityQueue_ThrowsException() {
        PriorityQueueRandomGenerator.getInstance().doGenerate(Object.class);
    }

    @Test
    public void whenGenerateWithPriorityQueue_ReturnNonEmptyPriorityQueue() {
        PriorityQueue priorityQueue = (PriorityQueue) PriorityQueueRandomGenerator.getInstance().doGenerate(PriorityQueue.class);
        Assert.assertNotNull(priorityQueue);
        Assert.assertTrue(priorityQueue.size() > 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenGenerateWithGenericPriorityQueue_GenerateCorrectly() {
        Parameterizable parameterizable = new Parameterizable() {
            public Class<?> getType() {
                return PriorityQueue.class;
            }

            public boolean isParameterized() {
                return true;
            }

            public List<Type> getParameterTypes() {
                return Arrays.asList(new Type[]{ String.class });
            }
        };

        PriorityQueue<String> aPriorityQueueOfStrings;
        aPriorityQueueOfStrings = (PriorityQueue<String>) PriorityQueueRandomGenerator.getInstance().doGenerate(parameterizable);
        Assert.assertNotNull("Should not be null", aPriorityQueueOfStrings);
        Assert.assertTrue("Should have entries", aPriorityQueueOfStrings.size() > 0);
        for (Object s: aPriorityQueueOfStrings) {
            Assert.assertNotNull(s);
            Assert.assertTrue("Should be String", s instanceof String);
        }
    }

    @Test
    public void testEndToEnd() {
        PriorityQueue priorityQueue = RandomFactory.getRandomValue(PriorityQueue.class);
        Assert.assertNotNull("Should not be null", priorityQueue);
        Assert.assertTrue("Should not be empty", priorityQueue.size() > 0);
    }
}
