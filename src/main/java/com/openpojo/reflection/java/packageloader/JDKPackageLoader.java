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
package com.openpojo.reflection.java.packageloader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.reflection.java.JDKType;

/**
 * @author oshoukry
 */
public abstract class JDKPackageLoader {

    @BusinessKey
    protected final URL packageURL;

    @BusinessKey(caseSensitive = false)
    protected final String packageName;

    public JDKPackageLoader(final URL packageURL, final String packageName) {
        this.packageURL = packageURL;
        this.packageName = packageName;
    }

    public abstract Set<JDKType> getTypes();

    public abstract Set<JDKPackageLoader> getSubPackages();

    public static Set<URL> getThreadResources(final String path) throws IOException, URISyntaxException {
        String normalizedPath = fromJDKPackageToJDKPath(path);
        Enumeration<URL> urls = getThreadClassLoader().getResources(normalizedPath);
        Set<URL> returnURLs = new HashSet<URL>();
        while (urls.hasMoreElements()) {
            returnURLs.add(urls.nextElement());
        }
        return returnURLs;
    }

    private static ClassLoader getThreadClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static final Character JDKPACKAGE_DELIMETER = '.';
    public static final Character JDKPATH_DELIMETER = '/';
    public static final String CLASS_EXTENSION = ".class";

    protected final static String fromJDKPackageToJDKPath(final String path) {
        return path.replace(JDKPACKAGE_DELIMETER, JDKPATH_DELIMETER);
    }

    protected final static String fromJDKPathToJDKPackage(final String path) {
        return path.replace(JDKPATH_DELIMETER, JDKPACKAGE_DELIMETER);
    }

    protected String stripClassExtension(final String path) {
        return path.substring(0, path.length() - CLASS_EXTENSION.length());
    }

    protected boolean isClass(final String path) {
        return path.endsWith(CLASS_EXTENSION);
    }
}
