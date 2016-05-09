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

package com.openpojo.random.generator.security;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.generator.AbstractGeneratorTest;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import sun.security.krb5.EncryptionKey;

/**
 * @author oshoukry
 */
public class EncryptionKeyRandomGeneratorTest extends AbstractGeneratorTest {

  protected PojoClass getPojoClass() {
    return PojoClassFactory.getPojoClass(EncryptionKeyRandomGenerator.class);
  }

  protected String getTypeName() {
    return EncryptionKey.class.getName();
  }

  protected RandomGenerator getRandomGenerator() {
    return EncryptionKeyRandomGenerator.getInstance();
  }
}