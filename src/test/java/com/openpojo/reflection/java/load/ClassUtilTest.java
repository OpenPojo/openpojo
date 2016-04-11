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

package com.openpojo.reflection.java.load;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ClassUtilTest {

  @Test(expected = UnsupportedOperationException.class)
  public void shouldNotBeAbleToConstruct() {
    try {
      PojoClass classUtilPojoClass = PojoClassFactory.getPojoClass(ClassUtil.class);
      Assert.assertEquals(1, classUtilPojoClass.getPojoConstructors().size());
      InstanceFactory.getLeastCompleteInstance(classUtilPojoClass);
    } catch (ReflectionException re) {
      Throwable cause = re.getCause();
      while (cause != null) {
        if (cause instanceof UnsupportedOperationException)
          throw (UnsupportedOperationException) cause;
        cause = cause.getCause();
      }
    }
    Assert.fail("Should have not been able to construct");
  }

  @Test
  public void shouldDetectTestClassLoaded() {
    Assert.assertTrue(ClassUtil.isClassLoaded(this.getClass().getName()));
  }

  @Test
  public void shouldDetectInvalidClassNotLoaded() {
    Assert.assertFalse(ClassUtil.isClassLoaded(this.getClass().getName() + "NonExistentClass"));
  }
}
