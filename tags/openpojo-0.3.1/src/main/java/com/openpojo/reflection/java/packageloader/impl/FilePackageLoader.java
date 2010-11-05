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

import java.net.URL;
import java.util.Set;

import com.openpojo.reflection.java.JDKType;
import com.openpojo.reflection.java.packageloader.JDKPackageLoader;

/**
 * @author oshoukry
 */
public final class FilePackageLoader extends JDKPackageLoader {

    public FilePackageLoader(final URL packageURL, final String packageName) {
        super(packageURL, packageName);
    }

    @Override
    public Set<JDKType> getTypes() {
        // TODO Auto-generated method stub
        throw new IllegalStateException("Unimplemented!!");
    }

    @Override
    public Set<JDKPackageLoader> getSubPackages() {
        // TODO Auto-generated method stub
        throw new IllegalStateException("Unimplemented!!");
    }

}
