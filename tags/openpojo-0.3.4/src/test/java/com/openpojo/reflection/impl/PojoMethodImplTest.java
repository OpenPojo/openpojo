/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.sampleannotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;
import com.openpojo.reflection.impl.sampleclasses.PojoMethodClass;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class PojoMethodImplTest {
    PojoClass pojoClass;
    List<PojoMethod> pojoMethods;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pojoClass = PojoClassFactory.getPojoClass(PojoMethodClass.class);
        pojoMethods = pojoClass.getPojoMethods();
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoMethodImpl#getAnnotation(java.lang.Class)}.
     */
    @Test
    public void testGetAnnotation() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("methodWithAnnotation")) {
                Affirm.affirmNotNull("removed SomeAnnotation annotation from methodWithAnnotation?", pojoMethod
                        .getAnnotation(SomeAnnotation.class));
            }
            if (pojoMethod.getName().equals("methodWithoutAnnotation")) {
                Affirm.affirmNull("SomeAnnotation annotation added to methodWithoutAnnotation?", pojoMethod
                        .getAnnotation(SomeAnnotation.class));
            }
        }
    }

    @Test
    public void multipleAnnotationsShouldBeReturned() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("methodWithMultipleAnnotations")) {
                Affirm.affirmEquals(String.format("Annotations added/removed from method=[%s]", pojoMethod), 2,
                                    pojoMethod.getAnnotations().size());
                List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
                expectedAnnotations.add(SomeAnnotation.class);
                expectedAnnotations.add(AnotherAnnotation.class);
                for (Annotation annotation : pojoMethod.getAnnotations()) {
                    Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]",
                                                    expectedAnnotations, annotation.annotationType()),
                                      expectedAnnotations.contains(annotation.annotationType()));
                }
                return;
            }
        }
        Affirm.fail(String.format("methodWithMultipleAnnotations renamed? expected in [%s]", pojoClass));
    }

    /**
     * Test method for {@link com.openpojo.reflection.impl.PojoMethodImpl#isFinal()}.
     */
    @Test
    public void testIsFinal() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("finalMethod")) {
                Affirm.affirmTrue("Failed to check final", pojoMethod.isFinal());
                return;
            }
        }
        Affirm.fail("finalMethod missing!!");
    }

    @Test
    public void testIsNonFinal() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("nonFinalMethod")) {
                Affirm.affirmTrue("Failed to check non final", !pojoMethod.isFinal());
                return;
            }
        }
        Affirm.fail("nonFinalMethod missing!!");
    }

    @Test
    public void testIsPrivate() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("privateMethod")) {
                Affirm.affirmTrue("Failed to check private method", pojoMethod.isPrivate());
                return;
            }
        }
        Affirm.fail("privateMethod missing!!");
    }

    @Test
    public void testIsNotPrivate() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("nonPrivateMethod")) {
                Affirm.affirmTrue("Failed to check non private method", !pojoMethod.isPrivate());
                return;
            }
        }
        Affirm.fail("nonPrivateMethod missing!!");
    }

    @Test
    public void testIsProtected() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("protectedMethod")) {
                Affirm.affirmTrue("Failed to check protected method", pojoMethod.isProtected());
                return;
            }
        }
        Affirm.fail("protectedMethod missing!!");
    }

    @Test
    public void testIsNotProtected() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("nonProtectedMethod")) {
                Affirm.affirmTrue("Failed to check non protected method", !pojoMethod.isProtected());
                return;
            }
        }
        Affirm.fail("nonProtectedMethod missing!!");
    }

    @Test
    public void testIsPublic() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("publicMethod")) {
                Affirm.affirmTrue("Failed to check public method", pojoMethod.isPublic());
                return;
            }
        }
        Affirm.fail("publicMethod missing!!");
    }

    @Test
    public void testIsNotPublic() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("nonPublicMethod")) {
                Affirm.affirmTrue("Failed to check non public method", !pojoMethod.isPublic());
                return;
            }
        }
        Affirm.fail("nonPublicMethod missing!!");
    }

    @Test
    public void testIsStatic() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("staticMethod")) {
                Affirm.affirmTrue("Failed to check static method", pojoMethod.isStatic());
                return;
            }
        }
        Affirm.fail("staticMethod missing!!");
    }

    @Test
    public void testIsNotStatic() {
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.getName().equals("nonStaticMethod")) {
                Affirm.affirmTrue("Failed to check non static method", !pojoMethod.isStatic());
                return;
            }
        }
        Affirm.fail("nonStaticMethod missing!!");
    }

}
