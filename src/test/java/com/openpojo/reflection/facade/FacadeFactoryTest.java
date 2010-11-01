package com.openpojo.reflection.facade;

import java.util.Arrays;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.facade.sampleclasses.FirstClass;
import com.openpojo.reflection.facade.sampleclasses.SecondClass;
import com.openpojo.validation.affirm.Affirm;

public class FacadeFactoryTest {
    Class<?> firstClass = FirstClass.class;
    Class<?> secondClass = SecondClass.class;

    @Test
    public final void shouldGetFirstClass() {
        checkReturnedFacade(firstClass, firstClass.getName(), secondClass.getName());
        checkReturnedFacade(secondClass, secondClass.getName(), firstClass.getName());
    }

    private final void checkReturnedFacade(final Class<?> expected, final String... facades) {
        PojoClass facade = FacadeFactory.getLoadedFacadePojoClass(facades);
        Affirm.affirmNotNull(String.format("Failed to load from the valid list of facades [%s]?!", Arrays.toString(facades)), facade);

        Affirm.affirmEquals("Wrong facade returned!!", expected, facade.getClazz());

    }

    @Test(expected = ReflectionException.class)
    public void shouldThrowErorr() {
        FacadeFactory.getLoadedFacadePojoClass(new String[]{ (String) RandomFactory.getRandomValue(String.class) });
    }
}
