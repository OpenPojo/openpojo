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

package com.openpojo.random.collection.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.openpojo.random.collection.util.BaseCollectionRandomGenerator;
import com.openpojo.random.util.Helper;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class TransferQueueRandomGenerator extends BaseCollectionRandomGenerator {
  private static final String TYPE = "java.util.concurrent.TransferQueue";
  private static final String CONCRETE_TYPE = "java.util.concurrent.LinkedTransferQueue";
  private static final TransferQueueRandomGenerator INSTANCE = new TransferQueueRandomGenerator();

  public static TransferQueueRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (ClassUtil.isClassLoaded(TYPE) && ClassUtil.isClassLoaded(CONCRETE_TYPE))
      types.add(ClassUtil.loadClass(TYPE));
    return types;
  }

  @Override
  protected Collection getBasicInstance(Class<?> type) {
    Helper.assertIsAssignableTo(type, getTypes());
    return (Collection) InstanceFactory.getInstance(PojoClassFactory.getPojoClass(ClassUtil.loadClass(CONCRETE_TYPE)));
  }

  private TransferQueueRandomGenerator() {
  }
}
