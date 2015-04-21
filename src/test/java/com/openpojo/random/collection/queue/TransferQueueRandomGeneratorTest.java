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

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.SimpleType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Assume;
import org.junit.Before;

/**
 * @author oshoukry
 */
public class TransferQueueRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {
    private static final String EXPECTED_TYPE_CLASS_NAME = "java.util.concurrent.TransferQueue";
    private static final String GENERATED_TYPE_CLASS_NAME = "java.util.concurrent.LinkedTransferQueue";

    @Before
    public void requirement() {
        Assume.assumeTrue(EXPECTED_TYPE_CLASS_NAME + " is not loaded, skipping test", ClassUtil.isClassLoaded(EXPECTED_TYPE_CLASS_NAME));
        Assume.assumeTrue(GENERATED_TYPE_CLASS_NAME + " is not loaded, skipping test", ClassUtil.isClassLoaded(GENERATED_TYPE_CLASS_NAME));
    }

    @Override
    protected ParameterizableRandomGenerator getInstance() {
        return TransferQueueRandomGenerator.getInstance();
    }

    @Override
    protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
        return TransferQueueRandomGenerator.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<? extends Collection> getExpectedTypeClass() {
        return (Class<? extends Collection>) ClassUtil.loadClass(EXPECTED_TYPE_CLASS_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Class<? extends Collection> getGeneratedTypeClass() {
        return (Class<? extends Collection>) ClassUtil.loadClass(GENERATED_TYPE_CLASS_NAME);
    }

    @Override
    protected Class<?> getGenericType() {
        return SimpleType.class;
    }
}
