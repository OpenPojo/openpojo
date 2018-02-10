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

package com.openpojo.random.map.sample;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.map.support.EnumType1;
import com.openpojo.random.map.support.SimpleType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.random.util.MapCollectionAssertionHelper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
@SuppressWarnings({ "unused", "FieldCanBeLocal" })

public class ClassWithVariousGenericMap {
  private Map mapUndefined;
  private Map<?, ?> mapUnbounded;

  private Map<String, Integer> mapStringInteger;

  private Map<SomeGeneric, SomeGeneric> mapSomeGeneric;
  private Map<? extends SomeGeneric, ? extends SomeGeneric> mapExtendsSomeGeneric;

  private Map<? super SomeInterface, ? super SomeGeneric> mapOfSuperSomeInterfaceSomeGeneric;

  private Map<Map, Map> mapOfMapUndefined;
  private Map<Map<?, ?>, Map<?, ?>> mapOfMapUnbounded;

  private Map<Map<Object, SomeGeneric>, Map<SomeInterface, SerializableComparableObject>>
      mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject;

  private Map<Map<? extends SerializableComparableObject, ? extends SomeInterface>, Map<? extends SomeGeneric, ?>>
      mapOfMapOfExtendsSomeGeneric;

  private Map<Map<? super SomeGeneric, ? super SomeGeneric>, Map<? super SomeGeneric, ? super SomeGeneric>>
      mapOfMapOfSuperSomeGeneric;

  private List<Map> listOfMap;
  private Map<List, Queue> mapOfListQueue;

  private Map<SomeGeneric, List<Map<List, Queue>>> mapOfSomeGenericListOfMapOfListQueue;
  private Map<SomeInterface, SomeGeneric> mapOfSomeInterfaceSomeGeneric;

  private Map<AbstractMap<ConcurrentHashMap<SomeGeneric, SomeGeneric>, EnumMap<EnumType1, SomeGeneric>>, HashMap<SimpleType1,
      SimpleType2>> mapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap;

  public void setMapUndefined(Map mapUndefined) {
    assertParameters(mapUndefined,
        Map.class,
          SerializableComparableObject.class,
          SerializableComparableObject.class);
    this.mapUndefined = mapUndefined;
  }

  public void setMapUnbounded(Map<?, ?> mapUnbounded) {
    assertParameters(mapUnbounded,
        Map.class,
          Object.class,
          Object.class);
    this.mapUnbounded = mapUnbounded;
  }

  public void setMapStringInteger(Map<String, Integer> mapStringInteger) {
    assertParameters(mapStringInteger, Map.class, String.class, Integer.class);
    this.mapStringInteger = mapStringInteger;
  }

  public void setMapSomeGeneric(Map<SomeGeneric, SomeGeneric> mapSomeGeneric) {
    assertParameters(mapSomeGeneric,
        Map.class,
          SomeGeneric.class,
          SomeGeneric.class);

    this.mapSomeGeneric = mapSomeGeneric;
  }

  public void setMapExtendsSomeGeneric(Map<? extends SomeGeneric, ? extends SomeGeneric> mapExtendsSomeGeneric) {
    assertParameters(mapExtendsSomeGeneric,
        Map.class,
          SomeGeneric.class,
          SomeGeneric.class);

    this.mapExtendsSomeGeneric = mapExtendsSomeGeneric;
  }

    public void setMapOfSuperSomeInterfaceSomeGeneric(Map<? super SomeInterface, ? super SomeGeneric> mapOfSuperSomeInterfaceSomeGeneric) {
      assertParameters(mapOfSuperSomeInterfaceSomeGeneric,
          Map.class,
            SomeInterface.class,
            SomeGeneric.class);

    this.mapOfSuperSomeInterfaceSomeGeneric = mapOfSuperSomeInterfaceSomeGeneric;
  }

  public void setMapOfMapUndefined(Map<Map, Map> mapOfMapUndefined) {
    assertParameters(mapOfMapUndefined,
        Map.class,
          Map.class,
            SerializableComparableObject.class,
            SerializableComparableObject.class,
        Map.class,
          SerializableComparableObject.class,
          SerializableComparableObject.class);

    this.mapOfMapUndefined = mapOfMapUndefined;
  }

