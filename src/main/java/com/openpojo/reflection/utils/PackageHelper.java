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
package com.openpojo.reflection.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * This is a utility class to help enumerate and generate PojoClasses for specific pacakge.
 *
 * @author oshoukry
 */
public final class PackageHelper {
    public static final char PATH_SEPERATOR = '/';

    private static final String CLASS_SUFFIX = ".class";

    /**
     * Get a list of all classes in the package.
     *
     * @return
     *         List of all classes in the package.
     */
    public static List<Class<?>> getClasses(final String packageName) {
        List<Class<?>> classes = new LinkedList<Class<?>>();

        List<File> paths = getPackageDirectories(packageName);

        for (File path : paths) {
            for (File entry : path.listFiles()) {
                if (isClass(entry.getName())) {
                    Class<?> clazz = getPathEntryAsClass(packageName, entry.getName());
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }

    /**
     * Turn a java package into a directory listing.
     *
     * @param packageName
     *            String path of the package to turn into a directoy.
     * @return
     *         Return a file representation of the directory.
     */
    public static List<File> getPackageDirectories(final String packageName) {
        List<String> paths = getFullyQualifiedPathsForPackage(packageName);

        List<File> directories = new LinkedList<File>();
        for (String path : paths) {
            directories.add(new File(path));
        }
        return directories;
    }

    private static Class<?> getPathEntryAsClass(final String packageName, final String entry) {
        return loadClass(getClassLoader(), getFQClassName(packageName, entry));
    }

    private static List<String> getFullyQualifiedPathsForPackage(final String packageName) {
        String packageAsPath = convertPackageToPath(packageName);
        List<URL> resources = getResources(packageAsPath);
        if (resources.size() == 0) {
            throw ReflectionException.getInstance(String.format("No such package [%s], path [%s] not found",
                    packageName, packageAsPath));
        }
        List<String> paths = new LinkedList<String>();
        try {
            // work around because of this
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4466485
            URI uri;
            for (URL resource : resources) {
                uri = new URI(resource.toString());
                paths.add(uri.getPath());
            }
            return paths;
        } catch (URISyntaxException e) {
            throw ReflectionException.getInstance(e);
        }
    }

    private static Class<?> loadClass(final ClassLoader classLoader, final String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw ReflectionException.getInstance(e);
        }
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private static List<URL> getResources(final String path) {
        List<URL> resources = new LinkedList<URL>();
        try {
            Enumeration<URL> enumeration = ClassLoader.getSystemResources(path);
            while (enumeration.hasMoreElements()) {
                resources.add(enumeration.nextElement());
            }
        } catch (IOException e) {
            throw ReflectionException.getInstance(e);
        }
        return resources;
    }

    private static String convertPackageToPath(final String packageName) {
        return packageName.replace(PojoPackage.PACKAGE_DELIMETER, PATH_SEPERATOR);
    }

    /**
     * Returns true if the string refers to a class entry (i.e. ends with .class).
     *
     * @param entry
     *            The class name.
     * @return
     *         True if the className ends with CLASS_SUFFIX.
     */
    private static boolean isClass(final String entry) {
        if (entry.endsWith(CLASS_SUFFIX)) {
            return true;
        }
        return false;
    }

    /**
     * Return a fully qualified class name given the fileEntry for the classname, and package this helper represents.
     *
     * @param fileEntry
     *            The classname to qualify.
     * @return
     *         The fully qualifed package name and classname.
     */
    private static String getFQClassName(final String packageName, final String fileEntry) {
        String className = packageName + PojoPackage.PACKAGE_DELIMETER
                + fileEntry.substring(0, fileEntry.length() - CLASS_SUFFIX.length());
        return className;
    }
}
