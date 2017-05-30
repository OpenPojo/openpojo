package com.openpojo.validation.test.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EqualsAndHashcodeContractTester implements Tester {

    public void run(final PojoClass pojoClass) {
        verifyEqualsHashCodeContrat(pojoClass);
        objectShouldBeEqualsToItself(pojoClass);
    }

    private static void verifyEqualsHashCodeContrat(final PojoClass pojoClass) {
        final Object classInstance = ValidationHelper.getMostCompleteInstance(pojoClass);
        final Object classOtherInstance = InstanceFactory.getLeastCompleteInstance(pojoClass);

        copyProperties(classInstance, classOtherInstance);

        boolean beansEquals = classInstance.equals(classOtherInstance);
        boolean hashCodeEquals = classInstance.hashCode() == classOtherInstance.hashCode();

        if (beansEquals && !hashCodeEquals) {
            Affirm.fail("Equals/hashCode contrat invalid " + pojoClass.getName());
        }
    }

    private static void objectShouldBeEqualsToItself(final PojoClass pojoClass){
        final Object classInstance = ValidationHelper.getMostCompleteInstance(pojoClass);

        if (!classInstance.equals(classInstance)) {
            Affirm.fail("one instance [" + pojoClass.getName() + "] should be equals to itself");
        }

        if (classInstance.hashCode() != classInstance.hashCode()) {
            Affirm.fail("one instance of [" + pojoClass.getName() + "] should always have the same hashCode");
        }
    }

    private static void objectShouldNotBeEqualsToAnything(final PojoClass pojoClass){
        final Object classInstance = ValidationHelper.getMostCompleteInstance(pojoClass);

        if (classInstance.equals(new Object())) {
            Affirm.fail("one instance of [" + pojoClass.getName() + "] shouldn't be equals anything");
        }

        if (classInstance.equals(null)) {
            Affirm.fail("one instance of [" + pojoClass.getName() + "] shouldn't be equals to null ");
        }
    }

    private static void copyProperties(Object source, Object destination) {
        for (Field field : source.getClass().getDeclaredFields()){
            if (!Modifier.isFinal(field.getModifiers())){
                setFieldProperty(source, destination, field);
            }
        }
    }

    private static void setFieldProperty(Object source, Object destination, Field field) {
        try {
            makeAccessible(field);
            field.set(destination, field.get(source));
        } catch (Exception e) {
            // nothing to see here
        }
    }

    private static void makeAccessible(AccessibleObject obj) {
        if (!obj.isAccessible()) {
            obj.setAccessible(true);
        }
    }
}
