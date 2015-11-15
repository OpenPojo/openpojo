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

package com.openpojo.random.sampleclasses.hierarchy;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class SomeInterfaceRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { ClassExtendingClassImplementingSomeInterface.class };

  private SomeInterfaceRandomGenerator() {

  }

  public static RandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.openpojo.random.RandomGenerator#getTypes()
   */
  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.openpojo.random.RandomGenerator#doGenerate(java.lang.Class)
   */
  public Object doGenerate(Class<?> type) {
    return new ClassExtendingClassImplementingSomeInterface();
  }

  private static class Instance {
    private static final RandomGenerator INSTANCE = new SomeInterfaceRandomGenerator();
  }

}
