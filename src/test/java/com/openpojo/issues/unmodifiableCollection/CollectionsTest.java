package com.openpojo.issues.unmodifiableCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class CollectionsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCollectionContainingClass() {
		PojoValidator pojoValidator = new PojoValidator();

		GetterTester getterTester = new GetterTester();
		SetterTester setterTester = new SetterTester();

		pojoValidator.addTester(getterTester);
		pojoValidator.addTester(setterTester);

		PojoClass pojoClass = PojoClassFactory
				.getPojoClass(CollectionContainingClass.class);
		pojoValidator.runValidation(pojoClass);
	}

	private static class CollectionContainingClass {
		private List<String> values = new ArrayList<String>();

		public void setValues(List<String> values) {
			this.values = values;
		}

		public List<String> getValues() {
			return Collections.unmodifiableList(values);
		}

	}
}
