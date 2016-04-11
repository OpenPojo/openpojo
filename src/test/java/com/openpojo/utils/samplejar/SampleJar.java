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

package com.openpojo.utils.samplejar;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author oshoukry
 */
public class SampleJar {
  private final URLClassLoader urlClassLoader;
  private final String path;

  private static final SampleJar INSTANCE = new SampleJar();


  public static URLClassLoader getURLClassLoader() {
    return INSTANCE.urlClassLoader;
  }

  public static String getPath() {
    return INSTANCE.path;
  }

  private SampleJar() {
    String tmpPath = "jar:file://";
    String userdir = System.getProperty("user.dir");

    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
    if (isWindows)
      tmpPath += userdir.substring(2).replace("\\", "/"); // skip over the C:
    else
      tmpPath += userdir;

    tmpPath += "/test/sampleJar.zip!/";

    path = tmpPath;
    urlClassLoader = getClassLoader();
  }

  private URLClassLoader getClassLoader() {
    try {
      return URLClassLoader.newInstance(new URL[] { new URL(path) });
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
