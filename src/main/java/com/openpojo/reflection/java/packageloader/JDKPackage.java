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
import java.util.Set;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.JDKType;

/**
 * @author oshoukry
 */
public final class JDKPackage {
    private final String packageName;
    private final Set<URL> resources;
    private final Set<JDKPackageLoader> packageLoader;

    public JDKPackage(final String packageName) {
        this.packageName = packageName;
        try {
            resources = JDKPackageLoader.getThreadResources(packageName);
            packageLoader = null;
//
        } catch (IOException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        } catch (URISyntaxException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        }
    }

    public Set<JDKType> getClasses() {
        return null;
    }
}
