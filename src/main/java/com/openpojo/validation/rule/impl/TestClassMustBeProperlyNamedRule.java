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

package com.openpojo.validation.rule.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;


/**
 * There are three default accepted naming schemes for test classes.
 * <ul>
 * <li> Test ends with Test
 * <li> Test begins with Test
 * <li> Test ends with TestCase
 * </ul>
 *
 * To override the accepted list use the {@link #TestClassMustBeProperlyNamedRule(Collection prefix, Collection postfix)
 * TestClassMustBeProperlyNamedRule}
 *
 * @author oshoukry
 */
public class TestClassMustBeProperlyNamedRule implements Rule {

  public static final String[] DEFAULT_PREFIX_TOKENS = { "Test" };
  public static final String[] DEFAULT_SUFFIX_TOKENS = { "Test", "TestCase" };

  private final Collection<String> prefixes;
  private final Collection<String> suffixes;

  public static final String DEFAULT_ANNOTATIONS[] = { "org.testng.annotations.Test", "org.junit.Test" };
  private final Collection<Class<? extends Annotation>> loadedAnnotations = new ArrayList<Class<? extends Annotation>>();

  /**
   * This constructor used the default is prefix "Test", suffixes "Test" &amp; "TestCase".
   */
  public TestClassMustBeProperlyNamedRule() {
    this(Arrays.asList(DEFAULT_PREFIX_TOKENS), Arrays.asList(DEFAULT_SUFFIX_TOKENS));
  }

  /**
   * This constructor enables you to override the prefix / postfixes for the test names.
   * The default is prefix is Test, suffixes *Test or *TestCase
   *
   * @param prefixes
   *     the prefix list to use.
   * @param suffixes
   *     the sufix list to use
   */
  @SuppressWarnings("unchecked")
  public TestClassMustBeProperlyNamedRule(Collection<String> prefixes, Collection<String> suffixes) {
    this.prefixes = prefixes;
    this.suffixes = suffixes;
    for (String annotation : DEFAULT_ANNOTATIONS) {
      Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) ClassUtil.loadClass(annotation);
      if (annotationClass != null) {
        loadedAnnotations.add(annotationClass);
      }
    }

    if (loadedAnnotations.size() == 0)
      throw new IllegalStateException("No annotations loaded, exptected any of " + Arrays.toString(DEFAULT_ANNOTATIONS));
  }

  @SuppressWarnings("unchecked")
  public void evaluate(PojoClass pojoClass) {
    if (!pojoClass.isConcrete() || properlyNamed(pojoClass))
      return;

    for (Class<? extends Annotation> annotation : loadedAnnotations) {
      if (isAnnotatedOrParentAnnotated(pojoClass, annotation)) {
        Affirm.fail("Test class [" + pojoClass.getName() + "] does not start with " + prefixes.toString() + " or ends with "
            + suffixes.toString());
      }
    }
  }

  private boolean properlyNamed(PojoClass pojoClass) {
    String simpleClassName = getClassName(pojoClass);

    for (String token : prefixes) {
      if (simpleClassName.startsWith(token))
        return true;
    }

    for (String token : suffixes) {
      if (simpleClassName.endsWith(token))
        return true;
    }
    return false;
  }

  private String getClassName(PojoClass pojoClass) {
    return pojoClass.getClazz().getSimpleName();
  }

  private boolean isAnnotatedOrParentAnnotated(PojoClass pojoClass, Class<? extends Annotation> testAnnotation) {
    if (pojoClass == null)
      return false;

    //Class level annotation
    if (pojoClass.getAnnotation(testAnnotation) != null)
      return true;

    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (pojoMethod.getAnnotation(testAnnotation) != null)
        return true;
    }

    return (isAnnotatedOrParentAnnotated(pojoClass.getSuperClass(), testAnnotation));
  }

}
