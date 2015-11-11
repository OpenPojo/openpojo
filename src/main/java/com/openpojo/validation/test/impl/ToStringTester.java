package com.openpojo.validation.test.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * 
 * This tester ensures the toString based on the object Identity is used properly.
 * 
 * This Tester is NOT thread safe.
 * 
 * @author Kleeven81
 *
 */
public class ToStringTester implements Tester {

	public void run(final PojoClass pojoClass) {
		final Object completeClassInstance = ValidationHelper.getMostCompleteInstance(pojoClass);
		checkToString(pojoClass, completeClassInstance);

		final Object basicClassInstance = ValidationHelper.getBasicInstance(pojoClass);
		checkToString(pojoClass, basicClassInstance);

	}

	private void checkToString(final PojoClass pojoClass, final Object instance) {
		StringBuilder expectedToString = new StringBuilder(pojoClass.getName());
		expectedToString.append(" [@").append(Integer.toHexString(System.identityHashCode(instance))).append(':');
		boolean firstField = true;
		for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
			if (!firstField) {
				expectedToString.append(',');
			} else {
				firstField = false;
			}
			expectedToString.append(' ');
			expectedToString.append(fieldEntry.getName());
			expectedToString.append('=');
			Object entry = fieldEntry.get(instance);
			if (entry != null) {
				expectedToString.append(entry.toString());
			} else {
				expectedToString.append("null");
			}
		}
		expectedToString.append(']');
		String expected = expectedToString.toString();
		String actual = instance.toString();
		Affirm.affirmEquals(
				String.format("toString did not return expected string for class '%s'.", pojoClass.getName()),
				expected, actual);
	}

}
