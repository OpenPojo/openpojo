/*
 * Copyright (c) 2010-2014 Osman Shoukry
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
