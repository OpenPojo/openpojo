/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
            if (classEntry.getPackage().equals(packageName)) {
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
            String directSubPackageName = getDirectSubPackageName(typeClassPackageName);
            if (directSubPackageName != null) {
                subPackages.add(directSubPackageName);
            }
        }
        return subPackages;
    }

    private Set<Type> getAllJarTypes() {
        Set<Type> types = new LinkedHashSet<Type>();
        JarURLConnection conn;
        JarFile jar = null;
        try {
            conn = (JarURLConnection) packageURL.openConnection();
            jar = conn.getJarFile();
        } catch (IOException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        }
        for (JarEntry e : Collections.list(jar.entries())) {
            String entryName = e.getName();
            try {
                Class<?> classEntry = getAsClass(entryName);
                if (classEntry != null) {
                    types.add(classEntry);
                }
            } catch (ClassNotFoundException classNotFoundException) { // entry wasn't a class
            }
        }
        return types;
    }

    /**
     * This method breaks up a package path into its elements.
     * For example, if packageName is set to "com" and the JAR file has only one class
     * "com.openpojo.reflection.somclass", we need to state that there are "com.openpojo", "com.openpojo.reflection"
     * as two distinct subPackages. The JAR file returns a full list of files, no paths, so this is the way to break it
     * up.
     *
     * @param subPackageName
     *            The subpackage name.
     * @return
     *         A Set of unique sub path entries.
     */
    private String getDirectSubPackageName(final String subPackageName) {
        if (subPackageName.startsWith(packageName) && !packageName.equals(subPackageName)) {
            String[] subPackageTokens = null;
            subPackageTokens = subPackageName.substring(packageName.length() + 1).split("\\.");
            if (subPackageTokens.length > 0) {
                return packageName + JDKPACKAGE_DELIMETER + subPackageTokens[0];
            }
        }
        return null;
    }
}
