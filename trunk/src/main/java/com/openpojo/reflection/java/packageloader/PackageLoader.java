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
            throw ReflectionException.getInstance(MessageFormatter.format("Failed to getThreadResources for path[{0}]",
                                                                          path), e);
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

    public static final Character JDKPACKAGE_DELIMETER = '.';
    public static final Character JDKPATH_DELIMETER = '/';
    public static final String CLASS_EXTENSION = ".class";

    protected final Class<?> getAsClass(final String entry) throws ClassNotFoundException {
        if (isClass(entry)) {
            String className = stripClassExtension(fromJDKPathToJDKPackage(entry));
            logger.trace("loading class [{0}]", className);
            try {
                return Class.forName(className, false, getThreadClassLoader());
            } catch (LinkageError linkageError) { // entry depends on class that wasn't found.
                logger.debug("Class [{0}] has unmet dependency, loading skipped [{1}]", entry, linkageError);
            }
        }
        return null;
    }

    protected static String fromJDKPackageToJDKPath(final String path) {
        return path.replace(JDKPACKAGE_DELIMETER, JDKPATH_DELIMETER);
    }

    protected static String fromJDKPathToJDKPackage(final String path) {
        return path.replace(JDKPATH_DELIMETER, JDKPACKAGE_DELIMETER);
    }

    private String stripClassExtension(final String path) {
        return path.substring(0, path.length() - CLASS_EXTENSION.length());
    }

    private boolean isClass(final String path) {
        return path.endsWith(CLASS_EXTENSION);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }
}
