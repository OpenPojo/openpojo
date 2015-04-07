/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.reflection.java.packageloader.Package;

/**
 * This class represents the abstraction of a JAVA Package as PojoPackageImpl.
 *
 * @author oshoukry
 */
class PojoPackageImpl implements PojoPackage {

    private final String packageName;
    private final PojoClass packageInfoPojoClass;
    private final Package jdkPackage;

    public String getName() {
        return packageName;
    }

    public PojoPackageImpl(final String packageName) {
        if (packageName == null) {
            throw new IllegalArgumentException("PackageName can not be null");
        }

        this.packageName = packageName;

        jdkPackage = new Package(packageName);
        if (!isValid()) {
            throw ReflectionException.getInstance(MessageFormatter.format("Package [{0}] is not valid", packageName));
        }

        Class<?> infoClass = ClassUtil.loadClass(packageName + Java.PACKAGE_DELIMETER + Java.PACKAGE_INFO);

        if (infoClass != null) {
            packageInfoPojoClass = PojoClassFactory.getPojoClass(infoClass);
        } else {
            packageInfoPojoClass = null;
        }
    }

    private boolean isValid() {
        return jdkPackage.isValid();
    }

    public List<PojoClass> getPojoClasses() {
        return getPojoClasses(null);
    }

    public List<PojoClass> getPojoClasses(final PojoClassFilter filter) {
        List<PojoClass> pojoClasses = new LinkedList<PojoClass>();
        for (Type type : jdkPackage.getTypes()) {
            PojoClass pojoClass = PojoClassFactory.getPojoClass((Class<?>) type);
            if (filter == null || filter.include(pojoClass)) {
                pojoClasses.add(pojoClass);
            }
        }

        return pojoClasses;
    }

    public List<PojoPackage> getPojoSubPackages() {
        List<PojoPackage> pojoPackages = new LinkedList<PojoPackage>();
        for (Package entry : jdkPackage.getSubPackages()) {
            pojoPackages.add(new PojoPackageImpl(entry.getPackageName()));
        }
        return pojoPackages;
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        if (packageInfoPojoClass == null) {
            return null;
        }
        return packageInfoPojoClass.getAnnotation(annotationClass);
    }

    public List<? extends Annotation> getAnnotations() {
        if (packageInfoPojoClass == null) {
            return Collections.emptyList();
        }
        return packageInfoPojoClass.getAnnotations();
    }

    @Override
    public String toString() {
        return String.format("PojoPackageImpl [packageName=%s]", packageName);
    }

}
