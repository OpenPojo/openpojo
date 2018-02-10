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

package com.openpojo.utils.samplejar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author oshoukry
 */
public class SampleJar {
  private final URLClassLoader urlClassLoader;
  private final String jarUrlPath;
  private final String jarFilePath;

  private static final SampleJar INSTANCE = new SampleJar();


  public static URLClassLoader getURLClassLoader() {
    return INSTANCE.urlClassLoader;
  }

  public static URL getJarURL() {
    try {
      return new URL(getJarURLPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getJarURLPath() {
    return INSTANCE.jarUrlPath;
  }

  public static String getJarFilePath() {
    return INSTANCE.jarFilePath;
  }

  private String buildJarFilePath() {
    String systemPath = "";
    String userdir = System.getProperty("user.dir");

    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");

    if (isWindows)
      systemPath += userdir.substring(2).replace("\\", "/"); // skip over the letter drive (i.e. C:)
    else
      systemPath += userdir;

    systemPath += "/test/sampleJar.zip";
    return systemPath;
  }

  private SampleJar() {
    jarFilePath = buildJarFilePath();
    jarUrlPath = "jar:file://" + jarFilePath + "!/" ;
    urlClassLoader = getClassLoader();
  }

  private URLClassLoader getClassLoader() {
    try {
      return URLClassLoader.newInstance(new URL[] { new URL(jarUrlPath) });
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
