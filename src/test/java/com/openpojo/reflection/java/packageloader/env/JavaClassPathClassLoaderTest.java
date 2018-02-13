/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.reflection.java.packageloader.env;

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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;

/**
 * @author oshoukry
 */
public class JavaClassPathClassLoaderTest {

  private Integer minExpectedTotalClasses;
  private JavaClassPathClassLoader javaClassPathClassLoader;
  private int minJavaUtilConcurrentAtomicCount;
  private int minJavaLangClasses;
  private int minPackageCountUnderJava;

  private static final String JAVA_VERSION = System.getProperty("java.version");

  @Before
  public void setup() {
    javaClassPathClassLoader = JavaClassPathClassLoader.getInstance();
    if (JAVA_VERSION.startsWith("1.8")) {
      minExpectedTotalClasses = 20000;
      minJavaUtilConcurrentAtomicCount = 30;
      minJavaLangClasses = 400;
      minPackageCountUnderJava = 13;
    } else {
      minExpectedTotalClasses = 16000;
      minJavaUtilConcurrentAtomicCount = 17;
      minJavaLangClasses = 230;
      minPackageCountUnderJava = 10;
    }
  }

  @Test
  public void onlyPrivateConstructors() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(JavaClassPathClassLoader.class);
    for (PojoMethod constructor : pojoClass.getPojoConstructors())
      Assert.assertThat(constructor.isPrivate(), is(true));
  }

  @Test
  public void shouldNotThrowExceptionOnInvalidClassPathProperty() {
    JavaClassPathClassLoader instance = JavaClassPathClassLoader.getInstance("InvalidClassPathPropertyName");
    Assert.assertThat(instance, notNullValue());
    Assert.assertThat(instance.getClassNames().size(), is(0));
  }

  @Test
  public void canGetInstance() {
    JavaClassPathClassLoader instance = JavaClassPathClassLoader.getInstance();
    Assert.assertThat(instance, notNullValue());
  }

  @Test
  public void whenPackageNameIsNullReturnEmptyClassSet() {
    Set<Type> classes = javaClassPathClassLoader.getTypesInPackage(null);
    Assert.assertThat(classes, notNullValue());
    Assert.assertThat(classes.size(), is(0));
  }

  @Test
  public void defaultClassPathVars() {
    String[] expectedClassPathKeys = { "java.library.path", "java.class.path", "java.ext.dirs", "sun.boot.class.path" };

    Set<String> classPathKeys = javaClassPathClassLoader.getClassPathKeys();

    Assert.assertThat(classPathKeys, notNullValue());
    Assert.assertThat(classPathKeys.size(), is(expectedClassPathKeys.length));
    Assert.assertThat(classPathKeys, containsInAnyOrder(expectedClassPathKeys));
  }

  @Test
  public void canGetAllClassNamesInBootClassPath() {
    Set<String> classNames = javaClassPathClassLoader.getClassNames();
    Assert.assertThat(classNames, notNullValue());
    Assert.assertThat(classNames.size(), greaterThan(minExpectedTotalClasses));
  }

  @Test
  public void canLoadAllClassesInJavaUtilConcurrentAtomic() {
    String concurrentPackageName = AtomicInteger.class.getPackage().getName();
    Set<Type> classesInPackage = javaClassPathClassLoader.getTypesInPackage(concurrentPackageName);
    Assert.assertThat(classesInPackage.size(), greaterThan(minJavaUtilConcurrentAtomicCount));
  }

  @Test
  public void canGetPackageNamesUnderGivenPackageName() {
    Set<String> subPackages = javaClassPathClassLoader.getSubPackagesFor("java");
    Assert.assertThat(subPackages, notNullValue());
    Assert.assertThat(subPackages.size(), greaterThan(minPackageCountUnderJava));
  }

  @Test
  public void willReturnTrueForJavaPackageExists() {
    Assert.assertThat(javaClassPathClassLoader.hasPackage("java"), is(true));
    Assert.assertThat(javaClassPathClassLoader.hasPackage("javax"), is(true));
    Assert.assertThat(javaClassPathClassLoader.hasPackage("com.sun"), is(true));
    Assert.assertThat(javaClassPathClassLoader.hasPackage("com.openpojo"), is(false));
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

    Assert.assertThat(types.size(), greaterThan(minJavaLangClasses));
  }

  private void checkListOfPojoClassesContains(List<PojoClass> types, Class<?> expectedClass) {
    Assert.assertTrue("Expected type [" + expectedClass.getName() + "] not found",
        types.contains(PojoClassFactory.getPojoClass(expectedClass)));
  }

  @Test
  public void end2endLoadAllClassesInTheVM() {
    List<PojoClass> types = PojoClassFactory.getPojoClassesRecursively("", null);
    Assert.assertTrue(types.contains(PojoClassFactory.getPojoClass(this.getClass())));
    final String reason = "Loaded " + types.size() + " classes instead of expected " + minExpectedTotalClasses;
    Assert.assertThat(reason, types.size(), greaterThan(minExpectedTotalClasses));
    checkListOfPojoClassesContains(types, java.rmi.registry.LocateRegistry.class);
  }

}