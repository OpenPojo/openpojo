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

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.packageloader.PackageLoader;
import com.openpojo.reflection.java.packageloader.utils.PackageNameHelper;

/**
 * @author oshoukry
 */
public final class JARPackageLoader extends PackageLoader {

  public JARPackageLoader(final URL packageURL, final String packageName) {
    super(packageURL, packageName);
  }

  @Override
  public Set<Type> getTypes() {
    Set<Type> types = new LinkedHashSet<Type>();
    for (Type type : getAllJarTypes()) {
      Class<?> classEntry = (Class<?>) type;
      if (classEntry.getPackage().getName().equals(packageName)) {
        types.add(type);
      }
    }
    return types;
  }

  @Override
  public Set<String> getSubPackages() {
    Set<String> subPackages = new LinkedHashSet<String>();

    Set<Type> types = getAllJarTypes();
    for (Type type : types) {
      Class<?> typeClass = (Class<?>) type;
      String typeClassPackageName = typeClass.getPackage().getName();
      String directSubPackageName = PackageNameHelper.getDirectSubPackageName(packageName, typeClassPackageName);
      if (directSubPackageName != null) {
        subPackages.add(directSubPackageName);
      }
    }
    return subPackages;
  }

  private Set<Type> getAllJarTypes() {
    Set<Type> types = new LinkedHashSet<Type>();
    JarURLConnection conn;
    JarFile jar;
    try {
      conn = (JarURLConnection) packageURL.openConnection();
      jar = conn.getJarFile();
    } catch (IOException e) {
      throw ReflectionException.getInstance(e.getMessage(), e);
    }
    for (JarEntry e : Collections.list(jar.entries())) {
      String entryName = e.getName();
      Class<?> classEntry = getAsClass(entryName);
      if (classEntry != null) {
        types.add(classEntry);
      }
    }
    return types;
  }

}
