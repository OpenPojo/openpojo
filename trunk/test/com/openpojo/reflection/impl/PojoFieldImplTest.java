/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleclasses.PojoFieldImplClass;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class PojoFieldImplTest {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(PojoFieldImplClass.class);
    Object pojoClassInstance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pojoClassInstance = pojoClass.newInstance();
    }

    /**
     * Test method for: {@link com.openpojo.reflection.impl.PojoFieldImpl#get(java.lang.Object)}.
     * {@link com.openpojo.reflection.impl.PojoFieldImpl#set(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public void testSetAndGet() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (!pojoField.isFinal() && !pojoField.isPrimitive()) {
                Affirm.affirmNull(String.format("Field=[%s] should have null default value", pojoField), pojoField
                        .get(pojoClassInstance));
                Object randomValue = RandomFactory.getRandomValue(pojoField.getType());
                pojoField.set(pojoClassInstance, randomValue);
                Affirm.affirmEquals(String.format(
                        "PojoField.get() result=[%s] different from what was set=[%s] for PojoFieldImpl=[%s]",
                        pojoField.get(pojoClassInstance), randomValue, pojoField), randomValue, pojoField
                        .get(pojoClassInstance));
            }
        }
    }

    private PojoField getPrivateStringField() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName() == "privateString") {
                return pojoField;
            }
        }
        Affirm.fail("Field with name 'privateString' removed from class" + pojoClass.getName());
        return null;
    }

    @Test (expected = ReflectionException.class)
    public void shouldFailSet() {
        PojoField pojoField = getPrivateStringField();
        pojoField.set(null, RandomFactory.getRandomValue(pojoField.getType()));
    }

    @Test (expected = ReflectionException.class)
    public void shouldFailGet() {
        PojoField pojoField = getPrivateStringField();
        pojoField.get(null);
    }

    @Test (expected = ReflectionException.class)
    public void shouldFailSetter() {
        PojoField pojoField = getPrivateStringField();
        pojoField.inovkeSetter(null, RandomFactory.getRandomValue(pojoField.getType()));
    }

    @Test (expected = ReflectionException.class)
    public void shouldFailGetter() {
        PojoField pojoField = getPrivateStringField();
        pojoField.invokeGetter(null);
    }


    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#getName()}.
     * TODO:
     */
    //@Test
    public void testGetName() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#hasGetter()}.
     * TODO:
     */
    //@Test
    public void testHasGetter() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#invokeGetter(java.lang.Object)}.
     * TODO:
     */
    //@Test
    public void testInvokeGetter() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#hasSetter()}.
     * TODO:
     */
    //@Test
    public void testHasSetter() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link com.openpojo.reflection.impl.PojoFieldImpl#inovkeSetter(java.lang.Object, java.lang.Object)}.
     * TODO:
     */
    //@Test
    public void testInovkeSetter() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#getType()}.
     * TODO:
     */
    //@Test
    public void testGetType() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#getAnnotation(java.lang.Class)}.
     * TODO:
     */
    //@Test
    public void testGetAnnotation() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isPrimitive()}.
     */
    @Test
    public void testIsPrimitive() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName().startsWith("primitive")) {
                Affirm.affirmTrue(String.format("isPrimitive() check on primitive field=[%s] returned false!!",
                        pojoField), pojoField.isPrimitive());
            }
        }
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isFinal()}.
     * TODO:
     */
    //@Test
    public void testIsFinal() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isStatic()}.
     */
    @Test
    public void testIsStatic() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName().startsWith("static")) {
                Affirm.affirmTrue(String.format("isStatic() check on field=[%s] returned false!!",
                        pojoField), pojoField.isStatic());
            }
        }
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isPrivate()}.
     */
    @Test
    public void testIsPrivate() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName().startsWith("private")) {
                Affirm.affirmTrue(String.format("isPrivate() check on field=[%s] returned false!!",
                        pojoField), pojoField.isPrivate());
            }
        }

    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isProtected()}.
     */
    @Test
    public void testIsProtected() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName().startsWith("protected")) {
                Affirm.affirmTrue(String.format("isProtected() check on field=[%s] returned false!!",
                        pojoField), pojoField.isProtected());
            }
        }
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#isPublic()}.
     */
    @Test
    public void testIsPublic() {
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName().startsWith("public")) {
                Affirm.affirmTrue(String.format("isPublic() check on field=[%s] returned false!!",
                        pojoField), pojoField.isPublic());
            }
        }
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#toString()}.
     * TODO:
     */
    //@Test
    public void testToString() {
        Affirm.fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoFieldImpl#toString(java.lang.Object)}.
     * TODO:
     */
    //@Test
    public void testToStringObject() {
        Affirm.fail("Not yet implemented");
    }

}
