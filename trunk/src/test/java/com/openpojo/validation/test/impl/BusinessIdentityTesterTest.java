package com.openpojo.validation.test.impl;

import org.junit.Test;

import com.openpojo.validation.rule.impl.CommonCode;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoDispatchingHashCodeAndEquals;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingEquals;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingHashCode;

public class BusinessIdentityTesterTest {
    Class<?>[] failClasses = new Class<?>[]{ ABusinessPojoNotDispatchingHashCode.class,
            ABusinessPojoNotDispatchingEquals.class };
    Class<?>[] passClasses = new Class<?>[]{ ABusinessPojoDispatchingHashCodeAndEquals.class };
    Tester test = new BusinessIdentityTester();

    @Test
    public void testEvaluate() {
        CommonCode.shouldPassTesterValidation(test, passClasses);
        CommonCode.shouldFailTesterValidation(test, failClasses);
    }

}
