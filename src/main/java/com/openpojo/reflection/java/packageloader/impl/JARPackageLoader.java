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

package com.openpojo.reflection.java.packageloader.impl;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Set;

import com.openpojo.reflection.java.packageloader.PackageLoader;
import com.openpojo.reflection.java.packageloader.reader.JarFileReader;

/**
 * @author oshoukry
 */
public final class JARPackageLoader extends PackageLoader {
  private final JarFileReader jarFileReader;

  public JARPackageLoader(final URL packageURL, final String packageName) {
    super(packageURL, packageName);
    jarFileReader = JarFileReader.getInstance(packageURL);
  }

  @Override
  public Set<Type> getTypes() {
    return jarFileReader.getTypesInPackage(packageName);
  }

  @Override
  public Set<String> getSubPackages() {
    return jarFileReader.getSubPackagesOfPackage(packageName);
  }
}