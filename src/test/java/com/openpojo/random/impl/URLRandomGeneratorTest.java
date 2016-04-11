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

package com.openpojo.random.impl;

import java.net.URL;
import java.util.Collection;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.After;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author oshoukry
 */
public class URLRandomGeneratorTest {

  @Test
  public void constructorIsPrivate() {
    PojoClass urlPojoClass = PojoClassFactory.getPojoClass(URLRandomGenerator.class);
    for (PojoMethod constructor : urlPojoClass.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        assertTrue(constructor + " should be private", constructor.isPrivate());
    }
  }

  @Test
  public void canCreate() {
    URLRandomGenerator urlRandomGenerator = URLRandomGenerator.getInstance();
    assertNotNull(urlRandomGenerator);
  }

  @Test
  public void getTypesReturnsURLTypeOnly() {
    Collection<Class<?>> types = URLRandomGenerator.getInstance().getTypes();

    assertNotNull(types);
    assertEquals(1, types.size());
    assertEquals(URL.class, types.toArray()[0]);
  }

  @Test
  public void canGenerateURL() {
    Object firstURL = URLRandomGenerator.getInstance().doGenerate(URL.class);
    assertNotNull(firstURL);
    assertTrue(firstURL instanceof URL);
  }

  @Test
  public void generatedURLIsRandom() {
    URL firstURL = (URL) URLRandomGenerator.getInstance().doGenerate(URL.class);
    assertNotNull(firstURL);
    URL secondURL = (URL) URLRandomGenerator.getInstance().doGenerate(URL.class);

    if (secondURL.equals(firstURL)) // in the 1 in a million chance this is true, try again
      secondURL = (URL) URLRandomGenerator.getInstance().doGenerate(URL.class);

    assertNotEquals(firstURL, secondURL);
  }

  @Test
  public void shouldIgnoreTypeParameter() {
    URL generatedURL = (URL) URLRandomGenerator.getInstance().doGenerate(this.getClass());
    assertNotNull(generatedURL);
  }

  @Test
  public void end2endTest() {
    URL generatedURL = RandomFactory.getRandomValue(URL.class);
    assertNotNull(generatedURL);
  }

  @Test (expected = RandomGeneratorException.class)
  public void willThrowExceptionWhenHostPrefixMalformed() {
    URLRandomGenerator.getInstance().setUrlPrefix("");
    URLRandomGenerator.getInstance().doGenerate(URL.class);
  }

  @After
  public void tearDown() {
    URLRandomGenerator.getInstance().setUrlPrefix("http://randomurl.openpojo.com/");
  }
}
