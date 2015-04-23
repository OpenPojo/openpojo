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

package com.openpojo.random.collection.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.management.relation.RoleList;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.collection.support.ALeafChildClass;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.util.SomeRole;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class RoleListRandomGeneratorTest {
    private final RoleListRandomGenerator randomGenerator = RoleListRandomGenerator.getInstance();
    private final Class<RoleList> expectedTypeClass = RoleList.class;

    @Test
    public void constructorShouldBePrivate() {
        PojoClass randomGeneratorPojo = PojoClassFactory.getPojoClass(randomGenerator.getClass());

        List<PojoMethod> constructors = new ArrayList<PojoMethod>();

        for (PojoMethod constructor : randomGeneratorPojo.getPojoConstructors()) {
            if (!constructor.isSynthetic())
                constructors.add(constructor);
        }
        Assert.assertEquals("Should only have one constructor [" + randomGeneratorPojo.getPojoConstructors() + "]", 1, constructors.size());

        PojoMethod constructor = constructors.get(0);

        Assert.assertTrue(constructor.isPrivate());
    }

    @Test
    public void shouldBeAbleToCreate() {
        Assert.assertEquals(RoleListRandomGenerator.class, randomGenerator.getClass());
    }

    @Test
    public void shouldOnlyReturnCollectionClassFromGetTypes() {
        Collection<Class<?>> types = randomGenerator.getTypes();
        Assert.assertNotNull("Should not be null", types);
        Assert.assertEquals("Should only have one type", 1, types.size());
        Assert.assertEquals("Should only be " + expectedTypeClass.getName(), expectedTypeClass, types.iterator().next());
    }

    @Test
    public void generatedTypeShouldBeAssignableToDeclaredType() {
        Class<?> declaredType = randomGenerator.getTypes().iterator().next();
        Object generatedInstance = randomGenerator.doGenerate(declaredType);
        Assert.assertTrue("[" + declaredType.getName() + " is not assignable to " + generatedInstance.getClass().getName() + "]",
                declaredType.isAssignableFrom(generatedInstance.getClass()));
    }

    @Test(expected = RandomGeneratorException.class)
    public void shouldThrowExceptionForDoGenerateForOtherThanCollectionClass() {
        randomGenerator.doGenerate(ALeafChildClass.class);
    }

    @Test
    public void shouldGenerateCorrectTypeCollectionForRequestedCollection() {
        Collection someObject = randomGenerator.doGenerate(expectedTypeClass);
        Assert.assertNotNull("Should not be null", someObject);
        Assert.assertEquals("Should be a " + expectedTypeClass.getName(), expectedTypeClass, someObject.getClass());
        Assert.assertTrue("Should not be Empty", someObject.size() > 0);
    }

    @Test
    public void endToEnd() {
        Collection<?> generatedCollection = RandomFactory.getRandomValue(expectedTypeClass);
        assertCollectionHasExpectedTypes(generatedCollection, SomeRole.class);
    }

    protected void assertCollectionHasExpectedTypes(Collection<?> generatedCollection, Class<?> type) {
        Assert.assertNotNull("Should not be null", generatedCollection);
        Assert.assertEquals( expectedTypeClass, generatedCollection.getClass());
        Assert.assertTrue("Should not be empty", generatedCollection.size() > 0);
        for (Object entry : generatedCollection) {
            Assert.assertNotNull("Should not be null", entry);
            Assert.assertEquals("Entry should be " + type.getName(), type, entry.getClass());
        }
    }

}
