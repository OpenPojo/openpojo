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
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.JDKType;
import com.openpojo.reflection.java.packageloader.JDKPackageLoader;

/**
 * @author oshoukry
 */
public final class JARPackageLoader extends JDKPackageLoader {

    public JARPackageLoader(final URL packageURL, final String packageName) {
        super(packageURL, packageName);
        throw new IllegalStateException("Unimplemented!!");
    }

    @Override
    public Set<JDKType> getTypes() {
        Set<JDKType> types = new HashSet<JDKType>();
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
            if (isClass(entryName)) {
                String className = fromJDKPathToJDKPackage(stripClassExtension(e.getName()));
                try {
                    Class<?> classEntry = Class.forName(className);
                    if (classEntry.getPackage().getName().equals(packageName)) {
                        types.add(new JDKType(classEntry));
                    }
                } catch (Throwable t) {

                }
            }
        }
        return types;
    }

    @Override
    public Set<JDKPackageLoader> getSubPackages() {
        // TODO Auto-generated method stub
        throw new IllegalStateException("Unimplemented!!");
    }

}
