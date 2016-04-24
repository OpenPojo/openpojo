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

package com.openpojo.reflection.java.packageloader.reader;

import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.load.ClassUtil;

import static com.openpojo.reflection.java.packageloader.utils.Helper.getDirectSubPackageName;
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
      jarFile = ((JarURLConnection)jarURL.openConnection()).getJarFile();
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

  public Set<String> getAllEntries() {
    Set<String> entries = new HashSet<String>();

    ArrayList<JarEntry> jarEntries = Collections.list(jarFile.entries());
    for (JarEntry entry : jarEntries)
      entries.add(entry.getName());

    return entries;
  }

  public Set<Type> getTypesInPackage(String packageName) {
    Set<Type> entries = new HashSet<Type>();
    for (String entry : classNames) {
      String entryPackageName = entry.substring(0, (entry.lastIndexOf(Java.PACKAGE_DELIMITER)));
      if (entryPackageName.equals(packageName)) {
        Type entryClass = ClassUtil.loadClass(entry, false);
        if (entryClass != null)
          entries.add(entryClass);
      }
    }
    return entries;
  }

  public Set<String> getSubPackagesOfPackage(String packageName) {
    Set<String> subPackages = new HashSet<String>();
    for (String entry : classNames) {
      String typeClassPackageName = entry.substring(0, (entry.lastIndexOf(Java.PACKAGE_DELIMITER)));
      String directSubPackageName = getDirectSubPackageName(packageName, typeClassPackageName);
      if (directSubPackageName != null) {
        subPackages.add(directSubPackageName);
      }
    }
    return subPackages;
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
}