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
package com.openpojo.reflection.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * Test PackageHelper.
 *
 * @author oshoukry
 */
public class PackageHelperTest {
    private static final String PACKAGE_NAME = "com.openpojo.utils.dummypackage";
    private static final String[] CLASSES = new String[]{ "Person.class", "Persistable.class", "dummy.txt", "level2" };
    private static final String CLOVER_STRING = "$__CLR";

    @Test
    public final void testGetClasses() {
        List<Class<?>> classes = new LinkedList<Class<?>>();
        for (Class<?> classEntry : PackageHelper.getClasses(PACKAGE_NAME)) {
            if (!classEntry.getName().contains(CLOVER_STRING)) {
                classes.add(classEntry);
            }
        }
        Assert.assertEquals(2, classes.size());
    }

    /**
     * Test that getPackageAsDirectory returns all contents of a package.
     */
    @Test
    public final void testGetPackageAsDirectory() {
        List<File> directories = PackageHelper.getPackageDirectories(PACKAGE_NAME);
        Assert.assertNotNull(directories);

        List<String> list = new LinkedList<String>();
        for (File directory : directories) {
            for (String entry : directory.list()) {
                if (!entry.contains(CLOVER_STRING)) {
                    list.add(entry);
                }
            }
        }

        Assert.assertEquals(list.toString(), CLASSES.length, list.size());
        for (String entry : CLASSES) {
            Assert.assertTrue(String.format("expected entry=[%s] in package=[%s] but was not found", entry,
                PACKAGE_NAME), list.contains(entry));
        }
    }

    @Test
    public final void testClassNotFoundOnClassLoading() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(PackageHelper.class);
        PojoMethod loadClassPojoMethod = null;
        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (pojoMethod.getName().equals("loadClass")) {
                loadClassPojoMethod = pojoMethod;
            }
        }
        Assert.assertNotNull("loadClass method removed from class PackageHelper.class?", loadClassPojoMethod);
        // ClassLoader classLoader, final String className
        loadClassPojoMethod.invoke(null, Thread.currentThread().getContextClassLoader(), this.getClass().getName());

        // now invoke on non existing class.
        try {
            loadClassPojoMethod.invoke(null, Thread.currentThread().getContextClassLoader(), RandomFactory
                .getRandomValue(String.class));
        } catch (ReflectionException e) {
        }
    }

}