  public void setMapOfMapUnbounded(Map<Map<?, ?>, Map<?, ?>> mapOfMapUnbounded) {
    assertParameters(mapOfMapUnbounded,
        Map.class,
          Map.class,
            Object.class,
            Object.class,
          Map.class,
            Object.class,
            Object.class);

    this.mapOfMapUnbounded = mapOfMapUnbounded;
  }

  public void setMapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject(Map<Map<Object, SomeGeneric>,
      Map<SomeInterface, SerializableComparableObject>> mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject) {
    assertParameters(mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject,
        Map.class,
          Map.class,
            Object.class,
            SomeGeneric.class,
          Map.class,
            SomeInterface.class,
            SerializableComparableObject.class);

    this.mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject =
        mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject;
  }

  public void setMapOfMapOfExtendsSomeGeneric(Map<Map<? extends SerializableComparableObject, ? extends SomeInterface>, Map<?
      extends
      SomeGeneric, ?>> mapOfMapOfExtendsSomeGeneric) {
    assertParameters(mapOfMapOfExtendsSomeGeneric,
        Map.class,
          Map.class,
            SerializableComparableObject.class,
            SomeInterface.class,
          Map.class,
            SomeGeneric.class,
            Object.class);

    this.mapOfMapOfExtendsSomeGeneric = mapOfMapOfExtendsSomeGeneric;
  }

  public void setMapOfMapOfSuperSomeGeneric(Map<Map<? super SomeGeneric, ? super SomeGeneric>, Map<? super SomeGeneric, ?
      super
      SomeGeneric>> mapOfMapOfSuperSomeGeneric) {
    assertParameters(mapOfMapOfSuperSomeGeneric,
        Map.class,
          Map.class,
            SomeGeneric.class,
            SomeGeneric.class,
          Map.class,
            SomeGeneric.class,
            SomeGeneric.class);

    this.mapOfMapOfSuperSomeGeneric = mapOfMapOfSuperSomeGeneric;
  }

  public void setListOfMap(List<Map> listOfMap) {
    assertParameters(listOfMap,
        List.class,
          Map.class,
            SerializableComparableObject.class,
            SerializableComparableObject.class);
    this.listOfMap = listOfMap;
  }

  public void setMapOfListQueue(Map<List, Queue> mapOfListQueue) {
    assertParameters(mapOfListQueue,
        Map.class,
          List.class,
            SerializableComparableObject.class,
          Queue.class,
            SerializableComparableObject.class);
    this.mapOfListQueue = mapOfListQueue;
  }

    public void setMapOfSomeGenericListOfMapOfListQueue(Map<SomeGeneric, List<Map<List, Queue>>> mapOfSomeGenericListOfMapOfListQueue) {
      assertParameters(mapOfSomeGenericListOfMapOfListQueue,
          Map.class,
            SomeGeneric.class,
            List.class,
              Map.class,
                List.class,
                  SerializableComparableObject.class,
                Queue.class,
                  SerializableComparableObject.class);
    this.mapOfSomeGenericListOfMapOfListQueue = mapOfSomeGenericListOfMapOfListQueue;
  }

  public void setMapOfSomeInterfaceSomeGeneric(Map<SomeInterface, SomeGeneric> mapOfSomeInterfaceSomeGeneric) {
    assertParameters(mapOfSomeInterfaceSomeGeneric,
        Map.class,
          SomeInterface.class,
          SomeGeneric.class);
    this.mapOfSomeInterfaceSomeGeneric = mapOfSomeInterfaceSomeGeneric;
  }

    public void setMapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap(Map<AbstractMap<ConcurrentHashMap<SomeGeneric, SomeGeneric>,
        EnumMap<EnumType1, SomeGeneric>>, HashMap<SimpleType1, SimpleType2>> mapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap) {
      assertParameters(mapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap,
          Map.class,
            AbstractMap.class,
              ConcurrentHashMap.class,
                SomeGeneric.class,
                SomeGeneric.class,
              EnumMap.class,
                EnumType1.class,
                SomeGeneric.class,
            HashMap.class,
              SimpleType1.class,
              SimpleType2.class);
    this.mapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap = mapOfAbstractMapOfConcurrentHashMapAndEnumMapAndHashMap;
  }

  private void assertParameters(Object input, Class<?>... expectedTypes) {
    LoggerFactory.getLogger(this.getClass()).debug("Checking: [" + input + "] with types " + Arrays.toString(expectedTypes));

    MapCollectionAssertionHelper.assertParametersStructure(input, expectedTypes);
  }
}
