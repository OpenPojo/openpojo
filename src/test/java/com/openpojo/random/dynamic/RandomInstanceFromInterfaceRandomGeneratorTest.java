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

package com.openpojo.random.dynamic;

import java.util.List;

import com.openpojo.random.dynamic.sampleclasses.AConcreteClass;
import com.openpojo.random.dynamic.sampleclasses.ASimpleInterface;
import com.openpojo.random.dynamic.sampleclasses.AnAbstractClass;
import com.openpojo.random.dynamic.sampleclasses.AnInterfaceWithGenericMethodReturnType;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

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
    Affirm.affirmFalse("Same instance returned or faulty equality implementation on proxy",
        aSimpleInterface.equals(proxyGenerator.doGenerate(ASimpleInterface.class)));
  }

  @Test
  public void shouldReturnFalseWithNullEquals() {
    Affirm.affirmFalse("Should be false", aSimpleInterface.equals(null));
  }

  @Test
  public void shouldReturnRandomNonNullValuesForInterfaceMethods() {
    final ASimpleInterface aSimpleInterface = proxyGenerator.doGenerate(ASimpleInterface.class);

    Affirm.affirmNotNull("Generated proxy getName() returned null", aSimpleInterface.getName());

    final String name = aSimpleInterface.getName();
    final String otherName = aSimpleInterface.getName();
    if (name.equals(otherName)) { // Just in case they are the same by chance.
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

    Affirm.affirmTrue("toString() doesn't end with hashCode()", toString.endsWith(String.valueOf(aSimpleInterface.hashCode())));

    final ASimpleInterface anotherSimpleInterface = proxyGenerator.doGenerate(ASimpleInterface.class);
    Affirm.affirmTrue("Generated Proxy hashCode() should not return equal values across instances",
        aSimpleInterface.hashCode() != anotherSimpleInterface.hashCode());
  }

  @Test
  public void shouldAllowInvokingVoidReturnMethods() {
    // Just ensuring it doesn't throw some exception/etc.
    aSimpleInterface.doSomethingUseful();
  }

  @Test
  public void shouldReturnProperGenericsValue() {
    final AnInterfaceWithGenericMethodReturnType anInterfaceWithGenericMethodReturnType = proxyGenerator.doGenerate
        (AnInterfaceWithGenericMethodReturnType.class);

    List<AConcreteClass> theList = anInterfaceWithGenericMethodReturnType.aListOfAConcreteClass();

    Affirm.affirmNotNull("Should not be null", theList);
    Affirm.affirmTrue("Should not be empty", theList.size() > 0);
    for (Object entry : theList)
      Affirm.affirmEquals("Should be of correct type", AConcreteClass.class, entry.getClass());

    int[] anIntArray = anInterfaceWithGenericMethodReturnType.anIntArray();
    Affirm.affirmNotNull("Should not be null", anIntArray);
    Affirm.affirmTrue("Should not be empty", anIntArray.length > 0);
    for (int entry : anIntArray)
      Affirm.affirmFalse("should not be equal", entry == entry + 1);

    String aString = anInterfaceWithGenericMethodReturnType.aString();
    Affirm.affirmNotNull("Should not be null", aString);
    Affirm.affirmTrue("Should not be empty", aString.length() > 0);

    boolean voidMethodInvoked = false;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(anInterfaceWithGenericMethodReturnType.getClass());
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (pojoMethod.getName().equals("aVoid")) {
        Affirm.affirmNull("Should be null", pojoMethod.invoke(anInterfaceWithGenericMethodReturnType));
        voidMethodInvoked = true;
      }
    }
    Affirm.affirmTrue("Void method not found!!", voidMethodInvoked);
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
