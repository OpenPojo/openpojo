/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.openpojo.PojoClass;
import com.openpojo.exception.ReflectionException;
import com.openpojo.impl.PojoClassFactory;

/**
 * This is a utility class to help enumerate and generate PojoClasses for specific pacakge.
 * @author oshoukry
 */
public class PojoPackageHelper {
    private final String packagename;

    public PojoPackageHelper(final String packagename) {
        this.packagename = packagename;
    }

    public List<PojoClass> getPojoClasses() throws ClassNotFoundException {
        List<PojoClass> pojoClasses = new ArrayList<PojoClass>();

        // Get a File object for the package
        File directory;
        directory = getPackageAsDirectory();

        // Get the list of the files contained in the package
        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            // we are only interested in .class files
            if (isClass(files[i])) {
                Class<?> clazz = Class.forName(getFQClassName(files[i]));
                pojoClasses.add(PojoClassFactory.buildPojoClass(clazz));
            }
        }
        return pojoClasses;
    }

    /**
     * 
     * @param pckgname
     * @return
     */
    private File getPackageAsDirectory() {
        File directory = null;
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ReflectionException("Can't get class loader.");
        }
        String path = packagename.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ReflectionException("No resource for " + path);
        }
        directory = new File(resource.getFile());
        if (!directory.exists()) {
            throw new ReflectionException(packagename + " does not appear to be a valid package");
        }

        if (!directory.exists()) {
            return directory;
        } else

            return directory;
    }

    private static final String CLASS_SUFFIX = ".class";

    /**
     * @param className
     * @return
     */
    private boolean isClass(String className) {
        if (className.endsWith(CLASS_SUFFIX)) {
            return true;
        }
        return false;
    }

    /**
     * @param pkg
     * @param fileEntry
     * @return
     */
    private String getFQClassName(String fileEntry) {
        String className = packagename + '.' + fileEntry.substring(0, fileEntry.length() - CLASS_SUFFIX.length());
        return className;
    }
}
