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
 *
 */
public class PojoPackageImplTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoPackageImpl#getPojoClasses()}.
     */
    @Test
    public void testGetPojoClasses() {
        String packageName = this.getClass().getPackage().getName() + ".sampleclasses";
        PojoPackageImpl pojoPackageImpl = new PojoPackageImpl(packageName);
        Assert.assertEquals(String.format("classes added/removoed to package=[%s]?", packageName), 18, pojoPackageImpl.getPojoClasses().size());
    }

}
