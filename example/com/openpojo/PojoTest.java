/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactoryImpl;

/**
 * @author oshoukry
 *         This class will test all getters and setters to ensure they are working correctly.
 */
public class PojoTest {
    private static final int EXPECTED_CLASS_COUNT = 1;
    private static final String DB_PACKAGE = "com.openpojo.samplepojo";

    private List<PojoClass> pojoClasses;
    
    @Before
    public void setup() throws ClassNotFoundException {
        pojoClasses = PojoClassFactoryImpl.getPojoClasses(DB_PACKAGE);
    }
    
    @Test
    public void ensureExpectedPojoCount() {
        Assert.assertEquals("Classes added / removed?", EXPECTED_CLASS_COUNT, pojoClasses.size());
    }

    /**
     * This method tests getters / setters vs. direct access for all fields in a class.
     * 
     */
    @Test
    public void testPojoStructure() {
        for (PojoClass classEntry : pojoClasses) {
            failIfHasPublicFields(classEntry);
            failIfFieldMissingGetterOrSetter(classEntry);
        }
    }

    /**
     * Check and fail if DB Pojo has public accessable fields (i.e. not through getters/setters).
     * 
     * @param classEntry
     *            Class to test.
     */
    private void failIfHasPublicFields(PojoClass classEntry) {
        for (PojoField fieldEntry : classEntry.getPojoFields()) {
            if (fieldEntry.isPublic()) {
                Assert.fail("Public fields not allowed " + fieldEntry);
            }
        }
    }

    /**
     * Check and fail if DB Pojo has fields missing a getter or setter.
     * 
     * @param classEntry
     *            Class to test.
     */
    private void failIfFieldMissingGetterOrSetter(PojoClass classEntry) {
        for (PojoField fieldEntry : classEntry.getPojoFields()) {
            if (!isStaticFinal(fieldEntry) && (!fieldEntry.hasGetter() || !fieldEntry.hasSetter())) {
                Assert.fail(fieldEntry + " is missing a getter/setter");
            }
        }
    }

    /**
     * Fail if initial value for a field is not null.
     * 
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void failIfDefaultFieldValueIsNotNull() throws InstantiationException, IllegalAccessException {
        Object classInstance = null;

        for (PojoClass classEntry : pojoClasses) {
            classInstance = classEntry.newInstance();
            for (PojoField fieldEntry : classEntry.getPojoFields()) {
                if (!isStaticFinal(fieldEntry)) {
                    Assert.assertNull("Expected null value for for field=[" + fieldEntry + "]", fieldEntry
                            .get(classInstance));
                }
            }
        }

    }

    /**
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testPojoFieldGetter() throws InstantiationException, IllegalAccessException {
        Object classInstance = null;

        for (PojoClass classEntry : pojoClasses) {
            classInstance = classEntry.newInstance();
            for (PojoField fieldEntry : classEntry.getPojoFields()) {
                if (!isStaticFinal(fieldEntry)) {
                    Object value = RandomFactory.getRandomValue(fieldEntry.getType());
                    fieldEntry.set(classInstance, value);
                    Assert.assertEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value,
                            fieldEntry.invokeGetter(classInstance));
                }
            }
        }
    }

    /**
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void testPojoFieldSetter() throws InstantiationException, IllegalAccessException {
        Object classInstance = null;

        for (PojoClass classEntry : pojoClasses) {
            classInstance = classEntry.newInstance();
            for (PojoField fieldEntry : classEntry.getPojoFields()) {
                if (!isStaticFinal(fieldEntry)) {
                    Object value = RandomFactory.getRandomValue(fieldEntry.getType());
                    fieldEntry.inovkeSetter(classInstance, value);
                    Assert.assertEquals("Setter test failed, non equal value for field=[" + fieldEntry + "]", value,
                            fieldEntry.get(classInstance));
                }
            }
        }

    }

    /**
     * Utility method to inspect a field for being a static final field.
     * 
     * @param pojoFieldImpl
     *            The field to examine.
     * @return
     *         Returns true if the field is static final, otherwise false.
     */
    private boolean isStaticFinal(PojoField pojoFieldImpl) {
        return pojoFieldImpl.isStatic() && pojoFieldImpl.isFinal();
    }

}
