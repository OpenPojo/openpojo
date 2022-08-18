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

package com.openpojo.random.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 */
public class URIRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { URI.class };
  private String uriPrefix = "http://randomuri.openpojo.com/";

  private URIRandomGenerator() {

  }

  public static URIRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public Object doGenerate(Class<?> type) {
    String entry = uriPrefix + RandomFactory.getRandomValue(UUID.class) + "/";
    try {
      return new URI(entry);
    } catch (URISyntaxException use) {
      throw RandomGeneratorException.getInstance("Failed to create random URI (Invalid uriPrefix set?): " + entry, use);
    }
  }

  public void setUriPrefix(String uriPrefix) {
    this.uriPrefix = uriPrefix;
  }

  private static class Instance {
    private static final URIRandomGenerator INSTANCE = new URIRandomGenerator();
  }
}
