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

import java.util.EnumMap;
import java.util.Map;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.impl.SomeEnum;
import com.openpojo.random.map.support.EnumType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.random.map.util.BaseMapRandomGeneratorTest;

/**
 * @author oshoukry
 */
public class EnumMapRandomGeneratorTest extends BaseMapRandomGeneratorTest {

    protected EnumMapRandomGenerator getInstance() {
        return EnumMapRandomGenerator.getInstance();
    }

    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return EnumMapRandomGenerator.class;
    }

    protected Class<? extends Map> getExpectedTypeClass() {
        return EnumMap.class;
    }

    protected Class<? extends Map> getGeneratedTypeClass() {
        return EnumMap.class;
    }

    protected Class<?> getDefaultType1() {
        return SomeEnum.class;
    }

    @Override
    protected Class<?> getGenericType1() {
        return EnumType1.class;
    }

    @Override
    protected Class<?> getGenericType2() {
        return SimpleType2.class;
    }

}
