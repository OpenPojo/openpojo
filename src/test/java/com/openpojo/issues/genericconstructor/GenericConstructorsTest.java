/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.issues.genericconstructor;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author oshoukry
 *
 *         TODO: Add Generic support specially for constructors, sample code bellow is able to interrogate generics.
 */
public class GenericConstructorsTest {

    private List<PojoClass> pojoClasses;
    private String samplePackage;

    @Before
    public void setup() {
        samplePackage = this.getClass().getPackage().getName() + ".sample";
        pojoClasses = PojoClassFactory.getPojoClasses(samplePackage);
    }

    @Test
    @Ignore
    public void playWithConstructors() {
        for (final PojoClass pojoClass : pojoClasses) {
            final Class<?> rawClass = pojoClass.getClazz();
            System.out.println("Class = " + rawClass);
            for (final Constructor<?> constructor : rawClass.getConstructors()) {
                System.out.println("\tconstructor = [" + constructor + "]");
                for (final Type type : constructor.getGenericParameterTypes()) {
                    System.out.println("\t\t param=[" + type + "]");
                    if (type instanceof ParameterizedType) {
                        final Type[] parameterizedTypes;
                        parameterizedTypes = ((ParameterizedType) type).getActualTypeArguments();
                        System.out.println(MessageFormatter.format("\t\t\t type=[{0}]", (Object) parameterizedTypes));
                    }
                }

            }
        }
    }

    @Test
    @Ignore
    public void shouldConstructClasses() {
        for (final PojoClass pojoClass : pojoClasses) {
            try {
                final Object instance = InstanceFactory.getLeastCompleteInstance(pojoClass);
                Affirm.affirmNotNull(MessageFormatter.format("Failed to construct PojoClass=[{0}]", pojoClass),
                                     instance);
            } catch (final ReflectionException re) {
                Affirm.fail(MessageFormatter.format("Failed to construct PojoClass=[{0}], Exception=[{1}]", pojoClass,
                                                    re.getCause()));
            }
        }
    }

}
