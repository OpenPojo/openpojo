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

import java.util.Collection;
import java.util.UUID;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author oshoukry
 */
public class UUIDRandomGeneratorTest {

  @Test
  public void constructorIsPrivate() {
    PojoClass uuidPojoClass = PojoClassFactory.getPojoClass(UUIDRandomGenerator.class);
    for (PojoMethod constructor : uuidPojoClass.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        assertTrue(constructor + " should be private", constructor.isPrivate());
    }
  }

  @Test
  public void canCreate() {
    UUIDRandomGenerator uuidRandomGenerator = UUIDRandomGenerator.getInstance();
    assertNotNull(uuidRandomGenerator);
  }

  @Test
  public void getTypesReturnsUUIDTypeOnly() {
    Collection<Class<?>> types = UUIDRandomGenerator.getInstance().getTypes();

    assertNotNull(types);
    assertEquals(1, types.size());
    assertEquals(UUID.class, types.toArray()[0]);
  }

  @Test
  public void canGenerateUUID() {
    Object firstUUID = UUIDRandomGenerator.getInstance().doGenerate(UUID.class);
    assertNotNull(firstUUID);
    assertTrue(firstUUID instanceof UUID);
  }

  @Test
  public void generatedUUIDIsRandom() {
    UUID firstUUID = (UUID) UUIDRandomGenerator.getInstance().doGenerate(UUID.class);
    assertNotNull(firstUUID);
    UUID secondUUID = (UUID) UUIDRandomGenerator.getInstance().doGenerate(UUID.class);

    if (secondUUID.equals(firstUUID)) // in the 1 in a million chance this is true, try again
      secondUUID = (UUID) UUIDRandomGenerator.getInstance().doGenerate(UUID.class);

    assertNotEquals(firstUUID, secondUUID);
  }

  @Test
  public void shouldIgnoreTypeParameter() {
    UUID generatedUUID = (UUID) UUIDRandomGenerator.getInstance().doGenerate(this.getClass());
    assertNotNull(generatedUUID);
  }

  @Test
  public void end2endTest() {
    UUID generatedUUID = RandomFactory.getRandomValue(UUID.class);
    assertNotNull(generatedUUID);
  }
}
