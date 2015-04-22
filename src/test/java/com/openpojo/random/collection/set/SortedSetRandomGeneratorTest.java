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
import java.util.SortedSet;
import java.util.TreeSet;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.ComparableType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;

/**
 * @author oshoukry
 */
public class SortedSetRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {

    @Override
    protected ParameterizableRandomGenerator getInstance() {
        return SortedSetRandomGenerator.getInstance();
    }

    @Override
    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return SortedSetRandomGenerator.class;
    }

    @Override
    protected Class<? extends Collection> getExpectedTypeClass() {
        return SortedSet.class;
    }

    @Override
    protected Class<? extends Collection> getGeneratedTypeClass() {
        return TreeSet.class;
    }

    @Override
    protected Class<?> getGenericType() {
        return ComparableType.class;
    }
}
