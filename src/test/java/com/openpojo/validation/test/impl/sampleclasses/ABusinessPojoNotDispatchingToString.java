package com.openpojo.validation.test.impl.sampleclasses;

import com.openpojo.business.annotation.BusinessKey;

public class ABusinessPojoNotDispatchingToString {

	@BusinessKey
	private String someString;

	@Override
	public String toString() {
		return someString;
	}
}
