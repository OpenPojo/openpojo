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

package com.openpojo.issues.unmodifiableCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

public class CollectionsTest {

  @Test
  public void testUnmodifiableCollectionsGetterReturned() {
    Validator pojoValidator = ValidatorBuilder.create()
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    PojoClass pojoClass = PojoClassFactory.getPojoClass(CollectionContainingClass.class);
    pojoValidator.validate(pojoClass);
  }

  private static class CollectionContainingClass {
    private List<String> values = new ArrayList<String>();

    @SuppressWarnings("unused")
    public void setValues(List<String> values) {
      this.values = values;
    }

    @SuppressWarnings("unused")
    public List<String> getValues() {
      return Collections.unmodifiableList(values);
    }

  }
}
