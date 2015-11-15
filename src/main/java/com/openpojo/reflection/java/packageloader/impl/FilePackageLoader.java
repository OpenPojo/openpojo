/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection.java.packageloader.impl;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.packageloader.PackageLoader;

/**
 * @author oshoukry
 */
public final class FilePackageLoader extends PackageLoader {

  public FilePackageLoader(final URL packageURL, final String packageName) {
    super(packageURL, packageName);
  }

  @Override
  public Set<Type> getTypes() {

    final Set<Type> types = new HashSet<Type>();

    for (final File entry : getEntries()) {
      final String className = fromJDKPathToJDKPackage(packageName) + Java.PACKAGE_DELIMITER + entry.getName();
      final Class<?> classEntry = getAsClass(className);
      if (classEntry != null) {
        types.add(classEntry);
      }
    }
    return types;
  }

  @Override
  public Set<String> getSubPackages() {
    final Set<String> subPaths = new HashSet<String>();
    for (final File file : getEntries()) {
      if (file.isDirectory()) {
        subPaths.add(fromJDKPathToJDKPackage(packageName) + Java.PACKAGE_DELIMITER + file.getName());
      }
    }
    return subPaths;
  }

  private File[] getEntries() {
    // convert toURI to decode %20 for spaces, etc.
    URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(packageURL);

    File directory = urlToFileSystemAdapter.getAsFile();
    File[] fileList = directory.listFiles();

    if (fileList == null) {
      throw ReflectionException.getInstance("Failed to retrieve entries in path: [" + directory.getAbsolutePath() + "] " +
          "created from URI: [" + urlToFileSystemAdapter.getAsURI() + "].  Please report this issue @ http://openpojo.com");
    }
    return fileList;
  }


}
