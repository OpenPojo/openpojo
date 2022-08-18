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

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.reflection.java.packageloader.impl.URLToFileSystemAdapter;
import com.openpojo.reflection.java.packageloader.utils.Helper;

/**
 * This is a facade that simplifies reading classes out of a JarFile.
 * This class is also lazy loading classes upon demand, and will not initialize any of the loaded classes.
 *
 * @author oshoukry
 */
public class FileSystemReader {
  private final File directory;

  private FileSystemReader(File directory) {
    this.directory = directory;
  }

  public static FileSystemReader getInstance(URL url) {
      return new FileSystemReader(getDirectory(url));
  }

  public static FileSystemReader getInstance(File directory) {
    return new FileSystemReader(directory);
  }

  public Set<Type> getTypesInPackage(String packageName) {

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

  public Set<String> getSubPackagesOfPackage(String packageName) {
    final Set<String> subPaths = new HashSet<String>();
    for (final File file : getEntries()) {
      if (file.isDirectory()) {
        if (packageName != null && packageName.length() > 0)
          subPaths.add(fromJDKPathToJDKPackage(packageName) + Java.PACKAGE_DELIMITER + file.getName());
        else
          subPaths.add(file.getName());
      }
    }
    return subPaths;
  }

  private static File getDirectory(URL url) {
    // convert toURI to decode %20 for spaces, etc.
    URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(url);

    File directory = urlToFileSystemAdapter.getAsFile();

    if (!directory.exists() || !directory.isDirectory())
      throw ReflectionException.getInstance("Failed to retrieve entries in path: [" + directory.getAbsolutePath() + "] " +
          "created from URI: [" + urlToFileSystemAdapter.getAsURI() + "].  Please report this issue @ http://openpojo.com");

    return directory;
  }

  private File[] getEntries() {
    return directory.listFiles();
  }

  private Class<?> getAsClass(final String entry) {
    if (Helper.isClass(entry)) {
      String className = Helper.getFQClassName(entry);

      return ClassUtil.loadClass(className, false);
    }
    return null;
  }

  private static String fromJDKPathToJDKPackage(final String path) {
    return path.replace(Java.PATH_DELIMITER, Java.PACKAGE_DELIMITER);
  }

}