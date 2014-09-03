/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.random.impl;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class EnumRandomGeneratorTest {

    @Test
    public void shouldDeclareRandomTypeAsEnum() {
        Affirm.affirmEquals("New types added / removed?", 1, EnumRandomGenerator.getInstance().getTypes().size());
        Affirm.affirmContains("Declared type must be Enum.class", Enum.class, EnumRandomGenerator.getInstance()
                .getTypes());
    }

    @Test
    public void shouldGenerateRandomEnum() {
        RandomGenerator randomGenerator = EnumRandomGenerator.getInstance();
        Enum someEnum = (Enum) randomGenerator.doGenerate(Enum.class);

        Affirm.affirmTrue("should never generate null", someEnum != null);

        Enum anotherEnum = (Enum) randomGenerator.doGenerate(Enum.class);

        try {
            Affirm.affirmFalse("Enum's should be different", someEnum.equals(anotherEnum));
        } catch (AssertionError error) {
            // on occasion they may be the same - 1% chance, try one more time.
            anotherEnum = (Enum) randomGenerator.doGenerate(Enum.class);
            Affirm.affirmFalse("Enum's should be different", someEnum.equals(anotherEnum));
        }
    }

    @Test
    public void endToEndTest() {
        Enum someEnum = RandomFactory.getRandomValue(Enum.class);
        Affirm.affirmNotNull("Should generate Enum", someEnum);
        Affirm.affirmTrue("Should use SomeEnum when generating", someEnum.getClass() == SomeEnum.class);
    }
}
