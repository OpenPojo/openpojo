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

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import sun.security.krb5.Credentials;
import sun.security.krb5.EncryptionKey;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.HostAddresses;
import sun.security.krb5.internal.KerberosTime;
import sun.security.krb5.internal.Ticket;
import sun.security.krb5.internal.TicketFlags;

/**
 * @author oshoukry
 */
public class CredentialsRandomGenerator implements RandomGenerator {

  private static final Class<?>[] TYPES = new Class<?>[] { Credentials.class };
  private static final CredentialsRandomGenerator INSTANCE = new CredentialsRandomGenerator();

  private CredentialsRandomGenerator() {
  }

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public Object doGenerate(Class<?> type) {
    Ticket var1 = RandomFactory.getRandomValue(Ticket.class);

    PrincipalName var2 = RandomFactory.getRandomValue(PrincipalName.class);
    PrincipalName var3 = RandomFactory.getRandomValue(PrincipalName.class);

    EncryptionKey var4 = RandomFactory.getRandomValue(EncryptionKey.class);

    TicketFlags var5 = RandomFactory.getRandomValue(TicketFlags.class);

    KerberosTime var6 = RandomFactory.getRandomValue(KerberosTime.class);
    KerberosTime var7 = RandomFactory.getRandomValue(KerberosTime.class);
    KerberosTime var8 = RandomFactory.getRandomValue(KerberosTime.class);
    KerberosTime var9 = RandomFactory.getRandomValue(KerberosTime.class);

    HostAddresses var10 = RandomFactory.getRandomValue(HostAddresses.class);

    return new Credentials(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
  }
}