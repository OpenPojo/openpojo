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

package com.openpojo.issues.issue82;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {
  private static final Class<?> ZONED_DATE_TIME_CLASS = ClassUtil.loadClass("java.time.ZonedDateTime");

  @Before
  public void setup() {
    Assume.assumeNotNull(ZONED_DATE_TIME_CLASS);
  }

  @Test
  public void canGenerateRandomZonedDateTime() {
    Assert.assertNotNull(RandomFactory.getRandomValue(ZONED_DATE_TIME_CLASS));
  }

}