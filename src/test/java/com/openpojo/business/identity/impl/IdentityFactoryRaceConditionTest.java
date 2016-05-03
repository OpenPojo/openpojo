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

package com.openpojo.business.identity.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.openpojo.business.identity.impl.sampleclasses.PojoClassWithHashCodeBusinessIdentity;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IdentityFactoryRaceConditionTest {

  @Test
  public void runWithManyThreads() {
    int numberOfThreads = 1500;

    ReportingThreadExecutionPool tpe = getReportingThreadExecutionPool(numberOfThreads);
    CyclicBarrier cb = new CyclicBarrier(numberOfThreads);

    Validator validator = getValidator();

    for (int i = 0; i < numberOfThreads; i++)
      tpe.execute(new Worker(cb, validator));

    tpe.shutdown();

    while (!tpe.isTerminated())
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException ignored) {
      }

    Assert.assertEquals("Some threads failed to complete successfully", numberOfThreads, tpe.getCompletedSuccessfully());
  }

  private ReportingThreadExecutionPool getReportingThreadExecutionPool(int numberOfThreads) {
    return new ReportingThreadExecutionPool(
        numberOfThreads,
        numberOfThreads, 3 * 60,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(numberOfThreads));
  }

  private Validator getValidator() {
    return ValidatorBuilder.create()
        .with(new BusinessKeyMustExistRule())
        .with(new GetterMustExistRule())
        .with(new NoFieldShadowingRule())
        .with(new NoNestedClassRule())
        .with(new NoPrimitivesRule())
        .with(new NoPublicFieldsExceptStaticFinalRule())
        .with(new NoPublicFieldsRule())
        .with(new NoStaticExceptFinalRule())
        .with(new SerializableMustHaveSerialVersionUIDRule())
        .with(new SetterMustExistRule())
        .with(new BusinessIdentityTester())
        .with(new DefaultValuesNullTester())
        .with(new GetterTester())
        .with(new SetterTester())
        .build();
  }

  private class Worker implements Runnable {
    private final CyclicBarrier cyclicBarrier;
    private final Validator validator;

    Worker(final CyclicBarrier cyclicBarrier, final Validator validator) {
      this.cyclicBarrier = cyclicBarrier;
      this.validator = validator;
    }

    public void run() {
      try {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(PojoClassWithHashCodeBusinessIdentity.class);
        cyclicBarrier.await();
        validator.validate(pojoClass);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  private class ReportingThreadExecutionPool extends ThreadPoolExecutor {
    private AtomicInteger completedSuccessfully = new AtomicInteger(0);

    ReportingThreadExecutionPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
          throw new RuntimeException("Rejected Task - " + r);
        }
      });
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
      if (t == null)
        completedSuccessfully.incrementAndGet();
    }

    int getCompletedSuccessfully() {
      return completedSuccessfully.get();
    }
  }
}
