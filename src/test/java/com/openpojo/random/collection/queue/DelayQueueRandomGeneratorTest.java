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

package com.openpojo.random.collection.queue;

import java.util.Collection;
import java.util.concurrent.DelayQueue;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.DelayedType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;
import com.openpojo.random.util.ComparableDelayed;

/**
 * @author oshoukry
 */
public class DelayQueueRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {
    @Override
    protected ParameterizableRandomGenerator getInstance() {
        return DelayQueueRandomGenerator.getInstance();
    }

    @Override
    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return DelayQueueRandomGenerator.class;
    }

    @Override
    protected Class<? extends Collection> getExpectedTypeClass() {
        return DelayQueue.class;
    }

    @Override
    protected Class<? extends Collection> getGeneratedTypeClass() {
        return getExpectedTypeClass();
    }

    protected Class<?> getDefaultType() {
        return ComparableDelayed.class;
    }

    @Override
    protected Class<?> getGenericType() {
        return DelayedType.class;
    }
}
