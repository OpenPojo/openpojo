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

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.cache.PojoCache;

/**
 * This is a factory class that builds PojoClassImpl representation given a class.
 * 
 * @author oshoukry
 */
public final class PojoClassFactory {

    /**
     * Create a PojoClass for a given application Class.
     * 
     * @param clazz
     *            Class to introspect.
     * @return
     *         A PojoClass meta representation for the clazz.
     */
    public static PojoClass getPojoClass(final Class<?> clazz) {
        PojoClass pojoClass = PojoCache.getPojoClass(clazz.getName());
        if (pojoClass == null) {
            pojoClass = new PojoClassImpl(clazz, PojoFieldFactory.getPojoFields(clazz));
            PojoCache.addPojoClass(clazz.getName(), pojoClass);
        }
        return pojoClass;
    }

    /**
     * This method returns a list of PojoClasses in a package representation.
     * 
     * @param packageName
     *            Package to introspect (eg. com.mypackage.pojo).
     * @return
     *         A list of PojoClasses.
     */
    public static List<PojoClass> getPojoClasses(final String packageName) {
        return getPojoClasses(packageName, null);
    }

    /**
     * This method returns a list of PojoClasses in a package representation with filtering cababilities.
     * 
     * @param packageName
     *            Package to introspect (eg. com.mypackage.pojo).
     * @param filter
     *            The filter to apply to the list of PojoClasses.
     * @return
     *         A list of PojoClasses.
     */
    public static List<PojoClass> getPojoClasses(final String packageName, final PojoClassFilter filter) {
        return new PojoPackageImpl(packageName).getPojoClasses(filter);
    }

    /**
     * This method enumerates all classes in a package path.
     * This method will enumerate using the class loader, so if you're tests live in the same package as your code, make
     * sure you pass in a filter that can weed those out for testing.
     * 
     * @param packageName
     *            The package name in question.
     * @param filter
     *            The filter to use.
     * @return
     *         List of PojoClasses enumerated.
     */
    public static List<PojoClass> getPojoClassesRecursively(final String packageName, final PojoClassFilter filter) {
        List<PojoClass> pojoClasses = new LinkedList<PojoClass>();

        PojoPackage pojoPackage = new PojoPackageImpl(packageName);
        List<PojoPackage> packages = pojoPackage.getPojoSubPackages();
        for (int counter = 0; counter < packages.size(); counter++) {
            PojoPackage entry = packages.get(counter);

            // add newly discovered paths
            packages.addAll(entry.getPojoSubPackages());

            // add all classes in current path
            for (PojoClass pojoClass : entry.getPojoClasses(filter)) {
                pojoClasses.add(pojoClass);
            }
        }
        return pojoClasses;
    }

    /**
     * Return a list of classes that implement/extend a given type
     * @param packageName
     *          Parent package to recurse through.
     * @param type
     *          Inheritence type (can be interface / abstract class or class).
     * @param filter
     *          A filter to use for PojoClasses.
     * @return
     *          List of all Pojo's that extend type.
     */
    public static List<PojoClass> enumerateClassesByExtendingType(final String packageName,
            final Class<?> type, final PojoClassFilter filter) {
        FilterBasedOnInheritence inheritencefilter = new FilterBasedOnInheritence(type, filter);
        return getPojoClassesRecursively(packageName, inheritencefilter);
    }
}
