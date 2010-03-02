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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Test PackageHelper.
 * 
 * @author oshoukry
 */
public class PackageHelperTest {
    private String packageName;

    @Before
    public final void setUp() {
        packageName = "dummypackage";
    }

    @Test
    public final void testGetClasses() {
        Assert.assertEquals(2, PackageHelper.getClasses(packageName).size());
    }

    @Test
    public final void testGetPackageAsDirectory() {
        File directory = PackageHelper.getPackageAsDirectory(packageName);
        Assert.assertNotNull(directory);
        String[] list = directory.list();
        Assert.assertEquals(3, list.length);
        Assert.assertTrue(list[0].contains("Person.class") || list[0].contains("Persistable.class")
                || list[0].contains("dummy.txt"));
        Assert.assertTrue(list[1].contains("Person.class") || list[1].contains("Persistable.class")
                || list[0].contains("dummy.txt"));
    }

}
