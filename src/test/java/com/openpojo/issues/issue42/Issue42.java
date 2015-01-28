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

package com.openpojo.issues.issue42;

import com.openpojo.issues.issue42.sample.AClassWithArrayField;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.affirm.Affirmation;
import com.openpojo.validation.affirm.AffirmationFactory;
import com.openpojo.validation.affirm.JUnitAssertAffirmation;
import com.openpojo.validation.test.impl.GetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class Issue42 {

    @Test
    public void testByteArrayEquality() {

        byte [] first = new byte[] { (byte) 0xaa, (byte) 0xab, (byte) 0xac };
        byte [] second = first.clone();

        Affirm.affirmEquals("A clone failed to be seen as equal", first, second);
    }

    @Test
    public void testAClassWithArrayField() {
        Affirmation jUnitAffirmation = (Affirmation) InstanceFactory.getInstance(PojoClassFactory.getPojoClass(JUnitAssertAffirmation
                .class));
        AffirmationFactory.getInstance().setActiveAffirmation(jUnitAffirmation);
        PojoClass classWithArrayField = PojoClassFactory.getPojoClass(AClassWithArrayField.class);
        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addTester(new GetterTester());
        pojoValidator.runValidation(classWithArrayField);
    }
}
