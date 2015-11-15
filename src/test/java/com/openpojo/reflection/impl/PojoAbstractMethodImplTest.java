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

package com.openpojo.reflection.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.sampleclasses.AnAbstractClassEmpty;
import com.openpojo.reflection.impl.sampleclasses.AnAbstractClassWithOneAbstractMethod;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class PojoAbstractMethodImplTest {

  @Test
  public void shouldFindNoAbstractMethods() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AnAbstractClassEmpty.class);
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      Assert.assertTrue(pojoMethod.isConstructor());
      Assert.assertFalse(pojoMethod.isAbstract());
    }

    Assert.assertEquals(1, pojoClass.getPojoMethods().size());
  }

  @Test
  public void shouldfindOneAbstractMethod() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AnAbstractClassWithOneAbstractMethod.class);
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (!pojoMethod.isConstructor())
        Assert.assertTrue(pojoMethod.isAbstract());
    }

    Assert.assertEquals(1 + 1 /* constructor */, pojoClass.getPojoMethods().size());
  }
}
