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

package com.openpojo.random.dynamic;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.dynamic.sampleclasses.AConcreteClass;
import com.openpojo.random.dynamic.sampleclasses.ASimpleInterface;
import com.openpojo.random.dynamic.sampleclasses.AnAbstractClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.validation.affirm.Affirm;

public class RandomInstanceFromInterfaceRandomGeneratorTest {

    RandomInstanceFromInterfaceRandomGenerator proxyGenerator;
    ASimpleInterface aSimpleInterface;

    @Before
    public void setup() {
        proxyGenerator = RandomInstanceFromInterfaceRandomGenerator.getInstance();
        aSimpleInterface = proxyGenerator.doGenerate(ASimpleInterface.class);
    }

    @Test
    public void shouldReturnAProxy() {
        Affirm.affirmNotNull("Interface instance not generated", aSimpleInterface);
    }

    @Test
    public void shouldReturnANewInstanceEveryTime() {
        Affirm.affirmFalse("Same instance returned or faulty equality implementation on proxy", aSimpleInterface
                .equals(proxyGenerator.doGenerate(ASimpleInterface.class)));
    }

    @Test
    public void shouldReturnRandomNonNullValuesForInterfaceMethods() {
        final ASimpleInterface aSimpleInterface = proxyGenerator.doGenerate(ASimpleInterface.class);

        Affirm.affirmNotNull("Generated proxy getName() returned null", aSimpleInterface.getName());

        final String name = aSimpleInterface.getName();
        final String otherName = aSimpleInterface.getName();
        if (name.equals(otherName)) { // Just incase they are the same by chance.
            Affirm.affirmFalse(String.format("RandomProxyFactory=[%s] returned a non-Random Pojo Proxy",
                    RandomInstanceFromInterfaceRandomGenerator.getInstance()), name.equals(aSimpleInterface.getName()));
        }
    }

    @Test
    public void shouldImplementAccuratetoStringAndhashCode() {
        final String toString = aSimpleInterface.toString();
        Affirm.affirmNotNull("toString() on proxy returned null", toString);
        Affirm.affirmTrue(String.format("toString returned [%s] expected it to begin with [%s] and contain [@]", toString, "$Proxy"),
                toString.contains("$Proxy") && toString.contains("@"));
        Affirm.affirmTrue("toString() doesn't end with hashCode()", toString.endsWith(String.valueOf(aSimpleInterface
                .hashCode())));

        final ASimpleInterface anotherSimpleInterface = proxyGenerator.doGenerate(ASimpleInterface.class);
        Affirm.affirmTrue("Generated Proxy hashCode() should not return equal values across instances",
                aSimpleInterface.hashCode() != anotherSimpleInterface.hashCode());
    }

    @Test
    public void shouldAllowInvokingVoidReturnMethods() {
        // Just ensuring it doesn't throw some exception/etc.
        aSimpleInterface.doSomethingUseful();
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailAbstractClass() {
        proxyGenerator.doGenerate(AnAbstractClass.class);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailConcreteClass() {
        proxyGenerator.doGenerate(AConcreteClass.class);
    }

}
