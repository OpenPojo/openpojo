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

package com.openpojo.validation.test.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.CloseableHelper;

/**
 * This tester ensures that you are able to serialize and deserialize objects without any errors.
 *
 * If used with StrictValidation, the SerializableTester will fail if a member variable is an interface
 * and the interface doesn't explicitly extend Serializable.
 *
 * @author oshoukry
 */
public class SerializableTester implements Tester {
  private final Logger logger = LoggerFactory.getLogger(SerializableTester.class);
  private final boolean useStrictValidation;

  public SerializableTester() {
    this(false);
  }

  public SerializableTester(boolean useStrictValidation) {
    this.useStrictValidation = useStrictValidation;
  }

  public void run(PojoClass pojoClass) {
    final Class<?> clazz = pojoClass.getClazz();

    if (Serializable.class.isAssignableFrom(clazz)) {
      Object instance = RandomFactory.getRandomValue(clazz);
      ensureNoFieldsAreNull(pojoClass, instance);

      try {
        byte[] serializedObject = serialize(pojoClass, instance);
        Object instance2 = deSerialize(serializedObject, instance.getClass());
        Affirm.affirmNotNull("Failed to load serialized object [" + instance + "]", instance2);
      } catch (Exception e) {
        Affirm.fail("Failed to run " + this.getClass().getName() + " - Got exception [" + e + "] on PojoClass " + pojoClass);
      }
    } else {
      logger.warn("Class [" + clazz + "] is not serializable, skipping validation");
    }
  }

  private void ensureNoFieldsAreNull(PojoClass pojoClass, Object instance) {
    PojoClass currentPojo = pojoClass;
    while (currentPojo != null) {
      for (PojoField field : currentPojo.getPojoFields()) {
        PojoClass fieldClass = PojoClassFactory.getPojoClass(field.getType());
        if (useStrictValidation && !fieldClass.extendz(Serializable.class) && fieldClass.isInterface() && !field.isTransient()) {
          Affirm.fail("Field ["
                  + field.getName()
                  + "] is an interface that allows non-Serializable types on a Serializable ["
                  + pojoClass.getClazz()
                  + "]"
              );
        }
        if (field.get(instance) == null)
          field.set(instance, RandomFactory.getRandomValue(field));
      }
      currentPojo = currentPojo.getSuperClass();
    }
  }

  private byte[] serialize(PojoClass pojoClass, Object object) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = null;

    try {
      logger.debug("Serializing [" + object + "] to byte[]");
      objectOutputStream = new ObjectOutputStream(outputStream);
      objectOutputStream.writeObject(object);
    } catch (NotSerializableException notSerializable) {
      final String failMessage = getFailMessage(pojoClass, notSerializable);
      Affirm.fail(failMessage);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      CloseableHelper.closeResources(objectOutputStream, outputStream);
    }
    return outputStream.toByteArray();
  }

  private String getFailMessage(PojoClass pojoClass, NotSerializableException notSerializable) {
    String message = "Class [" + pojoClass.getClazz().getName() + "] has non-serializable field type [";
    boolean found = false;
    for (PojoField field : pojoClass.getPojoFields())
      if (field.getType().getName().equals(notSerializable.getMessage())) {
        found = true;
        message += field;
      }
    if (!found)
      message += notSerializable.getMessage() + "] which is inherited from a super class";
    else
      message += "]";
    return message;
  }

  private <T> T deSerialize(byte[] bytes, Class<T> clazz) {

    final T outClazz;
    logger.debug("De-Serializing [" + clazz.getName() + "] from byte[]");
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    ObjectInputStream objectInputStream = null;

    try {
      objectInputStream = new ObjectInputStream(inputStream);
      outClazz = clazz.cast(objectInputStream.readObject());
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      CloseableHelper.closeResources(objectInputStream, inputStream);
    }
    return outClazz;
  }

}
