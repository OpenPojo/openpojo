/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sample.annotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sample.annotation.SomeAnnotation;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoPackageImplTest {

  private static final int EXPECTED_CLASSES = 57;

  private String packageName;
  private String expectedToString;

  private PojoPackage pojoPackage;

  @Before
  public void setUp() {
    packageName = this.getClass().getPackage().getName() + ".sample.classes";
    expectedToString = "PojoPackageImpl [packageName=" + packageName + "]";
    pojoPackage = PojoPackageFactory.getPojoPackage(packageName);
  }

  @Test
  public void testGetPojoClasses() {
    int counter = 0;
    for (final PojoClass pojoClass : pojoPackage.getPojoClasses()) {
      if (ServiceRegistrar.getInstance().getPojoCoverageFilterService().include(pojoClass))
        counter++;
    }
    Affirm.affirmEquals(String.format("classes added/removed to package=[%s]?", packageName), EXPECTED_CLASSES, counter);
  }

  @Test
  public void shouldReturnEmptyListNoAnnotation() {
    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
        + ".packagenoannotation");
    Affirm.affirmTrue(String.format("Annotations added? expected none [%s]", pojoPackage), pojoPackage.getAnnotations() != null
        && pojoPackage.getAnnotations().size() == 0);
  }

  @Test
  public void shouldReturnAnAnnotation() {
    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
        + ".packagemanyannotations");
    final Class<? extends Annotation> expectedAnnotationClass = SomeAnnotation.class;
    Affirm.affirmNotNull(String.format("[%s] removed from package [%s]?", expectedAnnotationClass, pojoPackage), pojoPackage
        .getAnnotation(expectedAnnotationClass));
  }

  @Test
  public void shouldEnusreNoPackageInfoExists() {
    final String packageName = this.getClass().getPackage().getName() + ".packagenopackageinfo";
    final List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(packageName);

    Affirm.affirmTrue("No classes in package?", pojoClasses.size() > 0);
    for (final PojoClass pojoClass : pojoClasses) {
      Affirm.affirmTrue(String.format("package-info added to package [%s]?", packageName),
          !pojoClass.getName().endsWith("package-info"));
    }

  }

  @Test
  public void shouldReturnNullAnnotationNoPackageInfo() {
    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
        + ".packagenopackageinfo");
    Affirm.affirmNull(String.format("package-info added to package [%s]?", pojoPackage),
        pojoPackage.getAnnotation(SomeAnnotation.class));
  }

  @Test
  public void shouldReturnEmptyListAnnotationNoPackageInfo() {
    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
        + ".packagenopackageinfo");
    Affirm.affirmEquals(String.format("package-info with annotations added to package [%s]?", pojoPackage),
        0,
        pojoPackage.getAnnotations().size());
  }

  @Test
  public void shouldReturnAnnotationList() {
    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(this.getClass().getPackage().getName()
        + ".packagemanyannotations");
    Affirm.affirmEquals(String.format("Annotations added/removed? [%s]", pojoPackage), 2, pojoPackage.getAnnotations().size());

    final List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
    expectedAnnotations.add(SomeAnnotation.class);
    expectedAnnotations.add(AnotherAnnotation.class);
    for (final Annotation annotation : pojoPackage.getAnnotations()) {
      Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]", expectedAnnotations,
          annotation.annotationType()), expectedAnnotations.contains(annotation.annotationType()));
    }
  }

  @Test
  public void shouldReturnPackageName() {
    final String packageName = this.getClass().getPackage().getName();

    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(packageName);
    Affirm.affirmEquals("Mismatch in packageName!!", packageName, pojoPackage.getName());

  }

  @Test(expected = ReflectionException.class)
  public void shouldFailNoSuchPackage() {
    String randomPackageName = RandomFactory.getRandomValue(String.class);
    PojoPackageFactory.getPojoPackage(randomPackageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentException() {
    @SuppressWarnings("unused") final PojoPackage pojoPackage = new PojoPackageImpl(null);
  }

  @Test
  public void testtoString() {
    Assert.assertEquals("toString format changed?!", expectedToString, pojoPackage.toString());
  }
}
