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

package com.openpojo.reflection.java.packageloader.utils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class Helper {

  public static boolean isClass(String entry) {
    return entry != null && entry.endsWith(Java.CLASS_EXTENSION);
  }

  public static String getFQClassName(String entry) {
    String fullyQualifiedName = entry.substring(0, entry.length() - Java.CLASS_EXTENSION.length());
    return fullyQualifiedName.replace(Java.PATH_DELIMITER, Java.PACKAGE_DELIMITER);
  }

  public static Set<Type> loadClassesFromGivenPackage(Set<String> classNames, String packageName) {
    Set<Type> entries = new HashSet<Type>();
    for (String entry : classNames) {
      int endIndex = entry.lastIndexOf(Java.PACKAGE_DELIMITER);

      String entryPackageName = "";

      if (endIndex > 0)
        entryPackageName = entry.substring(0, endIndex);

      if (entryPackageName.equals(packageName)) {
        Type entryClass = ClassUtil.loadClass(entry, false);
        if (entryClass != null)
          entries.add(entryClass);
      }
    }
    return entries;
  }

  public static Set<String> getSubPackagesOfPackage(Set<String> classNames, String packageName) {
    Set<String> subPackages = new HashSet<String>();
    for (String entry : classNames) {
      int endIndex = entry.lastIndexOf(Java.PACKAGE_DELIMITER);
      String typeClassPackageName;
      if (endIndex > 0) {
        typeClassPackageName = entry.substring(0, endIndex);
        String directSubPackageName = getDirectSubPackageName(packageName, typeClassPackageName);
        if (directSubPackageName != null)
          subPackages.add(directSubPackageName);
      }
    }
    return subPackages;
  }

  /**
   * This method breaks up a package path into its elements returning the first sub-element only.
   * For example, if packageName is "com" and the JAR file has only one class
   * "com.openpojo.reflection.SomeClass.class", then the return will be set to "com.openpojo".
   *
   * @param parentPackageName
   *     The reference package name.
   * @param subPackageName
   *     The subpackage name.
   * @return A first sub level bellow packageName.
   */
  static String getDirectSubPackageName(final String parentPackageName, final String subPackageName) {
    String parentPackageNameAsPath = "";

    if (parentPackageName != null && parentPackageName.length() > 0)
      parentPackageNameAsPath = parentPackageName + Java.PACKAGE_DELIMITER;

    if (subPackageName.startsWith(parentPackageNameAsPath) && subPackageName.length() > parentPackageNameAsPath.length()) {
      String[] subPackageTokens;
      subPackageTokens = subPackageName.substring(parentPackageNameAsPath.length()).split("\\" + Java.PACKAGE_DELIMITER);
      if (subPackageTokens.length > 0) {
        return parentPackageNameAsPath + subPackageTokens[0];
      }
    }
    return null;
  }

  private Helper() {
    throw new UnsupportedOperationException(Helper.class.getName() +  " should not be constructed!");
  }
}