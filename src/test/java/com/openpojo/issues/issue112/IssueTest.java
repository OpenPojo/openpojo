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
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
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
  private Level getterTesterLevelBefore;
  private Level setterTesterLevelBefore;
  private SpyAppender appender;

  @Before
  public void setup() {
    getterTesterLevelBefore = getLogLevel(GetterTester.class);
    setLogLevel(GetterTester.class, Level.ALL);

    setterTesterLevelBefore = getLogLevel(SetterTester.class);
    setLogLevel(SetterTester.class, Level.ALL);

    appender = new SpyAppender();
    LogManager.getLogger(GetterTester.class).addAppender(appender);
    LogManager.getLogger(SetterTester.class).addAppender(appender);
  }

  private Level getLogLevel(Class<?> clazz) {
    return LogManager.getLogger(clazz).getLevel();
  }

  private void setLogLevel(Class<?> clazz, Level level) {
    LogManager.getLogger(clazz).setLevel(level);
  }

  @After
  public void tearDown() {
    setLogLevel(GetterTester.class, getterTesterLevelBefore);
    setLogLevel(SetterTester.class, setterTesterLevelBefore);
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

    Assert.assertThat(appender.getEventList().size(), is(2));

    PojoField xmlGregorianCalendarPojoField = pojoClass.getPojoFields().get(0);
    final String message = "Testing Field [" + xmlGregorianCalendarPojoField + "] with value [";

    validateLogMessages(appender, 0, GetterTester.class.getName(), message);
    validateLogMessages(appender, 1, SetterTester.class.getName(), message);

  }

  private void validateLogMessages(SpyAppender appender, int index, String testerName, String message) {
    Assert.assertThat(appender.getEventList().get(index).getRenderedMessage(), containsString(message));
    Assert.assertThat(appender.getEventList().get(index).getLevel(), is(Level.DEBUG));
    Assert.assertThat(appender.getEventList().get(index).getLoggerName(), is(testerName));
  }
}
