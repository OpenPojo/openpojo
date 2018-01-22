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

package com.openpojo.issues.issue112;

import com.openpojo.issues.issue112.sample.AClassWithXMLGregorianCalendar;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.log.SpyAppender;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.apache.log4j.Level;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;


/**
 * @author oshoukry
 */
public class IssueTest {
  private SpyAppender appender;

  @Before
  public void setup() {
    appender = new SpyAppender();
    appender.startCaptureForLogger(GetterTester.class);
    appender.startCaptureForLogger(SetterTester.class);
  }

  @After
  public void tearDown() {
    appender.stopCaptureForLogger(GetterTester.class);
    appender.stopCaptureForLogger(SetterTester.class);
  }

  @Test
  public void shouldNotFail() {
    Validator validator = ValidatorBuilder
        .create()
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    final PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithXMLGregorianCalendar.class);
    validator.validate(pojoClass);

    Assert.assertThat(appender.getEventsForLogger(GetterTester.class).size(), is(1));
    Assert.assertThat(appender.getEventsForLogger(SetterTester.class).size(), is(1));

    PojoField xmlGregorianCalendarPojoField = pojoClass.getPojoFields().get(0);
    final String message = "Testing Field [" + xmlGregorianCalendarPojoField + "] with value [";

    validateLogMessages(appender, GetterTester.class, message);
    validateLogMessages(appender, SetterTester.class, message);
  }

  private void validateLogMessages(SpyAppender appender, Class<?> testerClassName, String message) {
    Assert.assertThat(appender.getEventsForLogger(testerClassName).get(0).getRenderedMessage(), containsString(message));
    Assert.assertThat(appender.getEventsForLogger(testerClassName).get(0).getLevel(), is(Level.DEBUG));
    Assert.assertThat(appender.getEventsForLogger(testerClassName).get(0).getLoggerName(), is(testerClassName.getName()));
  }
}
