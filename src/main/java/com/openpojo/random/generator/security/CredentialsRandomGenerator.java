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
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;

import java.util.*;

import static com.openpojo.random.RandomFactory.getRandomValue;
import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;
import static com.openpojo.reflection.java.load.ClassUtil.loadClass;

/**
 * @author oshoukry
 */
public class CredentialsRandomGenerator implements RandomGenerator {

  private static final String TYPE = "sun.security.krb5.Credentials";
  private static final CredentialsRandomGenerator INSTANCE = new CredentialsRandomGenerator();
  private final Class<?> credentialsClass;

  private CredentialsRandomGenerator() {
    credentialsClass = loadClass(TYPE);
  }

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> supported = new ArrayList<Class<?>>();
    if (credentialsClass != null)
      supported.add(credentialsClass);
    return supported;
  }

  public Object doGenerate(Class<?> type) {
    Object var1 = getRandomValue(loadClass("sun.security.krb5.internal.Ticket"));

    Object var2 = getRandomValue(loadClass("sun.security.krb5.PrincipalName"));
    Object var3 = getRandomValue(loadClass("sun.security.krb5.PrincipalName"));

    Object var4 = getRandomValue(loadClass("sun.security.krb5.EncryptionKey"));

    Object var5 = getRandomValue(loadClass("sun.security.krb5.internal.TicketFlags"));

    Object var6 = getRandomValue(loadClass("sun.security.krb5.internal.KerberosTime"));
    Object var7 = getRandomValue(loadClass("sun.security.krb5.internal.KerberosTime"));
    Object var8 = getRandomValue(loadClass("sun.security.krb5.internal.KerberosTime"));
    Object var9 = getRandomValue(loadClass("sun.security.krb5.internal.KerberosTime"));

    Object var10 = getRandomValue(loadClass("sun.security.krb5.internal.HostAddresses"));

    PojoClass pojoClass = getPojoClass(credentialsClass);
    return InstanceFactory.getInstance(pojoClass, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
  }
}