/*
 * Copyright (c) 2010-2014 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.reflection.java.packageloader.utils;

import com.openpojo.reflection.java.packageloader.PackageLoader;

/**
 * @author oshoukry
 */
public class PackageNameHelper {

    /**
     * This method breaks up a package path into its elements returning the first sub-element only.
     * For example, if packageName is "com" and the JAR file has only one class
     * "com.openpojo.reflection.SomeClass.class", then the return will be set to "com.openpojo".
     *
     * @param parentPackageName
     *            The reference package name.
     * @param subPackageName
     *            The subpackage name.
     * @return
     *         A first sub level bellow packageName.
     */
    public static String getDirectSubPackageName(final String parentPackageName, final String subPackageName) {
        String parentPackageNameAsPath = parentPackageName + PackageLoader.JDKPACKAGE_DELIMETER;
        if (subPackageName.startsWith(parentPackageNameAsPath) && subPackageName.length() > parentPackageNameAsPath.length()) {
            String[] subPackageTokens;
            subPackageTokens = subPackageName.substring(parentPackageNameAsPath.length()).split("\\" + PackageLoader
                    .JDKPACKAGE_DELIMETER);
            if (subPackageTokens.length > 0) {
                return parentPackageName + PackageLoader.JDKPACKAGE_DELIMETER + subPackageTokens[0];
            }
        }
        return null;
    }
}
