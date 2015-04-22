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

package com.openpojo.random.collection.set;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.SynchronousQueue;

import com.openpojo.random.collection.queue.QueueConcreteRandomGenerator;
import com.openpojo.reflection.Parameterizable;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class QueueConcreteRandomGeneratorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void whenGenerateWithGenericSynchronousQueue_GenerateCorrectly() {
        Parameterizable parameterizable = new Parameterizable() {
            public Class<?> getType() {
                return SynchronousQueue.class;
            }

            public boolean isParameterized() {
                return true;
            }

            public List<Type> getParameterTypes() {
                return Arrays.asList(new Type[] { String.class });
            }
        };

        SynchronousQueue<String> aSynchronousQueue;
        aSynchronousQueue = (SynchronousQueue<String>) QueueConcreteRandomGenerator.getInstance().doGenerate(parameterizable);
        Assert.assertNotNull("Should not be null", aSynchronousQueue);
        Assert.assertTrue("Should be empty", aSynchronousQueue.size() == 0);
    }

    @Test
    public void whenGenerateWithAnInterface_GeneratedCorrectly() {
        Queue queue = (Queue) QueueConcreteRandomGenerator.getInstance().doGenerate(BlockingQueue.class);

        Assert.assertNotNull("Should not be null", queue);
        if (!(queue instanceof SynchronousQueue))
            Assert.assertTrue("Should not be empty", queue.size() > 0);
    }

    private Parameterizable getParameterizable(final Class<?> genericType) {
        return new Parameterizable() {
            public Class<?> getType() {
                return DelayQueue.class;
            }

            public boolean isParameterized() {
                return true;
            }

            public List<Type> getParameterTypes() {
                return Arrays.asList(new Type[] { genericType });
            }
        };
    }

    private void isNotNullOrEmptyAndPopulatedWithDelayed(DelayQueue<Delayed> aDelayQueue) {
        Assert.assertNotNull("Should not be null", aDelayQueue);
        Assert.assertTrue("Should be empty", aDelayQueue.size() > 0);
        for (Object s: aDelayQueue) {
            Assert.assertNotNull(s);
            Assert.assertTrue("Should be Delayed", s instanceof Delayed);
        }
    }
}
