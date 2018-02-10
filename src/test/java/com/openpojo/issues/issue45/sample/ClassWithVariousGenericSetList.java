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

package com.openpojo.issues.issue45.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.util.MapCollectionAssertionHelper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
@SuppressWarnings("all")
public class ClassWithVariousGenericSetList {
  private List listUndefined;
  private List<?> listUnbounded;
  private List<SomeGeneric> listSomeGeneric;
  private List<? extends SomeGeneric> listExtendsSomeGeneric;
  private List<? super SomeGeneric> listSuperSomeGeneric;
  private List<List> listOfListUndefined;
  private List<List<?>> listOfListUnbounded;
  private List<List<SomeGeneric>> listOfListOfSomeGeneric;
  private List<List<? extends SomeGeneric>> listOfListOfExtendsSomeGeneric;
  private List<List<? super SomeGeneric>> listOfListOfSuperSomeGeneric;

  private List<Map> listOfMap;

  private Queue queueUndefined;
  private Queue<?> queueUnbounded;
  private Queue<SomeGeneric> queueSomeGeneric;
  private Queue<? extends SomeGeneric> queueExtendsSomeGeneric;
  private Queue<? super SomeGeneric> queueSuperSomeGeneric;
  private Queue<Queue> queueOfQueueUndefined;
  private Queue<Queue<?>> queueOfQueueUnbounded;
  private Queue<Queue<SomeGeneric>> queueOfQueueOfSomeGeneric;
  private Queue<Queue<? extends SomeGeneric>> queueOfQueueOfExtendsSomeGeneric;
  private Queue<Queue<? super SomeGeneric>> queueOfQueueOfSuperSomeGeneric;

  private Queue<Map> queueOfMap;

  private Set setUndefined;
  private Set<?> setUnbounded;
  private Set<SomeGeneric> setSomeGeneric;
  private Set<? extends SomeGeneric> setExtendsSomeGeneric;
  private Set<? super SomeGeneric> setSuperSomeGeneric;
  private Set<Set> setOfSetUndefined;
  private Set<Set<?>> setOfSetUnbounded;
  private Set<Set<SomeGeneric>> setOfSetOfSomeGeneric;
  private Set<Set<? extends SomeGeneric>> setOfSetOfExtendsSomeGeneric;
  private Set<Set<? super SomeGeneric>> setOfSetOfSuperSomeGeneric;

  private Set<Map<SomeInterface, SomeGeneric>> setOfMapOfSomeInterfaceAndSomeGeneric;

  private Queue<Set<List<SomeGeneric>>> queueOfSetOfListOfSomeGeneric;
  private Set<List<Set<SomeGeneric>>> setOfListOfSetOfSomeGeneric;

  private List<Set<? super SomeInterface>> listOfSetOfSuperSomeInterface;

  private Map<?, ?> mapUnbounded;

  public void setListUndefined(List listUndefined) {
    assertParameters(listUndefined,
        List.class,
          SerializableComparableObject.class );

    this.listUndefined = listUndefined;
  }

  public void setListUnbounded(List<?> listUnbounded) {
    assertParameters(listUnbounded,
        List.class,
          Object.class);

    this.listUnbounded = listUnbounded;
  }

  public void setListSomeGeneric(List<SomeGeneric> listSomeGeneric) {
    assertParameters(listSomeGeneric,
        List.class,
          SomeGeneric.class);

    this.listSomeGeneric = listSomeGeneric;
  }

  public void setListExtendsSomeGeneric(List<? extends SomeGeneric> listExtendsSomeGeneric) {
    assertParameters(listExtendsSomeGeneric,
        List.class,
          SomeGeneric.class);

    this.listExtendsSomeGeneric = listExtendsSomeGeneric;
  }

  public void setListSuperSomeGeneric(List<? super SomeGeneric> listSuperSomeGeneric) {
    assertParameters(listSuperSomeGeneric,
        List.class,
          SomeGeneric.class);

    this.listSuperSomeGeneric = listSuperSomeGeneric;
  }

  public void setListOfListUndefined(List<List> listOfListUndefined) {
    assertParameters(listOfListUndefined,
        List.class,
          List.class,
            SerializableComparableObject.class);

    this.listOfListUndefined = listOfListUndefined;
  }

  public void setListOfListUnbounded(List<List<?>> listOfListUnbounded) {
    assertParameters(listOfListUnbounded,
        List.class,
          List.class,
            Object.class);

    this.listOfListUnbounded = listOfListUnbounded;
  }

  public void setListOfListOfSomeGeneric(List<List<SomeGeneric>> listOfListOfSomeGeneric) {
    assertParameters(listOfListOfSomeGeneric,
        List.class,
          List.class,
            SomeGeneric.class);

    this.listOfListOfSomeGeneric = listOfListOfSomeGeneric;
  }

