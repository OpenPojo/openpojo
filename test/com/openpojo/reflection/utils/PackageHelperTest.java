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
import java.util.Arrays;
import java.util.List;

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
        packageName = "utils.dummypackage";
    }

    @Test
    public final void testGetClasses() {
        Assert.assertEquals(2, PackageHelper.getClasses(packageName).size());
    }

    /**
     * Test that getPackageAsDirectory returns all contents of a package.
     */
    @Test
    public final void testGetPackageAsDirectory() {
        File directory = PackageHelper.getPackageAsDirectory(packageName);
        Assert.assertNotNull(directory);
        List<String> list = Arrays.asList(directory.list());

        Assert.assertEquals(list.toString(), 4, list.size());
        Assert.assertTrue(list.contains("Person.class"));
        Assert.assertTrue(list.contains("Persistable.class"));
        Assert.assertTrue(list.contains("dummy.txt"));
        Assert.assertTrue(list.contains("level2"));
    }

}
