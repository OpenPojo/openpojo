/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.reflection.filters;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.sampleclasses.SampleEnum;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class FilterEnumTest extends IdentitiesAreEqual {

  private final String sampleClassesPackage = this.getClass().getPackage().getName() + ".sampleclasses";
  private final PojoClassFilter enumFilter = new FilterEnum();
  private final List<PojoClass> filteredPojoClasses = PojoClassFactory.getPojoClasses(sampleClassesPackage, enumFilter);
  private final int EXPECTED_FILTERED_COUNT = 5;

  private final List<PojoClass> nonFilteredPojoClasses = PojoClassFactory.getPojoClasses(sampleClassesPackage);
  private final int EXPECTED_NON_FILTERED_COUNT = 6;

  @Test
  public void shouldConfirmSampleClassCount() {
    Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", sampleClassesPackage), EXPECTED_NON_FILTERED_COUNT,
        nonFilteredPojoClasses.size());
    Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", sampleClassesPackage), EXPECTED_FILTERED_COUNT,
        filteredPojoClasses.size());
  }

  @Test
  public void shouldIncludeNonEnum() {
    for (PojoClass pojoClass : filteredPojoClasses) {
      Affirm.affirmFalse(String.format("[%s] should have been filtered out!!", pojoClass), pojoClass.isEnum());
    }
  }

  @Test
  public final void shouldExcludeEnum() {
    Affirm.affirmFalse(String.format("[%s] didn't exclude enum!!", enumFilter), enumFilter.include(PojoClassFactory
        .getPojoClass(SampleEnum.class)));

  }

  @Test
  public void shouldBeIdentityEqual() {
    FilterEnum instanceOne = new FilterEnum();
    FilterEnum instanceTwo = new FilterEnum();
    checkEqualityAndHashCode(instanceOne, instanceTwo);
  }

}
