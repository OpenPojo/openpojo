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

package com.openpojo.random.collection.set;

import java.util.Collection;
import java.util.EnumSet;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.EnumType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;
import com.openpojo.random.util.SomeEnum;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class EnumSetRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {

    @Override
    protected ParameterizableRandomGenerator getInstance() {
        return EnumSetRandomGenerator.getInstance();
    }

    @Override
    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return EnumSetRandomGenerator.class;
    }

    @Override
    protected Class<? extends Collection> getExpectedTypeClass() {
        return EnumSet.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<? extends Collection> getGeneratedTypeClass() {
        return (Class<? extends Collection>) ClassUtil.loadClass("java.util.RegularEnumSet");
    }

    @Override
    protected Class<?> getDefaultType() {
        return SomeEnum.class;
    }

    @Override
    protected Class<?> getGenericType() {
        return EnumType.class;
    }
}
