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

package com.openpojo.reflection;

import java.util.List;

/**
 * This class encapsulates the meta data definition of a Java Package.
 *
 * @author oshoukry
 */
public interface PojoPackage extends PojoElement {
    public static final String PACKAGE_INFO = "package-info";
    public static final char PACKAGE_DELIMETER = '.';

    /**
     * Get all PojoClasses in current package.
     *
     * @return
     *         Return a list of all classes as PojoClasses in a given package.
     */
    public List<PojoClass> getPojoClasses();

    /**
     * Get all Classes in this PojoPackageImpl using defined filter.
     *
     * @param filter
     *            Filter to apply while enumerating;
     * @return
     *         List of PojoClasses in package.
     */
    public List<PojoClass> getPojoClasses(final PojoClassFilter filter);

    /**
     * Get all child Packages for current Package.
     *
     * @return
     *         A list containing PojoPackages for all sub packages.
     */
    public List<PojoPackage> getPojoSubPackages();
}
