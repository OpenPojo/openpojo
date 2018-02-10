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

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.packageloader.impl.FilePackageLoader;
import com.openpojo.reflection.java.packageloader.impl.JARPackageLoader;

/**
 * @author oshoukry
 */
public abstract class PackageLoader {

  protected final Logger logger;

  @BusinessKey
  protected final URL packageURL;

  @BusinessKey
  protected final String packageName;

  public PackageLoader(final URL packageURL, final String packageName) {
    this.packageURL = packageURL;
    this.packageName = packageName;
    logger = LoggerFactory.getLogger(this.getClass());
  }

  public abstract Set<Type> getTypes();

  public abstract Set<String> getSubPackages();

  public static Set<URL> getThreadResources(final String path) {
    String normalizedPath = fromJDKPackageToJDKPath(path);
    Enumeration<URL> urls;
    try {
      urls = getThreadClassLoader().getResources(normalizedPath);
    } catch (IOException e) {
      throw ReflectionException.getInstance(MessageFormatter.format("Failed to getThreadResources for path[{0}]", path), e);
    }
    Set<URL> returnURLs = new HashSet<URL>();
    while (urls.hasMoreElements()) {
      returnURLs.add(urls.nextElement());
    }
    return returnURLs;
  }

  protected static PackageLoader getPackageLoaderByURL(final URL packageURL, final String packageName) {
    if (packageURL.getProtocol().equalsIgnoreCase("jar")) {
      return new JARPackageLoader(packageURL, packageName);
    }
    if (packageURL.getProtocol().equalsIgnoreCase("file")) {
      return new FilePackageLoader(packageURL, packageName);
    }
    throw new IllegalArgumentException("Unknown package loader protocol: " + packageURL.getProtocol());
  }

  private static ClassLoader getThreadClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  private static String fromJDKPackageToJDKPath(final String path) {
    return path.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER);
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }
}