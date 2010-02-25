package com.openpojo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.DefaultValuesNullRule;
import com.openpojo.validation.rule.impl.GetterSetterMustExistRule;
import com.openpojo.validation.rule.impl.NoPrimitivesRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinal;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * This example demonstrates what you can do to utilize this utility to test your own pojos.
 * This class will test all getters and setters to ensure they are working correctly.
 * 
 * @author oshoukry
 */
public class PojoTest {
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 1;

    // The package to test
    private static final String DB_PACKAGE = "com.openpojo.samplepojo";

    private List<PojoClass> pojoClasses;
    private PojoValidator pojoValidator;

    @Before
    public void setup() {
        pojoClasses = PojoClassFactory.getPojoClasses(DB_PACKAGE);

        pojoValidator = new PojoValidator();
        pojoValidator.add(new NoPublicFieldsRule());
        pojoValidator.add(new DefaultValuesNullRule());
        pojoValidator.add(new NoPrimitivesRule());
        pojoValidator.add(new NoStaticExceptFinal());
        pojoValidator.add(new GetterSetterMustExistRule());

        pojoValidator.add(new SetterTester());
        pojoValidator.add(new GetterTester());
    }

    @Test
    public void ensureExpectedPojoCount() {
        Assert.assertEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }

    @Test
    public void testPojoStructure() {
        for (PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }
}
