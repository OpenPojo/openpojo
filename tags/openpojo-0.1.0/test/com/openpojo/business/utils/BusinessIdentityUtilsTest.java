package com.openpojo.business.utils;

import org.junit.Test;

import com.openpojo.business.exception.BusinessException;

public class BusinessIdentityUtilsTest {

    @Test(expected = BusinessException.class)
    public void testAnyNull() {
        BusinessIdentityUtils.anyNull((Object[]) null);
    }
}
