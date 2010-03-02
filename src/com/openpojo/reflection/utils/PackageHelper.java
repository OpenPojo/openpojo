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
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.exception.ReflectionException;

/**
 * This is a utility class to help enumerate and generate PojoClasses for specific pacakge.
 * 
 * @author oshoukry
 */
public final class PackageHelper {
    public static final char PATH_SEPERATOR = '/';
    public static final char PACKAGE_SEPERATOR = '.';

    /**
     * Get a list of all classes in the package.
     * 
     * @return
     *         List of all classes in the package.
     */
    public static List<Class<?>> getClasses(final String packageName) {
        List<Class<?>> classes = new LinkedList<Class<?>>();

        // Get a File object for the package
        File directory;
        directory = getPackageAsDirectory(packageName);

        // Get the list of the files contained in the package
        String[] files = directory.list();

        for (int i = 0; i < files.length; i++) {
            if (isClass(files[i])) {
                try {
                    classes.add(Class.forName(getFQClassName(packageName, files[i])));
                } catch (ClassNotFoundException e) {
                    // this should never happen since we get the entry from directory listing.
                    throw new ReflectionException(e);
                }
            }
        }

        return classes;
    }

    /**
     * Turn a java package into a directory listing.
     * 
     * @param packageName
     *          String path of the package to turn into a directoy.
     * @return
     *          Return a file representation of the directory.
     */
    public static File getPackageAsDirectory(final String packageName) {

        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ReflectionException("Can't get class loader.");
        }

        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ReflectionException("No resource for " + path);
        }

        File directory = null;
        directory = new File(resource.getFile());
        if (!directory.exists()) {
            throw new ReflectionException(packageName + " does not appear to be a valid package");
        }

        return directory;
    }

    private static final String CLASS_SUFFIX = ".class";

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
    private static String getFQClassName(final String packagename, final String fileEntry) {
        String className = packagename + '.' + fileEntry.substring(0, fileEntry.length() - CLASS_SUFFIX.length());
        return className;
    }
}
