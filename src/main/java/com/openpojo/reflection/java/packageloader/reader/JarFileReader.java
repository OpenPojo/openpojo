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

package com.openpojo.reflection.java.packageloader.reader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.packageloader.impl.URLToFileSystemAdapter;
import com.openpojo.reflection.java.packageloader.utils.Helper;

import static com.openpojo.reflection.java.packageloader.utils.Helper.getFQClassName;
import static com.openpojo.reflection.java.packageloader.utils.Helper.isClass;

/**
 * This is a facade that simplifies reading classes out of a JarFile.
 * This class is also lazy loading classes upon demand, and will not initialize any of the loaded classes.
 *
 * @author oshoukry
 */
public class JarFileReader {

  private JarFile jarFile = null;
  private Set<String> classNames;

  private JarFileReader(String jarFilePath) {
    try {
      jarFile = new JarFile(jarFilePath, true);
      initClassNames();
    } catch (Throwable ignored) {
    }
  }

  private JarFileReader(URL jarURL) {
    try {
      jarFile = ((JarURLConnection) jarURL.openConnection()).getJarFile();
      initClassNames();
    } catch (Throwable ignored) {
    }
  }

  public static JarFileReader getInstance(String jarFilePath) {
    return new JarFileReader(jarFilePath);
  }

  public static JarFileReader getInstance(URL jarURL) {
    return new JarFileReader(jarURL);
  }

  public boolean isValid() {
    return jarFile != null;
  }

  public Map<String, String> getManifestEntries() {
    Map<String, String> manifestEntries = new HashMap<String, String>();
    Manifest manifest;
    try {
      manifest = jarFile.getManifest();
    } catch (IOException e) {
      throw ReflectionException.getInstance("Failed to load Manifest-File for: " + jarFile.getName(), e);
    }

    Attributes mainAttributes = manifest.getMainAttributes();

    for (Attributes.Entry entry : mainAttributes.entrySet()) {
      String key = entry.getKey() == null ? "null" : entry.getKey().toString();
      String value = entry.getValue() == null ? "null" : entry.getValue().toString();
      manifestEntries.put(key, value);
    }
    return manifestEntries;
  }

  public String getManifestEntry(String name) {
    return getManifestEntries().get(name);
  }

  private Set<String> getAllEntries() {
    Set<String> entries = new HashSet<String>();

    ArrayList<JarEntry> jarEntries = Collections.list(jarFile.entries());
    for (JarEntry entry : jarEntries)
      entries.add(entry.getName());

    return entries;
  }

  public Set<Type> getTypesInPackage(String packageName) {
    return Helper.loadClassesFromGivenPackage(classNames, packageName);
  }

  public Set<String> getSubPackagesOfPackage(String packageName) {
    return Helper.getSubPackagesOfPackage(classNames, packageName);
  }

  private void initClassNames() {
    classNames = new HashSet<String>();
    for (String entry : getAllEntries()) {
      if (isClass(entry))
        classNames.add(getFQClassName(entry));
    }
    classNames = Collections.unmodifiableSet(classNames);
  }

  public Set<String> getClassNames() {
    return classNames;
  }

  public static String getJarFileNameFromURLPath(String name) {
    String fileName = "";

    if (null != name && name.indexOf(Java.JAR_FILE_PATH_SEPARATOR) > 0) {
      fileName = name.substring(0, name.indexOf(Java.JAR_FILE_PATH_SEPARATOR));
      try {
        URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(new URL(fileName));
        fileName = urlToFileSystemAdapter.getAsFile().getAbsolutePath();
      } catch (MalformedURLException ignored) {
      }
    }

    return fileName;
  }
}