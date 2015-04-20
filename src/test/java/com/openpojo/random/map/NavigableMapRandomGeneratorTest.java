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

package com.openpojo.random.map;

import java.util.Map;
import java.util.TreeMap;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.map.support.ComparableType1;
import com.openpojo.random.map.support.ComparableType2;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.BeforeClass;

/**
 * @author oshoukry
 */
public class NavigableMapRandomGeneratorTest extends AbstractMapRandomGeneratorTest {
    private static String JVM = System.getProperty("java.class.version");
    private static final String EXPECTED_TYPE_CLASS_NAME ="java.util.NavigableMap";
    private static Class<? extends Map> expectedTypeClass;
    private static boolean compatibleVM;


    @BeforeClass
    @SuppressWarnings("unchecked")
    public static void setExpectedTypeClass() {
        expectedTypeClass = (Class<? extends Map>) ClassUtil.loadClass(EXPECTED_TYPE_CLASS_NAME);
        compatibleVM = expectedTypeClass != null;
        System.out.println("JVM compatibility returned [" + compatibleVM + "] for class [" + EXPECTED_TYPE_CLASS_NAME + "]");
    }

    protected ParameterizableRandomGenerator getInstance() {
        return NavigableMapRandomGenerator.getInstance();
    }

    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return NavigableMapRandomGenerator.class;
    }

    @SuppressWarnings("unchecked")
    protected Class<? extends Map> getExpectedTypeClass() {
        return expectedTypeClass;
    }

    protected Class<? extends Map> getGeneratedTypeClass() {
        return TreeMap.class;
    }

    protected Class<?> getGenericType1() {
        return ComparableType1.class;
    }

    protected Class<?> getGenericType2() {
        return ComparableType2.class;
    }

    public void constructorShouldBePrivate() {
        super.constructorShouldBePrivate();
    }

    public void shouldOnlyReturnMapClassFromGetTypes() {
        if (compatibleVM)
            super.shouldOnlyReturnMapClassFromGetTypes();
    }

    public void shouldThrowExceptionForDoGenerateForOtherThanMapClass() {
        if (compatibleVM)
            super.shouldThrowExceptionForDoGenerateForOtherThanMapClass();
        throw RandomGeneratorException.getInstance("Not Suppported VM");
    }

    public void shouldGenerateParametrizableCorrectMapForRequest() {
        if (compatibleVM)
            super.shouldGenerateParametrizableCorrectMapForRequest();
    }

    public void endToEnd() {
        if (compatibleVM)
            super.endToEnd();
    }

    public void endToEndWithGenerics() {
        if (compatibleVM)
            super.endToEndWithGenerics();
    }

    public void shouldGenerateCorrectTypeMapForRequestedMap() {
        if (compatibleVM)
            super.shouldGenerateCorrectTypeMapForRequestedMap();
    }
}
