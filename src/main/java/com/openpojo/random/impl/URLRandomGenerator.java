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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 */
public class URLRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { URL.class };
  private String urlPrefix = "http://randomurl.openpojo.com/";

  private URLRandomGenerator() {
  }

  public static URLRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public void setUrlPrefix(String hostPrefix) {
    this.urlPrefix = hostPrefix;
  }

  public Object doGenerate(Class<?> type) {
    String entry = urlPrefix + RandomFactory.getRandomValue(UUID.class) + "/";
    try {
      return new URL(entry);
    } catch (MalformedURLException me) {
      throw RandomGeneratorException.getInstance("Failed to create random URL (Invalid urlPrefix set?): " + entry, me);
    }
  }

  private static class Instance {
    private static final URLRandomGenerator INSTANCE = new URLRandomGenerator();
  }
}
