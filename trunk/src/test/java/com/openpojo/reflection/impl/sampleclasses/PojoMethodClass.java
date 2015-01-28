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

package com.openpojo.reflection.impl.sampleclasses;

import com.openpojo.reflection.impl.sampleannotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;


/**
 * @author oshoukry
 *
 */
public final class PojoMethodClass {

    @SomeAnnotation
    public void methodWithAnnotation() {
    }

    @SomeAnnotation
    @AnotherAnnotation
    public void methodWithMultipleAnnotations() {

    }

    public void methodWithoutAnnotation() {

    }

    public final void finalMethod() {

    }

    public void nonFinalMethod() {

    }

    @SuppressWarnings("unused")
    private void privateMethod() {

    }

    public void nonPrivateMethod() {

    }

    protected void protectedMethod() {

    }

    void nonProtectedMethod() {

    }

    public void publicMethod() {

    }

    void nonPublicMethod() {

    }


    public static void staticMethod() {

    }

    public void nonStaticMethod() {

    }

    public void isNotSyntheticMethod() {

    }
}
