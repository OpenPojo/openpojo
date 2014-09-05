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

package com.openpojo.business;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.sampleclasses.Child;

public class BusinessIdentityInheritenceTest {

    @Test
    public void shouldEquateUsingInheritence() {
        Child first = new Child("First", "Last", 'M');
        Child second = new Child("First", "Last", 'M');
        Assert.assertTrue(BusinessIdentity.areEqual(first, second));
    }

    @Test
    public void shouldFailEquateUsingInheritence() {
        Child first = new Child("First", "Last", 'F');
        Child second = new Child("First", "LastName", 'F');
        Assert.assertFalse(BusinessIdentity.areEqual(first, second));
    }

    @Test (expected = BusinessException.class)
    public void shouldFailIncomplete() {
        Child first = new Child("firstName", "last", null);
        Child second = new Child("First", "LastName", null);
        Assert.assertTrue(BusinessIdentity.areEqual(first, second));
    }

}
