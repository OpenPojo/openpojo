/*
 * Copyright (c) 2010-2017 Osman Shoukry
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

package com.openpojo.reflection.java.packageloader.boot;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.packageloader.reader.FileSystemReader;
import com.openpojo.reflection.java.packageloader.reader.JarFileReader;
import com.openpojo.reflection.java.packageloader.utils.Helper;

/**
 * @author oshoukry
 */
public class JavaBootClassLoader {
  private static final String DEFAULT_BOOT_CLASS_PATH_PROPERTY_NAME = "sun.boot.class.path";
  private static final JavaBootClassLoader INSTANCE = new JavaBootClassLoader();

  private final Set<String> bootClassPathPropertyNames = new HashSet<String>();
  private Set<String> bootClassNames = new HashSet<String>();

  private JavaBootClassLoader() {
    bootClassPathPropertyNames.add(DEFAULT_BOOT_CLASS_PATH_PROPERTY_NAME);
    loadBootClassNames();
  }

  public static JavaBootClassLoader getInstance() {
    return INSTANCE;
  }

  public Set<Type> getTypesInPackage(String packageName) {
    return Helper.loadClassesFromGivenPackage(bootClassNames, packageName);
  }

  public Set<String> getClassPathKeys() {
    return Collections.unmodifiableSet(bootClassPathPropertyNames);
  }

  public Set<String> getBootClassNames() {
    return Collections.unmodifiableSet(bootClassNames);
  }

  private void loadBootClassNames() {
    for (String name : bootClassPathPropertyNames) {
      String envProperty = System.getProperty(name);
      String[] entries = envProperty.split(Java.CLASSPATH_DELIMITER);
      for (String entry : entries) {
        LoggerFactory.getLogger(this.getClass()).info("loading classes from: " + entry);

        File fileEntry = new File(entry);
        if (fileEntry.isDirectory())
          bootClassNames.addAll(FileSystemReader.getInstance(fileEntry).getClassNames());
        else {
          JarFileReader jarFileReader = JarFileReader.getInstance(entry);
          if (jarFileReader.isValid())
            bootClassNames.addAll(jarFileReader.getClassNames());
          else
            LoggerFactory.getLogger(this.getClass()).warn("Failed to load entries from: " + entry);
        }
      }
    }
  }

  public boolean hasPackage(String packageName) {
    for (String entry : bootClassNames)
      if (entry.startsWith(packageName + Java.PACKAGE_DELIMITER))
        return true;
    return false;
  }

  public Set<String> getSubPackagesFor(String packageName) {
    return Helper.getSubPackagesOfPackage(bootClassNames, packageName);
  }
}