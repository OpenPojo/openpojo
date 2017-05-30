/*
 * Copyright (c) 2010-2017 Osman Shoukry
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

package com.openpojo.reflection.java.packageloader.boot;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

/**
 * @author oshoukry
 */
public class JavaBootClassLoaderTest {

  private static final int MIN_EXPECTED_TOTAL_CLASSES = 20000;
  private JavaBootClassLoader javaBootClassLoader;


  @Before
  public void setup() {
    javaBootClassLoader = JavaBootClassLoader.getInstance();
  }

  @Test
  public void onlyPrivateConstructors() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(JavaBootClassLoader.class);
    for (PojoMethod constructor : pojoClass.getPojoConstructors())
      Assert.assertThat(constructor.isPrivate(), is(true));
  }

  @Test
  public void canGetInstance() {
    JavaBootClassLoader instance = JavaBootClassLoader.getInstance();
    Assert.assertThat(instance, notNullValue());
  }

  @Test
  public void whenPackageNameIsNullReturnEmptyClassSet() {
    Set<Type> classes = javaBootClassLoader.getTypesInPackage(null);
    Assert.assertThat(classes, notNullValue());
    Assert.assertThat(classes.size(), is(0));
  }

  @Test
  public void whenPackageNameIsEmptyReturnEmptyClassSet() {
    Set<Type> classes = javaBootClassLoader.getTypesInPackage("");
    Assert.assertThat(classes, notNullValue());
    Assert.assertThat(classes.size(), is(0));
  }

  @Test
  public void defaultIs_sun_boot_class_path() {
    Set<String> classPathKeys = javaBootClassLoader.getClassPathKeys();
    Assert.assertThat(classPathKeys, notNullValue());
    Assert.assertThat(classPathKeys.size(), is(1));
    Assert.assertThat(classPathKeys.iterator().next(), is("sun.boot.class.path"));
  }

  @Test
  public void canGetAllClassNamesInBootClassPath() {
    Set<String> classNames = javaBootClassLoader.getBootClassNames();
    Assert.assertThat(classNames, notNullValue());
    Assert.assertThat(classNames.size(), greaterThan(MIN_EXPECTED_TOTAL_CLASSES));
  }

  @Test
  public void canLoadAllClassesInJavaUtilConcurrent() {
    String concurrentPackageName = AtomicInteger.class.getPackage().getName();
    Set<Type> classesInPackage = javaBootClassLoader.getTypesInPackage(concurrentPackageName);
    Assert.assertThat(classesInPackage.size(), greaterThan(20));
  }

  @Test
  public void canGetPackageNamesUnderGivenPackageName() {
    Set<String> subPackages = javaBootClassLoader.getSubPackagesFor("java");
    Assert.assertThat(subPackages, notNullValue());
    Assert.assertThat(subPackages.size(), greaterThan(10));
  }

  @Test
  public void willReturnTrueForJavaPackageExists() {
    Assert.assertThat(javaBootClassLoader.hasPackage("java"), is(true));
    Assert.assertThat(javaBootClassLoader.hasPackage("javax"), is(true));
    Assert.assertThat(javaBootClassLoader.hasPackage("com.sun"), is(true));
    Assert.assertThat(javaBootClassLoader.hasPackage("com.openpojo"), is(false));
  }

  @Test
  public void end2end() {
    String concurrentPackageName = AtomicInteger.class.getPackage().getName();
    List<PojoClass> concurrentclasses = PojoClassFactory.getPojoClasses(concurrentPackageName);
    Assert.assertThat(concurrentclasses.size(), greaterThan(20));
  }

  @Test
  public void end2end_shouldLoadAllClassesInJavaLang() {
    List<PojoClass> types = PojoClassFactory.getPojoClassesRecursively("java.lang", null);
    checkListOfPojoClassesContains(types, java.lang.Class.class);
    checkListOfPojoClassesContains(types, java.lang.CharSequence.class);
    checkListOfPojoClassesContains(types, java.lang.Runnable.class);
    checkListOfPojoClassesContains(types, java.lang.Throwable.class);
    checkListOfPojoClassesContains(types, java.lang.Double.class);
    checkListOfPojoClassesContains(types, java.lang.Float.class);
    checkListOfPojoClassesContains(types, java.lang.Object.class);
    checkListOfPojoClassesContains(types, java.lang.Error.class);

    Assert.assertThat(types.size(), greaterThan(300));
  }

  private void checkListOfPojoClassesContains(List<PojoClass> types, Class<?> expectedClass) {
    Assert.assertTrue("Expected type [" + expectedClass.getName() + "] not found",
        types.contains(PojoClassFactory.getPojoClass(expectedClass)));
  }

  @Test
  public void end2endLoadAllClassesInTheVM() {
    List<PojoClass> types = PojoClassFactory.getPojoClassesRecursively("", null);
    System.out.println("Loaded " + types.size() + " classes");
    Assert.assertTrue(types.contains(PojoClassFactory.getPojoClass(this.getClass())));
    Assert.assertThat(types.size(), greaterThan(MIN_EXPECTED_TOTAL_CLASSES));
    checkListOfPojoClassesContains(types, java.rmi.registry.LocateRegistry.class);
  }

}