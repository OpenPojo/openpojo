/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * <p>
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
     * @param prefixes the prefix list to use.
     * @param suffixes the sufix list to use
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
                Affirm.fail("Test class [" + pojoClass.getName() + "] does not start with " + prefixes.toString() + " or ends with " +
                        suffixes.toString());
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
