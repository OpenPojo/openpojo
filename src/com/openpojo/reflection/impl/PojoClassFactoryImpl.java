package com.openpojo.reflection.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.utils.PackageHelper;

/**
 * This is a factory class that builds PojoClassImpl representation given a class.
 * 
 * @author oshoukry
 */
public final class PojoClassFactoryImpl {
    
    /**
     * Create a PojoClass for a given application Class.
     * @param clazz
     *          Class to introspect.
     * @return
     *          A PojoClass meta representation for the clazz.
     */
    public static PojoClass getPojoClass(Class<?> clazz) {
        return new PojoClassImpl(clazz, PojoFieldFactoryImpl.getPojoFields(clazz));
    }

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
        List<PojoClass> pojoClasses = new LinkedList<PojoClass>();
        
        PackageHelper pkg = new PackageHelper(packagename);
        
        for (Class<?> clazz : pkg.getClasses()) {
            PojoClass pojoClass = getPojoClass(clazz);
            if (filter == null || filter.include(pojoClass)) {
                pojoClasses.add(pojoClass);
            }
        }
        
        return pojoClasses;
    }
}
