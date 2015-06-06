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

package com.openpojo.business.utils;

import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.exception.BusinessException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

public class BusinessIdentityUtilsTest {

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToConstruct() {
        try {
            PojoClass pojoClass = PojoClassFactory.getPojoClass(BusinessIdentityUtils.class);
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
    public void shouldThrowBusinessExceptionWhenNullParameter() {
        try {
            BusinessIdentityUtils.anyNull((Object[]) null);
            Assert.fail("Expected BusinessException not thrown");
        } catch (final BusinessException be) {
            Assert.assertEquals("objects parameter cannot be null", be.getMessage());
        }
    }

    @Test
    public void whenArrayAndSecondElementHasNonNullAndFirstElementIsNullShouldReturnFalse() {
        final Integer[] firstObject = new Integer[] { 1, null };
        Integer[] secondObject = new Integer[] { 1, 2 };
        Assert.assertFalse("Should return false", BusinessIdentityUtils.areEqual(new BusinessKeyField() {
            public boolean isComposite() {
                return false;
            }
            public boolean isCaseSensitive() {
                return false;
            }

            public boolean isRequired() {
                return false;
            }

            public Object get(Object instance) {
                return instance;
            }

            public boolean isArray() {
                return true;
            }
        }, firstObject, secondObject, false));
    }
}
