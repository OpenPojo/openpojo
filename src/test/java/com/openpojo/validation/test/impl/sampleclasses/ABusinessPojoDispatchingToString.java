package com.openpojo.validation.test.impl.sampleclasses;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

public class ABusinessPojoDispatchingToString {

	@BusinessKey
	private String someString;

	@Override
	public String toString() {
		return BusinessIdentity.toString(this);
	}
}
