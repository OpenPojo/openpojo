/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.validation.test.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.log.SpyAppender;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.AClassWithFieldThatThrowsExceptionWhenToString;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author oshoukry
 */
public abstract class LoggingTesterTest {
  private SpyAppender spyAppender;
  private Class<?> sampleClass;
  private Validator validator;
  private Tester tester;

  @Before
  public void setup() {
    spyAppender = new SpyAppender();
    tester = getTester();
    spyAppender.captureForLogger(tester.getClass());
    sampleClass = AClassWithFieldThatThrowsExceptionWhenToString.class;
    validator = ValidatorBuilder
        .create()
        .with(tester)
        .build();
  }

  protected abstract Tester getTester();

  @Test
  public void shouldSuccessfullyValidateEvenIfLoggingFails() {
    validator.validate(PojoClassFactory.getPojoClass(sampleClass));

    final List<LoggingEvent> eventsForLogger = spyAppender.getEventsForLogger(tester.getClass());
    assertThat(eventsForLogger.size(), is(1));

    String expectedLog = getExpectedLogMessage();
    assertThat(eventsForLogger.get(0).getMessage().toString(), is(expectedLog));
  }

  private String getExpectedLogMessage() {
    final PojoField pojoField = getPojoField();
    return "Testing Field ["
        + pojoField
        + "] with value [Error calling toString: 'java.lang.RuntimeException: UnSupported call to toString()']";
  }

  private PojoField getPojoField() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(sampleClass);
    return pojoClass.getPojoFields().get(0);
  }

  @After
  public void tearDown() {
    spyAppender.stopCaptureForLogger(tester.getClass());
  }

}
