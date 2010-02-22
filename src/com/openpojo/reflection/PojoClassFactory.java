/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.reflection;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.utils.PojoPackageHelper;

/**
 * This is a factory class that builds PojoClassImpl representation given a class.
 * 
 * @author oshoukry
 */
public final class PojoClassFactory {

    /**
     * This method returns a list of PojoClasses in a package representation.
     * 
     * @param packagename
     *            Package to introspect (eg. com.mypackage.pojo).
     * @return
     *         A list of PojoClasses.
     * @throws ClassNotFoundException
     *             If this package is invalid a ClassNotFoundException gets thrown.
     */
    public static List<PojoClass> getPojoClasses(final String packagename) throws ClassNotFoundException {
        return getPojoClasses(packagename, null);
    }

    /**
     * This method returns a list of PojoClasses in a package representation with filtering cababilities.
     * 
     * @param packagename
     *            Package to introspect (eg. com.mypackage.pojo).
     * @param filter
     *            The filter to apply to the list of PojoClasses.
     * @return
     *         A list of PojoClasses.
     * @throws ClassNotFoundException
     *             If this package is invalid a ClassNotFoundException gets thrown.
     */
    public static List<PojoClass> getPojoClasses(final String packagename, PojoClassFilter filter)
            throws ClassNotFoundException {
        PojoPackageHelper pkg = new PojoPackageHelper(packagename);
        List<PojoClass> rawList = pkg.getPojoClasses();

        if (filter != null) {
            List<PojoClass> filteredList = new LinkedList<PojoClass>();
            for (PojoClass entry : rawList) {
                if (filter.include(entry)) {
                    filteredList.add(entry);
                }
            }
            return filteredList;
        }
        return rawList;
    }
}
