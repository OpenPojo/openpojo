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

package com.openpojo.random.generator.security;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.generator.AbstractGeneratorTest;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class EncryptionKeyRandomGeneratorTest extends AbstractGeneratorTest {

  protected PojoClass getPojoClass() {
    return PojoClassFactory.getPojoClass(EncryptionKeyRandomGenerator.class);
  }

  protected String getTypeName() {
    Class<?> encryptionKeyClass = ClassUtil.loadClass("sun.security.krb5.EncryptionKey");
    if (encryptionKeyClass != null)
      return encryptionKeyClass.getName();
    return null;
  }

  protected RandomGenerator getRandomGenerator() {
    return EncryptionKeyRandomGenerator.getInstance();
  }
}