  public void setListOfListOfExtendsSomeGeneric(List<List<? extends SomeGeneric>> listOfListOfExtendsSomeGeneric) {
    assertParameters(listOfListOfExtendsSomeGeneric,
        List.class,
          List.class,
            SomeGeneric.class);

    this.listOfListOfExtendsSomeGeneric = listOfListOfExtendsSomeGeneric;
  }

  public void setListOfListOfSuperSomeGeneric(List<List<? super SomeGeneric>> listOfListOfSuperSomeGeneric) {
    assertParameters(listOfListOfSuperSomeGeneric,
        List.class,
          List.class,
            SomeGeneric.class);

    this.listOfListOfSuperSomeGeneric = listOfListOfSuperSomeGeneric;
  }

  public void setListOfMap(List<Map> listOfMap) {
    assertParameters(listOfMap,
        List.class,
          Map.class,
            SerializableComparableObject.class,
            SerializableComparableObject.class);

    this.listOfMap = listOfMap;
  }

  public void setQueueUndefined(Queue queueUndefined) {
    assertParameters(queueUndefined,
        Queue.class,
          SerializableComparableObject.class);

    this.queueUndefined = queueUndefined;
  }

  public void setQueueUnbounded(Queue<?> queueUnbounded) {
    assertParameters(queueUnbounded,
        Queue.class,
          Object.class);

    this.queueUnbounded = queueUnbounded;
  }

  public void setQueueSomeGeneric(Queue<SomeGeneric> queueSomeGeneric) {
    assertParameters(queueSomeGeneric,
        Queue.class,
          SomeGeneric.class);

    this.queueSomeGeneric = queueSomeGeneric;
  }

  public void setQueueExtendsSomeGeneric(Queue<? extends SomeGeneric> queueExtendsSomeGeneric) {
    assertParameters(queueExtendsSomeGeneric,
        Queue.class,
          SomeGeneric.class);

    this.queueExtendsSomeGeneric = queueExtendsSomeGeneric;
  }

  public void setQueueSuperSomeGeneric(Queue<? super SomeGeneric> queueSuperSomeGeneric) {
    assertParameters(queueSuperSomeGeneric, Queue.class, SomeGeneric.class);
    this.queueSuperSomeGeneric = queueSuperSomeGeneric;
  }

  public void setQueueOfQueueUndefined(Queue<Queue> queueOfQueueUndefined) {
    assertParameters(queueOfQueueUndefined,
        Queue.class,
          Queue.class,
            SerializableComparableObject.class);

    this.queueOfQueueUndefined = queueOfQueueUndefined;
  }

  public void setQueueOfQueueUnbounded(Queue<Queue<?>> queueOfQueueUnbounded) {
    assertParameters(queueOfQueueUnbounded,
        Queue.class,
          Queue.class,
            Object.class);

    this.queueOfQueueUnbounded = queueOfQueueUnbounded;
  }

  public void setQueueOfQueueOfSomeGeneric(Queue<Queue<SomeGeneric>> queueOfQueueOfSomeGeneric) {
    assertParameters(queueOfQueueOfSomeGeneric,
        Queue.class,
          Queue.class,
            SomeGeneric.class);

    this.queueOfQueueOfSomeGeneric = queueOfQueueOfSomeGeneric;
  }

  public void setQueueOfQueueOfExtendsSomeGeneric(Queue<Queue<? extends SomeGeneric>> queueOfQueueOfExtendsSomeGeneric) {
    assertParameters(queueOfQueueOfExtendsSomeGeneric,
        Queue.class,
          Queue.class,
            SomeGeneric.class);

    this.queueOfQueueOfExtendsSomeGeneric = queueOfQueueOfExtendsSomeGeneric;
  }

  public void setQueueOfQueueOfSuperSomeGeneric(Queue<Queue<? super SomeGeneric>> queueOfQueueOfSuperSomeGeneric) {
    assertParameters(queueOfQueueOfSuperSomeGeneric,
        Queue.class,
          Queue.class,
            SomeGeneric.class);

    this.queueOfQueueOfSuperSomeGeneric = queueOfQueueOfSuperSomeGeneric;
  }

  public void setQueueOfMap(Queue<Map> queueOfMap) {
    assertParameters(queueOfMap,
        Queue.class,
          Map.class,
            SerializableComparableObject.class,
            SerializableComparableObject.class);

    this.queueOfMap = queueOfMap;
  }


  public void setSetUndefined(Set setUndefined) {
    assertParameters(setUndefined,
        Set.class,
          SerializableComparableObject.class);

    this.setUndefined = setUndefined;
  }

  public void setSetUnbounded(Set<?> setUnbounded) {
    assertParameters(setUnbounded,
        Set.class,
          Object.class);

    this.setUnbounded = setUnbounded;
  }

