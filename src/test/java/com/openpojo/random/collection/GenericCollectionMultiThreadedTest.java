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

package com.openpojo.random.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.openpojo.random.collection.sample.AClassWithExhaustiveCollection;
import com.openpojo.random.collection.sample.AClassWithGenericCollection;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class GenericCollectionMultiThreadedTest {

    private String samplePackage = GenericCollectionMultiThreadedTest.class.getPackage().getName() + ".sample";

    @Test
    public void shouldCreateGenericCollection() throws ExecutionException, InterruptedException {
        List<PojoClass> pojoClasses = new ArrayList<PojoClass>(2);
        pojoClasses.add(PojoClassFactory.getPojoClass(AClassWithGenericCollection.class));
        pojoClasses.add(PojoClassFactory.getPojoClass(AClassWithExhaustiveCollection.class));

        Assert.assertEquals(2, pojoClasses.size());
        int ttl_jobs = 1000;
        int per_thread = 30;

        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(ttl_jobs/per_thread, ttl_jobs/per_thread, 10, TimeUnit.SECONDS, new
                ArrayBlockingQueue<Runnable>
                (ttl_jobs),
                Executors.defaultThreadFactory(), rejectionHandler);

        List<Future> tasksStatus = new ArrayList<Future>(ttl_jobs);
        for (int i = 0; i < ttl_jobs; i++) {
            tasksStatus.add(executorPool.submit(new Verify(pojoClasses)));
        }


        executorPool.shutdown();
        while (!executorPool.isTerminated()) try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }

        Assert.assertEquals(ttl_jobs, executorPool.getCompletedTaskCount());
        Assert.assertEquals(0, rejectionHandler.getCount());
        for (Future f : tasksStatus) {
            Assert.assertNull(f.get());
        }
    }

    private static class Verify implements Runnable {
        private List<PojoClass> pojoClasses;
        private PojoValidator pojoValidator = new PojoValidator();


        private Verify(List<PojoClass> pojoClasses) {
            this.pojoClasses = pojoClasses;
            pojoValidator.addRule(new SetterMustExistRule());
            pojoValidator.addRule(new GetterMustExistRule());
            pojoValidator.addTester(new SetterTester());
            pojoValidator.addTester(new GetterTester());
        }

        public void run() {
            for (PojoClass entry : pojoClasses)
                pojoValidator.runValidation(entry);
        }
    }

    private static class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
        private int count = 0;
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

}
