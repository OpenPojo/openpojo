/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.TestClassMustBeProperlyNamedRule;
import com.openpojo.validation.test.Tester;
import org.junit.Before;
import org.junit.Test;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;
import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClassesRecursively;

/**
 * @author oshoukry
 */
public class StructuralTest {
  private static final Class<?>[] NON_INSTANTIABLES = new Class<?>[] {
      com.openpojo.reflection.PojoParameter.class
      ,com.openpojo.random.RandomFactory.class
      ,com.openpojo.log.LoggerFactory.class
      ,com.openpojo.business.BusinessIdentity.class
      ,com.openpojo.cache.CacheStorageFactory.class
      ,com.openpojo.reflection.impl.PojoMethodFactory.class
      ,com.openpojo.reflection.impl.PojoParameterFactory.class
      ,com.openpojo.reflection.impl.ParameterizableFactory.class
      ,com.openpojo.reflection.impl.PojoFieldFactory.class
      ,com.openpojo.reflection.impl.PojoClassFactory.class
      ,com.openpojo.reflection.impl.PojoPackageFactory.class
      ,com.openpojo.reflection.cache.PojoCache.class
      ,com.openpojo.reflection.construct.InstanceFactory.class
      ,com.openpojo.reflection.utils.ObjectToString.class
      ,com.openpojo.reflection.utils.AttributeHelper.class
      ,com.openpojo.reflection.utils.ToStringHelper.class
      ,com.openpojo.reflection.java.Java.class
      ,com.openpojo.reflection.facade.FacadeFactory.class
      ,com.openpojo.random.util.Helper.class
      ,com.openpojo.random.util.SomeEnum.class
      ,com.openpojo.random.thread.GeneratedRandomValues.class
      ,com.openpojo.log.utils.MessageFormatter.class
      ,com.openpojo.business.identity.IdentityFactory.class
      ,com.openpojo.business.utils.BusinessIdentityUtils.class
      ,com.openpojo.business.utils.BusinessPojoHelper.class
      ,com.openpojo.validation.utils.CloseableHelper.class
      ,com.openpojo.validation.utils.ToStringHelper.class
      ,com.openpojo.validation.utils.ValidationHelper.class
      ,com.openpojo.reflection.coverage.service.PojoCoverageFilterServiceFactory.class
      ,com.openpojo.reflection.java.type.Resolver.class
      ,com.openpojo.reflection.java.bytecode.ByteCodeFactory.class
      ,com.openpojo.reflection.java.load.ClassUtil.class
      ,com.openpojo.reflection.java.version.VersionParser.class
      ,com.openpojo.reflection.java.version.VersionFactory.class
      ,com.openpojo.random.map.util.MapHelper.class
      ,com.openpojo.random.collection.util.CollectionHelper.class
      ,com.openpojo.reflection.java.bytecode.asm.ClassReaderFactory.class
      ,com.openpojo.reflection.java.packageloader.utils.Helper.class
      ,com.openpojo.random.generator.time.util.ReflectionHelper.class
      ,com.openpojo.validation.affirm.Affirm.class
  };

  private Validator validator;

  @Before
  public void setup() {
    validator = ValidatorBuilder.create()
        .with(new TestClassMustBeProperlyNamedRule())
        .build();
  }

  @Test
  public void allTestsMustEndWithTest() {
    List<PojoClass> pojoClasses = getPojoClassesRecursively("com.openpojo", null);
    validator.validate(pojoClasses);
  }

  @Test
  public void shouldNotBeConstructed() {
    Validator validator = ValidatorBuilder.create()
        .with(new NonInstantiableTester())
        .build();

    for (Class<?> nonInstantiable : NON_INSTANTIABLES)
      validator.validate(getPojoClass(nonInstantiable));
  }

  private static class NonInstantiableTester implements Tester {
    public void run(PojoClass pojoClass) {
      Affirm.affirmEquals("Should have only one constructor [" + pojoClass.getName() + "]",
          1,
          pojoClass.getPojoConstructors().size());
      PojoMethod constructor = pojoClass.getPojoConstructors().get(0);
      Affirm.affirmTrue("Constructor should be private [" + pojoClass.getName() + "]", constructor.isPrivate());
      try {
        InstanceFactory.getInstance(pojoClass);
        Affirm.fail("Class [" + pojoClass.getName() + "] should have throw exception upon creation");
      } catch (ReflectionException re) {
        Throwable actual = re;
        while (actual != null && !(actual instanceof UnsupportedOperationException))
          actual = actual.getCause();

        if (actual == null)
          Affirm.fail("Expected " + UnsupportedOperationException.class.getName()
              + " to be thrown when constructing [" + pojoClass.getName() + "] but was " + re);
        else
          Affirm.affirmEquals("Expected message miss-match"
              , pojoClass.getName() + " should not be constructed!"
              , actual.getMessage());
      }
    }
  }

  @Test
  public void verifyNonInstantiableList() {
    List<Class<?>> nonInstantiables = Arrays.asList(NON_INSTANTIABLES);
    List<PojoClass> staticClasses = getPojoClassesRecursively("com.openpojo", new NonStaticClassesFilter());
    for (PojoClass entry : staticClasses)
      Affirm.affirmContains("Detected class [" + entry.getName() + "] not being tested", entry.getClazz(), nonInstantiables);

    for (Class<?> nonInstantiable : nonInstantiables)
      Affirm.affirmContains("Detected class [" + nonInstantiable.getName() + "] being tested but was filtered out",
          getPojoClass(nonInstantiable), staticClasses);
  }

  private static class NonStaticClassesFilter implements PojoClassFilter {
    private Set<String> excluded = new HashSet<String>();

    private NonStaticClassesFilter() {
      excluded.add(com.openpojo.random.util.SomeRoleUnresolved.class.getName());
      excluded.add(com.openpojo.random.util.SomeRole.class.getName());
    }

    public boolean include(PojoClass pojoClass) {

      if (pojoClass.getSourcePath().contains("test-classes")
          || RuntimeException.class.isAssignableFrom(pojoClass.getClazz())
          || excluded.contains(pojoClass.getName())
          || pojoClass.isSynthetic()
          || (pojoClass.isNestedClass() && pojoClass.isPrivate()))
        return false;

      boolean includeReturn = true;
      for (PojoMethod method : pojoClass.getPojoMethods())
      if (!method.isSynthetic() && !method.isConstructor())
        includeReturn &= method.isStatic();

      return includeReturn;
    }
  }
}
