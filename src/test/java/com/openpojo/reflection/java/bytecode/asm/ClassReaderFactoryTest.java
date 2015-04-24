/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection.java.bytecode.asm;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ClassReaderFactoryTest {

    @Test (expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToConstruct() {
        try {
            PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassReaderFactory.class);
            org.testng.Assert.assertEquals(1, pojoClass.getPojoConstructors().size());
            InstanceFactory.getLeastCompleteInstance(pojoClass);
        } catch (ReflectionException re) {
            Throwable cause = re.getCause();
            while (cause != null) {
                if (cause instanceof UnsupportedOperationException)
                    throw (UnsupportedOperationException) cause;
                cause = cause.getCause();
            }
        }
        Assert.fail("Should have not been able to construct");
    }


    @Test
    public void canCreateClassReader() {
        Assert.assertNotNull("Should not be null", ClassReaderFactory.getClassReader(this.getClass()));
    }

    @Test (expected = ReflectionException.class)
    public void shouldNotBeAbleToCreateClassReader() {
        ClassReaderFactory.getClassReader(null);
    }
}
