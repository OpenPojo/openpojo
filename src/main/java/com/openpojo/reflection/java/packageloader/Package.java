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

package com.openpojo.reflection.java.packageloader;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

/**
 * @author oshoukry
 */
public final class Package {

  @BusinessKey
  private final String packageName;

  public Package(final String packageName) {
    this.packageName = packageName;
  }

  public String getPackageName() {
    return packageName;
  }

  public boolean isValid() {
    return getPackageLoaders().size() > 0;
  }

  public Set<Type> getTypes() {
    Set<Type> types = new HashSet<Type>();
    for (PackageLoader packageLoader : getPackageLoaders()) {
      for (Type type : packageLoader.getTypes()) {
        types.add(type);
      }
    }
    return types;
  }

  public Set<Package> getSubPackages() {
    Set<Package> subPackages = new HashSet<Package>();
    Set<String> subPackageNames = new HashSet<String>();
    for (PackageLoader packageLoader : getPackageLoaders()) {
      subPackageNames.addAll(packageLoader.getSubPackages());
    }

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

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  public boolean equals(final Object obj) {
    return BusinessIdentity.areEqual(this, obj);
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }

}
