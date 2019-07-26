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

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class KerberosTimeRandomGenerator implements RandomGenerator {
  private static final String TYPE = "sun.security.krb5.internal.KerberosTime";
  private final Class<?> kerberosTimeClass;
  private static final KerberosTimeRandomGenerator INSTANCE = new KerberosTimeRandomGenerator();

  private KerberosTimeRandomGenerator() {
    kerberosTimeClass = ClassUtil.loadClass(TYPE);
  }

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> supported = new ArrayList<Class<?>>();
    if (kerberosTimeClass != null)
      supported.add(kerberosTimeClass);
    return supported;
  }

  public Object doGenerate(Class<?> type) {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(kerberosTimeClass);
    return InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(long.class));
  }
}
