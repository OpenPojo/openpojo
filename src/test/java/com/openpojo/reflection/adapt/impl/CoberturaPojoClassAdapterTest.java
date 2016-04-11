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

package com.openpojo.reflection.adapt.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.impl.sampleclasses.CoberturaInstrumentedClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class CoberturaPojoClassAdapterTest {

  PojoClass coberturaInstrumentedPojoClass = PojoClassFactory.getPojoClass(CoberturaInstrumentedClass.class);
  PojoClass coberturaCleanedPojoClass = CoberturaPojoClassAdapter.getInstance().adapt(coberturaInstrumentedPojoClass);

  @Test
  public void ensureCoberturaInstrumentedClassNotChanged() {
    Affirm.affirmEquals("Fields added/removed?", 4, coberturaInstrumentedPojoClass.getPojoFields().size());
    Affirm.affirmEquals("Methods added/removed?", 4, coberturaInstrumentedPojoClass.getPojoMethods().size());
  }

  @Test
  public void shouldSkipFieldsStartingWith__cobertura_() {
    Affirm.affirmEquals("Cobertura fields not filtered?", 2, coberturaCleanedPojoClass.getPojoFields().size());
  }

  @Test
  public void shouldSkipMethodsStartingWith__covertura_() {
    Affirm.affirmEquals("Cobertura methods not filtered?", 3, coberturaCleanedPojoClass.getPojoMethods().size());
  }

}
