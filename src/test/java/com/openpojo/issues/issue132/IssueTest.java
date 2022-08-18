/*
 * Copyright (c) 2010-2019 Osman Shoukry
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

package com.openpojo.issues.issue132;

import java.io.Serializable;

import com.openpojo.issues.issue132.sample.WithNoneSerializableInterface;
import com.openpojo.issues.issue132.sample.WithSerializableInterface;
import com.openpojo.issues.issue132.sample.WithTransientNonSerializableInterface;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.test.impl.SerializableTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

public class IssueTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();
  private SerializableTester serializableTester;

  @Before
  public void setup() {
    serializableTester = new SerializableTester(true);
  }

  @Test
  public void hasSerializableInterface() {
    assertSerializable(WithSerializableInterface.class);
  }

  @Test
  public void hasTransientNonSerializableInterface() {
    assertSerializable(WithTransientNonSerializableInterface.class);
  }

  @Test
  public void hasNonSerializableInterface() {
    expectedException.expect(AssertionError.class);

    Class<?> clazz = WithNoneSerializableInterface.class;
    expectedException.expectMessage("Field [someInterface] is an interface that allows non-Serializable types on a Serializable [" + clazz + "]");

    assertSerializable(clazz);
  }

  @Test
  public void shouldPassWithNonSerializableInterfaceAndNonStrictValidation() {
    serializableTester = new SerializableTester(false);
    assertSerializable(WithNoneSerializableInterface.class);
  }

  private void assertSerializable(Class serializableClass) {
    PojoClass serializablePojoClass = PojoClassFactory.getPojoClass(serializableClass);
    assertTrue(serializablePojoClass .extendz(Serializable.class));
    serializableTester.run(serializablePojoClass);
  }
}
