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

package com.openpojo.reflection.java.packageloader.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class FilePackageLoaderTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link com.openpojo.reflection.java.packageloader.impl.FilePackageLoader#getTypes()}.
     *
     * @throws IOException
     */
    @Test
    @Ignore
    public final void testGetTypes() throws IOException {
        String packageName = "com.openpojo".replace('.', '/');// this.getClass().getPackage().getName().replace('.',
                                                              // '/');
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName);
        System.out.println("Resources has more? = " + urls.hasMoreElements());
        int counter = 0;
        while (urls.hasMoreElements()) {
            URL entry = urls.nextElement();
            FilePackageLoader filePackageLoader = new FilePackageLoader(entry, packageName);
            for (String subPackage : filePackageLoader.getSubPackages()) {
                System.out.println("Sub: " + counter + " :" + subPackage);
            }
            for (Type type : filePackageLoader.getTypes()) {
                System.out.println(((Class<?>) type).getName());
            }
            counter++;
        }
    }
}
