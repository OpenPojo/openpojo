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
package com.openpojo.reflection.exception;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author oshoukry
 *
 */
public class ReflectionExceptionTest {

    /**
     * Test method for {@link com.openpojo.reflection.exception.ReflectionException#getInstance(java.lang.String)}.
     */
    @Test
    public void testGetInstanceString() {
        ReflectionException reflectionException = ReflectionException.getInstance((Throwable)null);
        Assert.assertNotNull(reflectionException);
    }

    /**
     * Test method for {@link com.openpojo.reflection.exception.ReflectionException#getInstance(java.lang.Throwable)}.
     */
    @Test
    @Ignore("Unimplemented")
    public void testGetInstanceThrowable() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.exception.ReflectionException#getInstance(java.lang.String, java.lang.Throwable)}.
     */
    @Test
    @Ignore("Unimplemented")
    public void testGetInstanceStringThrowable() {
        fail("Not yet implemented");
    }

}
