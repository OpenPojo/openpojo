package com.openpojo.validation.test.impl;

import org.junit.Test;

import com.openpojo.validation.rule.impl.CommonCode;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoDispatchingToString;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingEquals;
import com.openpojo.validation.test.impl.sampleclasses.ABusinessPojoNotDispatchingToString;

public class ToStringTesterTest {

	Class<?>[] failClasses = new Class<?>[] { ABusinessPojoNotDispatchingToString.class,
			ABusinessPojoNotDispatchingEquals.class };
	Class<?>[] passClasses = new Class<?>[] { ABusinessPojoDispatchingToString.class };
	Tester test = new ToStringTester();

	@Test
	public void testEvaluate() {
		CommonCode.shouldPassTesterValidation(test, passClasses);
		CommonCode.shouldFailTesterValidation(test, failClasses);
	}

}
