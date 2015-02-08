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

package com.openpojo.issues.issue45;

import java.lang.reflect.TypeVariable;
import java.util.List;

import com.openpojo.issues.issue45.sample.AClassWithGenericArray;
import com.openpojo.issues.issue45.sample.AClassWithGenericCollection;
import com.openpojo.issues.issue45.sample.AParameterizedClass;
import com.openpojo.issues.issue45.sample.ClassWithVariousGenericSetList;
import com.openpojo.issues.issue45.sample.SomeGeneric;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author oshoukry
 */
public class TestCase {

    private PojoClass aClassWithGenericCollectionPojo;
    private PojoClass aParameterizedClass;
    private PojoValidator pojoValidator;

    @Before
    public void setUp() throws Exception {
        aClassWithGenericCollectionPojo = PojoClassFactory.getPojoClass(AClassWithGenericCollection.class);
        aParameterizedClass = PojoClassFactory.getPojoClass(AParameterizedClass.class);

        pojoValidator = new PojoValidator();
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
    }

    @Test
    public void ensureAClassWithGenericCollectionPojo_HasNotBeenModified() {
        Assert.assertThat("Fields added / removed to sample class?", aClassWithGenericCollectionPojo.getPojoFields().size(), is(1));
        PojoField pojoField = aClassWithGenericCollectionPojo.getPojoFields().get(0);
        Assert.assertEquals(List.class, pojoField.getType());
        Assert.assertThat("Field should have only one parameter", pojoField.getParameterTypes().size(), is(1));
        Assert.assertEquals(SomeGeneric.class, pojoField.getParameterTypes().get(0));
    }

    @Test
    public void ensureAParameterizedClass_HasNotBeenModified() {
        Assert.assertThat(aParameterizedClass.getPojoFields().size(), is(1));
        Assert.assertThat(aParameterizedClass.getClazz().getTypeParameters().length, is(1));
        Assert.assertTrue(TypeVariable.class.isAssignableFrom(aParameterizedClass.getClazz().getTypeParameters()[0].getClass()));
    }

    @Test
    public void whenAClassHasGenericCollection_RandomGeneratorShouldPopulateProperly() {
        pojoValidator.runValidation(aClassWithGenericCollectionPojo);
    }

    @Test
    public void testAParameterizedClass() {
        pojoValidator.runValidation(aParameterizedClass);
    }

    @Test
    public void testAClassWithGenericArray() {
        pojoValidator.runValidation(PojoClassFactory.getPojoClass(AClassWithGenericArray.class));
    }

    @Test
    public void testEndToEndCollections() {
        pojoValidator = new PojoValidator();
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.runValidation(PojoClassFactory.getPojoClass(ClassWithVariousGenericSetList.class));
    }

}
