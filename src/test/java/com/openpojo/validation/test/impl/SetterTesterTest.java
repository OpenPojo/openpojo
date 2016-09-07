package com.openpojo.validation.test.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;

public class SetterTesterTest {

	@Test
	public void test() {
		PojoClass personPojo = PojoClassFactory.getPojoClass(Person.class);

		SetterTester setterTester = new SetterTester();
		setterTester.addField("name");
		
		GetterTester getterTester = new GetterTester();
		getterTester.addField("name");
		
		Validator validator = ValidatorBuilder.create().with(setterTester).with(getterTester).build();

		validator.validate(personPojo);
	}
}
