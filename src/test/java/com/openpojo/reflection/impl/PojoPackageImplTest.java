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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.filters.FilterCloverClasses;
import com.openpojo.reflection.impl.sampleannotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class PojoPackageImplTest {

    private static final int EXPECTED_CLASSES = 29;

    private String packageName;
    private String expectedToString;

    private PojoPackage pojoPackage;

    @Before
    public void setUp() {
        packageName = this.getClass().getPackage().getName() + ".sampleclasses";
        expectedToString = "PojoPackageImpl [packageName=" + packageName + "]";
        pojoPackage = PojoPackageFactory.getPojoPackage(packageName);
    }

    @Test
    public void testGetPojoClasses() {
        int counter = 0;
        PojoClassFilter pojoFilter = new FilterCloverClasses();
        for (PojoClass pojoClass : pojoPackage.getPojoClasses()) {
            if (pojoFilter.include(pojoClass)) {
                counter++;
            }
        }
        Assert.assertEquals(String.format("classes added/removed to package=[%s]?", packageName), EXPECTED_CLASSES,
                counter);
    }

    @Test
    public void shouldReturnEmptyListNoAnnotation() {
        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
                + ".packagenoannotation");
        Affirm.affirmTrue(String.format("Annotations added? expected none [%s]", pojoPackage), pojoPackage
                .getAnnotations() != null
                && pojoPackage.getAnnotations().size() == 0);
    }

    @Test
    public void shouldReturnAnAnnotation() {
        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
                + ".packagemanyannotations");
        Class<? extends Annotation> expectedAnnotationClass = SomeAnnotation.class;
        Affirm.affirmNotNull(String.format("[%s] removed from package [%s]?", expectedAnnotationClass, pojoPackage),
                pojoPackage.getAnnotation(expectedAnnotationClass));
    }

    @Test
    public void shouldEnusreNoPackageInfoExists() {
        String packageName = this.getClass().getPackage().getName() + ".packagenopackageinfo";
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(packageName);

        Affirm.affirmTrue("No classes in package?", pojoClasses.size() > 0);
        for (PojoClass pojoClass : pojoClasses) {
            Affirm.affirmTrue(String.format("package-info added to package [%s]?", packageName), !pojoClass.getName()
                    .endsWith("package-info"));
        }

    }

    @Test
    public void shouldReturnNullAnnotationNoPackageInfo() {
        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
                + ".packagenopackageinfo");
        Affirm.affirmNull(String.format("package-info added to package [%s]?", pojoPackage), pojoPackage
                .getAnnotation(SomeAnnotation.class));
    }

    @Test
    public void shouldReturnEmptyListAnnotationNoPackageInfo() {
        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
                + ".packagenopackageinfo");
        Affirm.affirmEquals(String.format("package-info with annotations added to package [%s]?", pojoPackage), 0,
                pojoPackage.getAnnotations().size());
    }

    @Test
    public void shouldReturnAnnotationList() {
        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
                + ".packagemanyannotations");
        Affirm.affirmEquals(String.format("Annotations added/removed? [%s]", pojoPackage), 2, pojoPackage
                .getAnnotations().size());

        List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
        expectedAnnotations.add(SomeAnnotation.class);
        expectedAnnotations.add(AnotherAnnotation.class);
        for (Annotation annotation : pojoPackage.getAnnotations()) {
            Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]",
                    expectedAnnotations, annotation.annotationType()), expectedAnnotations.contains(annotation
                    .annotationType()));
        }
    }

    @Test
    public void shouldReturnPackageName() {
        String packageName = this.getClass().getPackage().getName();

        PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(packageName);
        Affirm.affirmEquals("Mismatch in packageName!!", packageName, pojoPackage.getName());

    }

    @Test(expected = ReflectionException.class)
    public void shouldFailNoSuchPackage() {
        PojoPackage pojoPackage = PojoPackageFactory
                .getPojoPackage((String) RandomFactory.getRandomValue(String.class));
        pojoPackage.getAnnotations();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        @SuppressWarnings("unused")
        PojoPackage pojoPackage = new PojoPackageImpl(null);
    }

    @Test
    public void testtoString() {
        Assert.assertEquals("toString format changed?!", expectedToString, pojoPackage.toString());
    }
}
