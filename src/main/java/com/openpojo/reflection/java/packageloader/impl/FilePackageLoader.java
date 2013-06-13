/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.reflection.java.packageloader.impl;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.packageloader.PackageLoader;

/**
 * @author oshoukry
 */
public final class FilePackageLoader extends PackageLoader {

    public FilePackageLoader(final URL packageURL, final String packageName) {
        super(packageURL, packageName);
    }

    @Override
    public Set<Type> getTypes() {

        final Set<Type> types = new HashSet<Type>();

        for (final File entry : getEntries()) {
            try {
                final String className = fromJDKPathToJDKPackage(packageName) + JDKPACKAGE_DELIMETER + entry.getName();
                final Class<?> classEntry = getAsClass(className);
                if (classEntry != null) {
                    types.add(classEntry);
                }
            } catch (final ClassNotFoundException classNotFoundException) { // entry wasn't a class
            }
        }
        return types;
    }

    @Override
    public Set<String> getSubPackages() {
        final Set<String> subPaths = new HashSet<String>();
        for (final File file : getEntries()) {
            if (file.isDirectory()) {
                subPaths.add(fromJDKPathToJDKPackage(packageName) + JDKPACKAGE_DELIMETER + file.getName());
            }
        }
        return subPaths;
    }

    private File[] getEntries() {
        File directory;
        try {
            // convert toURI to decode %20 for spaces, etc.
            final URI uri = packageURL.toURI();

            // to handle windows paths i.e. //host_server/path/class, need a way to put the authority section back in
            // the path
            if (uri.getAuthority() != null) {
                directory = new File("//" + uri.getAuthority() + uri.getPath());
            } else {
                directory = new File(uri.getPath().toString());
            }
        } catch (final URISyntaxException uriSyntaxException) {
            throw ReflectionException.getInstance(uriSyntaxException.getMessage(), uriSyntaxException);
        }
        return directory.listFiles();
    }

}
