/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.validation.affirm;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 */
public class TestNGAssertAffirmationTest extends AbstractAffirmation {

    private final Affirmation testNGAssertAffirmation = (Affirmation) InstanceFactory.getInstance(PojoClassFactory
            .getPojoClass(TestNGAssertAffirmation.class));

    @Override
    public Affirmation getAffirmation() {
        return testNGAssertAffirmation;
    }

    @Test
    public void shouldTestToString() {
        // com.openpojo.validation.affirm.TestNGAssertAffirmation [@1c93d6bc: ]
        Assert.assertTrue(String.format("[%s] signature changed? expected=[%s]!!", TestNGAssertAffirmation.class
                                        .getName(), "com.openpojo.validation.affirm.TestNGAssertAffirmation [@xxxxxx: ]"),
                                        testNGAssertAffirmation.toString()
                                                          .startsWith("com.openpojo.validation.affirm.TestNGAssertAffirmation [@")
                                                          && testNGAssertAffirmation.toString().endsWith(": ]"));
    }
}
