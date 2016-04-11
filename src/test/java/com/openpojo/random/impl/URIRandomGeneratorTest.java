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

import java.net.URI;
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
public class URIRandomGeneratorTest {

  @Test
  public void constructorIsPrivate() {
    PojoClass uriPojoClass = PojoClassFactory.getPojoClass(URIRandomGenerator.class);
    for (PojoMethod constructor : uriPojoClass.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        assertTrue(constructor + " should be private", constructor.isPrivate());
    }
  }

  @Test
  public void canCreate() {
    URIRandomGenerator uriRandomGenerator = URIRandomGenerator.getInstance();
    assertNotNull(uriRandomGenerator);
  }

  @Test
  public void getTypesReturnsURITypeOnly() {
    Collection<Class<?>> types = URIRandomGenerator.getInstance().getTypes();

    assertNotNull(types);
    assertEquals(1, types.size());
    assertEquals(URI.class, types.toArray()[0]);
  }

  @Test
  public void canGenerateURI() {
    Object firstURI = URIRandomGenerator.getInstance().doGenerate(URI.class);
    assertNotNull(firstURI);
    assertTrue(firstURI instanceof URI);
  }

  @Test
  public void generatedURIIsRandom() {
    URI firstURI = (URI) URIRandomGenerator.getInstance().doGenerate(URI.class);
    assertNotNull(firstURI);
    URI secondURI = (URI) URIRandomGenerator.getInstance().doGenerate(URI.class);

    if (secondURI.equals(firstURI)) // in the 1 in a million chance this is true, try again
      secondURI = (URI) URIRandomGenerator.getInstance().doGenerate(URI.class);

    assertNotEquals(firstURI, secondURI);
  }

  @Test
  public void shouldIgnoreTypeParameter() {
    URI generatedURI = (URI) URIRandomGenerator.getInstance().doGenerate(this.getClass());
    assertNotNull(generatedURI);
  }

  @Test
  public void end2endTest() {
    URI generatedURI = RandomFactory.getRandomValue(URI.class);
    assertNotNull(generatedURI);
    assertNotNull(generatedURI.getHost());
  }

  @Test (expected = RandomGeneratorException.class)
  public void willThrowExceptionWhenHostPrefixMalformed() {
    URIRandomGenerator.getInstance().setUriPrefix("<");
    URIRandomGenerator.getInstance().doGenerate(URI.class);
  }

  @After
  public void tearDown() {
    URIRandomGenerator.getInstance().setUriPrefix("http://randomuri.openpojo.com/");
  }
}
