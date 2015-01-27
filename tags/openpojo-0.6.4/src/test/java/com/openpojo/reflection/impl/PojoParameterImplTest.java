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

package com.openpojo.reflection.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

/**
 * @author oshoukry
 */
public class PojoParameterImplTest {
    @Test
    public void isParameterized() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithParameterizedConstructors.class);

        Assert.assertTrue(pojoClass.isNestedClass());

        for (PojoMethod constructor: pojoClass.getPojoConstructors()) {
            if (!constructor.isSynthetic()) {
                List<PojoParameter> pojoParameters = constructor.getPojoParameters();
                Assert.assertThat(pojoParameters.size(), is(greaterThan(1)));
                for (int i = 1; i < pojoParameters.size(); i++) {
                    System.out.println(pojoParameters.get(i));
                    PojoParameter parameter = pojoParameters.get(i);
                    Assert.assertThat(parameter.isParameterized(), is(Matchers.equalTo(true)));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private class AClassWithParameterizedConstructors {
        public AClassWithParameterizedConstructors(List<? extends Collection> aWildType) {}
        public AClassWithParameterizedConstructors(Map<? super Collection, ?> aWildType) {}
        public AClassWithParameterizedConstructors(ParameterizedClass<List<String>> another) {}
        public AClassWithParameterizedConstructors(Queue<?> aMap) {}
        public AClassWithParameterizedConstructors(Class<String> aClass) {}
    }

    @SuppressWarnings("unused")
    private static class ParameterizedClass<T> {
        public ParameterizedClass(T someT) {}
    }
}