  public void setSetSomeGeneric(Set<SomeGeneric> setSomeGeneric) {
    assertParameters(setSomeGeneric,
        Set.class,
          SomeGeneric.class);

    this.setSomeGeneric = setSomeGeneric;
  }

  public void setSetExtendsSomeGeneric(Set<? extends SomeGeneric> setExtendsSomeGeneric) {
    assertParameters(setExtendsSomeGeneric,
        Set.class,
          SomeGeneric.class);

    this.setExtendsSomeGeneric = setExtendsSomeGeneric;
  }

  public void setSetSuperSomeGeneric(Set<? super SomeGeneric> setSuperSomeGeneric) {
    assertParameters(setSuperSomeGeneric,
        Set.class,
          SomeGeneric.class);

    this.setSuperSomeGeneric = setSuperSomeGeneric;
  }

  public void setSetOfSetUndefined(Set<Set> setOfSetUndefined) {
    assertParameters(setOfSetUndefined,
        Set.class,
          Set.class,
            SerializableComparableObject.class);

    this.setOfSetUndefined = setOfSetUndefined;
  }

  public void setSetOfSetUnbounded(Set<Set<?>> setOfSetUnbounded) {
    assertParameters(setOfSetUnbounded, Set.class, Set.class, Object.class);
    this.setOfSetUnbounded = setOfSetUnbounded;
  }

  public void setSetOfSetOfSomeGeneric(Set<Set<SomeGeneric>> setOfSetOfSomeGeneric) {
    assertParameters(setOfSetOfSomeGeneric,
        Set.class,
          Set.class,
            SomeGeneric.class);

    this.setOfSetOfSomeGeneric = setOfSetOfSomeGeneric;
  }

  public void setSetOfSetOfExtendsSomeGeneric(Set<Set<? extends SomeGeneric>> setOfSetOfExtendsSomeGeneric) {
    assertParameters(setOfSetOfExtendsSomeGeneric,
        Set.class,
          Set.class,
            SomeGeneric.class);

    this.setOfSetOfExtendsSomeGeneric = setOfSetOfExtendsSomeGeneric;
  }

  public void setSetOfSetOfSuperSomeGeneric(Set<Set<? super SomeGeneric>> setOfSetOfSuperSomeGeneric) {
    assertParameters(setOfSetOfSuperSomeGeneric,
        Set.class,
          Set.class,
            SomeGeneric.class);

    this.setOfSetOfSuperSomeGeneric = setOfSetOfSuperSomeGeneric;
  }

    public void setSetOfMapOfSomeInterfaceAndSomeGeneric(Set<Map<SomeInterface, SomeGeneric>> setOfMapOfSomeInterfaceAndSomeGeneric) {
      assertParameters(setOfMapOfSomeInterfaceAndSomeGeneric,
          Set.class,
            Map.class,
              SomeInterface.class,
              SomeGeneric.class);

    this.setOfMapOfSomeInterfaceAndSomeGeneric = setOfMapOfSomeInterfaceAndSomeGeneric;
  }

  public void setQueueOfSetOfListOfSomeGeneric(Queue<Set<List<SomeGeneric>>> queueOfSetOfListOfSomeGeneric) {
    assertParameters(queueOfSetOfListOfSomeGeneric,
        Queue.class,
          Set.class,
            List.class,
              SomeGeneric.class);

    this.queueOfSetOfListOfSomeGeneric = queueOfSetOfListOfSomeGeneric;
  }

  public void setSetOfListOfSetOfSomeGeneric(Set<List<Set<SomeGeneric>>> setOfListOfSetOfSomeGeneric) {
    assertParameters(setOfListOfSetOfSomeGeneric,
        Set.class,
          List.class,
            Set.class,
              SomeGeneric.class);

    this.setOfListOfSetOfSomeGeneric = setOfListOfSetOfSomeGeneric;
  }

  public void setListOfSetOfSuperSomeInterface(List<Set<? super SomeInterface>> listOfSetOfSuperSomeInterface) {
    assertParameters(listOfSetOfSuperSomeInterface,
        List.class,
          Set.class,
            SomeInterface.class);

    this.listOfSetOfSuperSomeInterface = listOfSetOfSuperSomeInterface;
  }

  public void setMapUnbounded(Map<?, ?> mapUnbounded) {
    assertParameters(mapUnbounded,
        Map.class,
          Object.class,
          Object.class);

    this.mapUnbounded = mapUnbounded;
  }

  private void assertParameters(Object input, Class<?>... expectedTypes) {
    LoggerFactory.getLogger(this.getClass()).debug("Checking: [" + input + "] with types " + Arrays.toString(expectedTypes));

    MapCollectionAssertionHelper.assertParametersStructure(input, expectedTypes);
  }
}
