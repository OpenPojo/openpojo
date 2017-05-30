package com.openpojo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.openpojo.reflection.PojoClassFilter;

@RunWith(MockitoJUnitRunner.class)
public class PojosTest {
    private static final String DUMMY_PACKAGE = "com.openpojo.utils.dummypackage";

    @Mock
    private PojoClassFilter filter;

    @Test
    public void createValidatorWithPackageName() {
	assertNotNull(Pojos.createValidator(DUMMY_PACKAGE));
    }

    @Test
    public void createValidatorWithPackageNameAndFilter() {
	assertNotNull(Pojos.createValidator(DUMMY_PACKAGE, filter));
    }

    @Test(expected = NullPointerException.class)
    public void createValidatorWithNullPackageName() {
	Pojos.createValidator(null);
    }
}
