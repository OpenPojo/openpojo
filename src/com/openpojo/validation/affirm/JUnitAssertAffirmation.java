package com.openpojo.validation.affirm;

public class JUnitAssertAffirmation implements Affirmation {
    public void fail(final String message) {
        org.junit.Assert.fail(message);
    }

    public void affirmTrue(final String message, final boolean condition) {
        org.junit.Assert.assertTrue(message, condition);
    }

    public void affirmFalse(final String message, final boolean condition) {
        org.junit.Assert.assertFalse(message, condition);
    }

    public void affirmNotNull(final String message, final Object object) {
        org.junit.Assert.assertNotNull(message, object);
    }

    public void affirmNull(final String message, final Object object) {
        org.junit.Assert.assertNull(message, object);
    }
    
    public void affirmEquals(final String message, final Object first, final Object second) {
        org.junit.Assert.assertEquals(message, first, second);
    }

}
