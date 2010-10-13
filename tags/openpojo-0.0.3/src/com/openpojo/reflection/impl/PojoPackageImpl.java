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

package com.openpojo.reflection.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.utils.PackageHelper;

/**
 * This class represents the abstraction of a JAVA Package as PojoPackageImpl.
 *
 * @author oshoukry
 */
public class PojoPackageImpl implements PojoPackage {

    private final String packageName;

    public PojoPackageImpl(final String packageName) {
        this.packageName = packageName;
    }

    public List<PojoClass> getPojoClasses() {
        return getPojoClasses(null);
    }

    public List<PojoClass> getPojoClasses(final PojoClassFilter filter) {
        List<PojoClass> pojoClasses = new LinkedList<PojoClass>();

        for (Class<?> clazz : PackageHelper.getClasses(packageName)) {
            PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
            if (filter == null || filter.include(pojoClass)) {
                pojoClasses.add(pojoClass);
            }
        }

        return pojoClasses;
    }

    public List<PojoPackage> getPojoSubPackages() {
        List<PojoPackage> pojoPackageSubPackages = new LinkedList<PojoPackage>();

        File directory = PackageHelper.getPackageAsDirectory(packageName);
        for (File entry : directory.listFiles()) {
            if (entry.isDirectory()) {
                String subPackageName = packageName + PackageHelper.PACKAGE_SEPERATOR + entry.getName();
                pojoPackageSubPackages.add(new PojoPackageImpl(subPackageName));
            }
        }

        return pojoPackageSubPackages;
    }

    @Override
    public String toString() {
        return String.format("PojoPackageImpl [packageName=%s]", packageName);
    }
}
