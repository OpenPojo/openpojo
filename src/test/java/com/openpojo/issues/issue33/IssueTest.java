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

package com.openpojo.issues.issue33;

import com.openpojo.issues.issue33.sample.AbstractClassRandomGenerator;
import com.openpojo.issues.issue33.sample.ClassAggregatingAbstractClass;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

    private Validator pojoValidator;
    private PojoClass pojoClass;

    @Before
    public void setUp() throws Exception {
        pojoClass = PojoClassFactory.getPojoClass(ClassAggregatingAbstractClass.class);
        RandomGeneratorService randomGeneratorService = ServiceRegistrar.getInstance().getRandomGeneratorService();
        randomGeneratorService.registerRandomGenerator(new AbstractClassRandomGenerator());
    }

    @Test
    public void testGetter() {
        pojoValidator = ValidatorBuilder.create().with(new GetterTester()).build();
        pojoValidator.validate(pojoClass);
    }

    @Test
    public void testSetter() {
        pojoValidator = ValidatorBuilder.create().with(new SetterTester()).build();
        pojoValidator.validate(pojoClass);
    }


}
