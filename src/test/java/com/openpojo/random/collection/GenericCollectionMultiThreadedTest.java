/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class GenericCollectionMultiThreadedTest {
  private int ttl_jobs;
  private int per_thread;

  @Before
  public void setup() {
    String javaVersion = System.getProperty("java.version");
    if (javaVersion.startsWith("1.5")) {
      ttl_jobs = 100;
      per_thread = 17;
    } else {
      ttl_jobs = 1000;
      per_thread = 30;
    }
  }

  @Test
  public void shouldCreateGenericCollection() throws ExecutionException, InterruptedException {
    List<PojoClass> pojoClasses = new ArrayList<PojoClass>(2);
    pojoClasses.add(PojoClassFactory.getPojoClass(AClassWithGenericCollection.class));
    pojoClasses.add(PojoClassFactory.getPojoClass(AClassWithExhaustiveCollection.class));

    Assert.assertEquals(2, pojoClasses.size());

    RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

    ThreadPoolExecutor executorPool = new ThreadPoolExecutor(ttl_jobs / per_thread, ttl_jobs / per_thread, 10,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(ttl_jobs),
        Executors.defaultThreadFactory(), rejectionHandler);

    List<Future> tasksStatus = new ArrayList<Future>(ttl_jobs);
    for (int i = 0; i < ttl_jobs; i++) {
      tasksStatus.add(executorPool.submit(new Verify(pojoClasses)));
    }


    executorPool.shutdown();
    while (!executorPool.isTerminated())
      try {
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
    private Validator pojoValidator;


    private Verify(List<PojoClass> pojoClasses) {
      this.pojoClasses = pojoClasses;
      pojoValidator = ValidatorBuilder.create()
          .with(new SetterMustExistRule())
          .with(new GetterMustExistRule())
          .with(new SetterTester())
          .with(new GetterTester())
          .build();
    }

    public void run() {
      pojoValidator.validate(pojoClasses);
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
