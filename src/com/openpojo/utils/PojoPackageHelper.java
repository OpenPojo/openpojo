/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.PojoClass;
import com.openpojo.PojoField;
import com.openpojo.cache.PojoCache;
import com.openpojo.exception.ReflectionException;
import com.openpojo.impl.PojoClassImpl;
import com.openpojo.impl.PojoFieldImpl;

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
                pojoClasses.add(buildPojoClass(clazz));
            }
        }
        return pojoClasses;
    }

    /**
     * @param clazz
     * @return
     */
    public static PojoClass buildPojoClass(final Class<?> clazz) {

        PojoClass returnPojo = PojoCache.getPojoClass(clazz.getName());

        if (returnPojo == null) {
            List<PojoField> pojoFields = new LinkedList<PojoField>();
            for (Field field : getDeclaredFields(clazz)) {
                if (!field.isSynthetic()) { // inner instance classes are synthetic skip those
                    pojoFields.add(new PojoFieldImpl(field, MethodLookup.getGetter(clazz, field), MethodLookup
                            .getSetter(clazz, field)));
                }
            }
            returnPojo = new PojoClassImpl(clazz, pojoFields);
            PojoCache.addPojoClass(returnPojo.getName(), returnPojo);
        }
        return returnPojo;
    }

    /**
     * Returns all declared fields on a given class.
     * 
     * @param clazz
     *            The class to generate fields for.
     * @return
     *         List containing the fields of the class.
     */
    private static List<Field> getDeclaredFields(final Class<?> clazz) {
        LinkedList<Field> fields = new LinkedList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        return Collections.unmodifiableList(fields);
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
