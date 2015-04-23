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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import com.openpojo.random.collection.util.BaseCollectionRandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SomeEnum;
import com.openpojo.random.util.Helper;
import com.openpojo.reflection.Parameterizable;

/**
 * @author oshoukry
 */
public class EnumSetRandomGenerator extends BaseCollectionRandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final Class<?>[] TYPES = new Class<?>[] { EnumSet.class };
    private static final EnumSetRandomGenerator INSTANCE = new EnumSetRandomGenerator();

    public static EnumSetRandomGenerator getInstance() {
        return INSTANCE;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    @Override
    protected Collection getBasicInstance(Class<?> type) {
        Helper.assertIsAssignableTo(type, getTypes());
        List<SomeEnum> someEnums = new ArrayList<SomeEnum>();
        for (int i = 0; i < CollectionHelper.MAX_RANDOM_ELEMENTS; i++) {
            someEnums.add(SomeEnum.values()[RANDOM.nextInt(SomeEnum.values().length - 1)]);
        }

        return EnumSet.copyOf(someEnums);
    }

    public Collection doGenerate(Class<?> type) {
        return getBasicInstance(type);
    }

    public Collection doGenerate(Parameterizable parameterizedType) {
        Helper.assertIsAssignableTo(parameterizedType.getType(), getTypes());
        return EnumSet.allOf((Class) parameterizedType.getParameterTypes().get(0));
    }

    private EnumSetRandomGenerator() {
    }
}
