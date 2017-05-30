package com.openpojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

@RunWith(MockitoJUnitRunner.class)
public class SimpleValidatorTest {
    @Mock
    private Rule rule;
    private Tester tester;

    @Mock
    private PojoValidator pojoValidator;
    @Mock
    private PojoClass class1;
    @Mock
    private PojoClass class2;
    @Spy
    private final List<PojoClass> classes = new ArrayList<PojoClass>();

    @InjectMocks
    private SimpleValidator validator;

    @Before
    public void setUp() {
	classes.add(class1);
	classes.add(class2);
    }

    @Test
    public void valide() {
	validator.with(rule).with(tester).validate();
    }

    @Test(expected = NullPointerException.class)
    public void nullClasses() {
	new SimpleValidator(pojoValidator, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullPojoValidator() {
	new SimpleValidator(null, classes);
    }
}
