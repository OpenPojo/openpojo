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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;


/**
 * @author oshoukry
 */
public class TestsMustBeNamedTestOrTestSuiteRule implements Rule {

    public static final String[] TOKENS = { "Test", "TestCase" };
    private String annotations[] = { "org.testng.annotations.Test", "org.junit.Test" };

    public TestsMustBeNamedTestOrTestSuiteRule() {
    }

    @SuppressWarnings("unchecked")
    public void evaluate(PojoClass pojoClass) {
        if (!pojoClass.isConcrete() || containsToken(pojoClass))
            return;

        for (String annotation : annotations) {
            Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) ClassUtil.loadClass(annotation);
            if (annotation !=null && isAnnotatedOrParentAnnotated(pojoClass, annotationClass)) {
                Affirm.fail("Class [" + pojoClass.getName() + "] does not end with 'Test' but has methods or inherits methods annotated with " +

                        "[" + annotationClass.getName() + "]");
            }
        }
    }
    private boolean containsToken(PojoClass pojoClass) {
        for (String token : TOKENS) {
            if (getClassName(pojoClass).contains(token))
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
