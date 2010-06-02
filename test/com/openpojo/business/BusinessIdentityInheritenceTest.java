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
