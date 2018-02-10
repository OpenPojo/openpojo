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

package com.openpojo.reflection.java.packageloader;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.reflection.java.packageloader.env.JavaClassPathClassLoader;

/**
 * @author oshoukry
 */
public final class Package {
  private static final JavaClassPathClassLoader JAVA_CLASSPATH_CLASS_LOADER = JavaClassPathClassLoader.getInstance();

  @BusinessKey
  private final String packageName;

  public Package(final String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }

  public boolean isValid() {
    return getPackageLoaders().size() > 0 || JAVA_CLASSPATH_CLASS_LOADER.hasPackage(packageName);
  }

  public Set<Type> getTypes() {
    Set<Type> types = new HashSet<Type>();
    for (PackageLoader packageLoader : getPackageLoaders()) {
      for (Type type : packageLoader.getTypes()) {
        types.add(type);
      }
    }

    types.addAll(JAVA_CLASSPATH_CLASS_LOADER.getTypesInPackage(packageName));
    return types;
  }

  public Set<Package> getSubPackages() {
    Set<Package> subPackages = new HashSet<Package>();
    Set<String> subPackageNames = new HashSet<String>();
    for (PackageLoader packageLoader : getPackageLoaders()) {
      subPackageNames.addAll(packageLoader.getSubPackages());
    }

    Set<String> subPackagesFor = JAVA_CLASSPATH_CLASS_LOADER.getSubPackagesFor(packageName);
    subPackageNames.addAll(subPackagesFor);

    for (String packageName : subPackageNames) {
      subPackages.add(new Package(packageName));
    }

    return subPackages;
  }

  private Set<PackageLoader> getPackageLoaders() {
    Set<PackageLoader> packageLoaders = new HashSet<PackageLoader>();

    Set<URL> resources = PackageLoader.getThreadResources(packageName);
    for (URL resource : resources) {
      packageLoaders.add(PackageLoader.getPackageLoaderByURL(resource, packageName));
    }

    return packageLoaders;
  }

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }

  @Override
  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  public boolean equals(final Object obj) {
    return BusinessIdentity.areEqual(this, obj);
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }

}
