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
package com.openpojo.reflection.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoPackageImplTest {

    private static final int EXPECTED_CLASSES = 23;

    private String packageName;
    private String expectedToString;

    private PojoPackageImpl pojoPackageImpl;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        packageName = this.getClass().getPackage().getName() + ".sampleclasses";
        expectedToString = "PojoPackageImpl [packageName=" + packageName + "]";
        pojoPackageImpl = new PojoPackageImpl(packageName);
    }

    @Test
    public void testGetPojoClasses() {
        Assert.assertEquals(String.format("classes added/removoed to package=[%s]?", packageName), EXPECTED_CLASSES,
                pojoPackageImpl.getPojoClasses().size());
    }

    @Test
    public void testtoString() {
        Assert.assertEquals("toString format changed?!", expectedToString, pojoPackageImpl.toString());
    }
}
