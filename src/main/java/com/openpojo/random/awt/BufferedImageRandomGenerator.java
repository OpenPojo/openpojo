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

package com.openpojo.random.awt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.construct.InstanceFactory;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;
import static com.openpojo.reflection.java.load.ClassUtil.isClassLoaded;
import static com.openpojo.reflection.java.load.ClassUtil.loadClass;

/**
 * @author oshoukry
 */
public class BufferedImageRandomGenerator implements RandomGenerator {
  private static final String TYPE = "java.awt.image.BufferedImage";
  private static final Random RANDOM = new Random(System.currentTimeMillis());
  private static final BufferedImageRandomGenerator INSTANCE = new BufferedImageRandomGenerator();

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (isClassLoaded(TYPE)) {
      types.add(loadClass(TYPE));
    }
    return types;
  }

  public Object doGenerate(Class<?> type) {
    int width = RANDOM.nextInt(10) + 1;
    int height = RANDOM.nextInt(10) + 1;
    int imageType = getRandomImageType();
    return InstanceFactory.getInstance(getPojoClass(loadClass(TYPE)), width, height, imageType);
  }

  private int getRandomImageType() {
    //public static final int TYPE
    List<Integer> availableTypes = new ArrayList<Integer>();

    PojoClass bufferedImagePojoClass = getPojoClass(loadClass(TYPE));

    for (PojoField field : bufferedImagePojoClass.getPojoFields()) {
      if(isPublicStaticFinalIntNamedTypeAndNotTypeCustom(field))
        availableTypes.add((Integer) field.get(null));
    }
    return availableTypes.get(RANDOM.nextInt(availableTypes.size()));
  }

  private boolean isPublicStaticFinalIntNamedTypeAndNotTypeCustom(PojoField field) {
    return field.isPublic()
        && field.isStatic()
        && field.isFinal()
        && field.getType().equals(int.class)
        && field.getName().startsWith("TYPE_")
        && !field.getName().equals("TYPE_CUSTOM");
  }

  private BufferedImageRandomGenerator() {}
}
