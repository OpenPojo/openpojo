package com.openpojo;

import java.util.List;

import com.google.common.base.Preconditions;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

public class SimpleValidator {
    private final PojoValidator validator;
    private final List<PojoClass> classes;

    SimpleValidator(final PojoValidator validator, final List<PojoClass> classes) {
	Preconditions.checkNotNull(validator, "PojoValidator can not be null");
	Preconditions.checkNotNull(classes, "List<PojoClass> can not be null");
	this.validator = validator;
	this.classes = classes;
    }

    /**
     * Add a Tester to the Validator
     * 
     * @param testers
     * @return this
     */
    public SimpleValidator with(final Tester... testers) {
	for (final Tester tester : testers) {
	    validator.addTester(tester);
	}
	return this;
    }

    /**
     * Add a Tester to the Validator
     * 
     * @param rules
     * @return this
     */
    public SimpleValidator with(final Rule... rules) {
	for (final Rule rule : rules) {
	    validator.addRule(rule);
	}
	return this;
    }

    /**
     * runs the validation
     */
    public void valide() {
	for (final PojoClass clazz : classes) {
	    validator.runValidation(clazz);
	}
    }
}
