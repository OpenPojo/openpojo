package com.openpojo.reflection.utils;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.exception.ReflectionException;

/**
 * This is a utility class to help enumerate and generate PojoClasses for specific pacakge.
 * 
 * @author oshoukry
 */
public class PackageHelper {
    private final String packagename;

    public PackageHelper(final String packagename) {
        this.packagename = packagename;
    }

    public List<Class<?>> getClasses() throws ClassNotFoundException {
        List<Class<?>> classes = new LinkedList<Class<?>>();

        // Get a File object for the package
        File directory;
        directory = getPackageAsDirectory();

        // Get the list of the files contained in the package
        String[] files = directory.list();

        for (int i = 0; i < files.length; i++) {
            if (isClass(files[i])) {
                classes.add(Class.forName(getFQClassName(files[i])));
            }
        }

        return classes;
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
     * @param fileEntry
     * @return
     */
    private String getFQClassName(String fileEntry) {
        String className = packagename + '.' + fileEntry.substring(0, fileEntry.length() - CLASS_SUFFIX.length());
        return className;
    }
}
