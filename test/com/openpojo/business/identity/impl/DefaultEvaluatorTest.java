package com.openpojo.business.identity.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.sampleclasses.Child;

public class DefaultEvaluatorTest {

    IdentityEvaluator identityEvaluator = DefaultEvaluator.getInstance();

    @Test
    public void shouldEquateUsingInheritence() {
        Child first = new Child("First", "Last");
        Child second = new Child("First", "Last");
        Assert.assertTrue(identityEvaluator.areEqual(first, second));
    }

    @Test
    public void shouldFailEquateUsingInheritence() {
        Child first = new Child("First", "Last");
        Child second = new Child("First", "LastName");
        Assert.assertFalse(identityEvaluator.areEqual(first, second));
    }

    @Test
    public void shouldFailIncomplete() {
        Child first = new Child("First");
        Child second = new Child("First");
        Assert.assertTrue(identityEvaluator.areEqual(first, second));
    }

}
