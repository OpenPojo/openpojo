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

package com.openpojo.random.collection.sample;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.management.Attribute;
import javax.management.AttributeList;

import com.openpojo.random.util.SerializableComparableObject;
import org.junit.Assert;

/**
 * @author oshoukry
 */
@SuppressWarnings({ "FieldCanBeLocal", "unused" })
public class AClassWithExhaustiveCollection {
  private Collection collectionUndefined;
  private Collection<?> collectionUnbounded;
  private Collection<SomeGeneric> collectionSomeGeneric;
  private Collection<? extends SomeGeneric> collectionExtendsSomeGeneric;
  private Collection<? super SomeGeneric> collectionSuperSomeGeneric;
  private Collection<Collection> collectionOfCollectionUndefined;
  private Collection<Collection<?>> collectionOfCollectionUnbounded;
  private Collection<Collection<SomeGeneric>> collectionOfCollectionOfSomeGeneric;
  private Collection<Collection<? extends SomeGeneric>> collectionOfCollectionOfExtendsSomeGeneric;
  private Collection<Collection<? super SomeGeneric>> collectionOfCollectionOfSuperSomeGeneric;

  private Collection<AbstractCollection<AbstractList<SomeGeneric>>> collectionOfAbstractCollectionOfAbstractList;
  private AbstractList<AbstractSequentialList<ArrayList<SomeGeneric>>> abstractListOfAbstractSequentialListOfArrayList;
  private ArrayList<CopyOnWriteArrayList<LinkedList<SomeGeneric>>> arrayListOfCopyOnWriteArrayListOfLinkedList;
  private LinkedList<List<Stack<SomeGeneric>>> linkedListOfListOfStack;
  private Stack<Vector<AttributeList>> stackOfVectorOfAttributeList;

  public void setCollectionUndefined(Collection collectionUndefined) {
    assertThatCollectionIsOf(collectionUndefined, SerializableComparableObject.class);
    this.collectionUndefined = collectionUndefined;
  }

  public void setCollectionUnbounded(Collection<?> collectionUnbounded) {
    assertThatCollectionIsOf(collectionUnbounded, Object.class);
    this.collectionUnbounded = collectionUnbounded;
  }

  public void setCollectionSomeGeneric(Collection<SomeGeneric> collectionSomeGeneric) {
    assertThatCollectionIsOf(collectionSomeGeneric, SomeGeneric.class);
    this.collectionSomeGeneric = collectionSomeGeneric;
  }

  public void setCollectionExtendsSomeGeneric(Collection<? extends SomeGeneric> collectionExtendsSomeGeneric) {
    assertThatCollectionIsOf(collectionExtendsSomeGeneric, SomeGeneric.class);
    this.collectionExtendsSomeGeneric = collectionExtendsSomeGeneric;
  }

  public void setCollectionSuperSomeGeneric(Collection<? super SomeGeneric> collectionSuperSomeGeneric) {
    assertThatCollectionIsOf(collectionSuperSomeGeneric, SomeGeneric.class);
    this.collectionSuperSomeGeneric = collectionSuperSomeGeneric;
  }

  public void setCollectionOfCollectionUndefined(Collection<Collection> collectionOfCollectionUndefined) {
    assertThatCollectionIsOf(collectionOfCollectionUndefined, ArrayList.class, SerializableComparableObject.class);
    this.collectionOfCollectionUndefined = collectionOfCollectionUndefined;
  }

  public void setCollectionOfCollectionUnbounded(Collection<Collection<?>> collectionOfCollectionUnbounded) {
    assertThatCollectionIsOf(collectionOfCollectionUnbounded, ArrayList.class, Object.class);
    this.collectionOfCollectionUnbounded = collectionOfCollectionUnbounded;
  }

  public void setCollectionOfCollectionOfSomeGeneric(Collection<Collection<SomeGeneric>> collectionOfCollectionOfSomeGeneric) {
    assertThatCollectionIsOf(collectionOfCollectionOfSomeGeneric, ArrayList.class, SomeGeneric.class);
    this.collectionOfCollectionOfSomeGeneric = collectionOfCollectionOfSomeGeneric;
  }

  public void setCollectionOfCollectionOfExtendsSomeGeneric(Collection<Collection<? extends SomeGeneric>>
                                                                collectionOfCollectionOfExtendsSomeGeneric) {
    assertThatCollectionIsOf(collectionOfCollectionOfExtendsSomeGeneric, ArrayList.class, SomeGeneric.class);
    this.collectionOfCollectionOfExtendsSomeGeneric = collectionOfCollectionOfExtendsSomeGeneric;
  }

  public void setCollectionOfCollectionOfSuperSomeGeneric(
      Collection<Collection<? super SomeGeneric>> collectionOfCollectionOfSuperSomeGeneric) {
    assertThatCollectionIsOf(collectionOfCollectionOfSuperSomeGeneric, ArrayList.class, SomeGeneric.class);
    this.collectionOfCollectionOfSuperSomeGeneric = collectionOfCollectionOfSuperSomeGeneric;
  }

  public void setCollectionOfAbstractCollectionOfAbstractList(Collection<AbstractCollection<AbstractList<SomeGeneric>>>
                                                                  collectionOfAbstractCollectionOfAbstractList) {
    assertThatCollectionIsOf(collectionOfAbstractCollectionOfAbstractList, ArrayList.class, ArrayList.class, SomeGeneric.class);
    this.collectionOfAbstractCollectionOfAbstractList = collectionOfAbstractCollectionOfAbstractList;
  }

  public void setAbstractListOfAbstractSequentialListOfArrayList(AbstractList<AbstractSequentialList<ArrayList<SomeGeneric>>>
                                                                     abstractListOfAbstractSequentialListOfArrayList) {

    assertThatCollectionIsOf(
        abstractListOfAbstractSequentialListOfArrayList, LinkedList.class, ArrayList.class, SomeGeneric.class);
    this.abstractListOfAbstractSequentialListOfArrayList = abstractListOfAbstractSequentialListOfArrayList;
  }

  public void setArrayListOfCopyOnWriteArrayListOfLinkedList(ArrayList<CopyOnWriteArrayList<LinkedList<SomeGeneric>>>
                                                                 arrayListOfCopyOnWriteArrayListOfLinkedList) {
    assertThatCollectionIsOf(
        arrayListOfCopyOnWriteArrayListOfLinkedList, CopyOnWriteArrayList.class, LinkedList.class, SomeGeneric.class);
    this.arrayListOfCopyOnWriteArrayListOfLinkedList = arrayListOfCopyOnWriteArrayListOfLinkedList;
  }

  public void setLinkedListOfListOfStack(LinkedList<List<Stack<SomeGeneric>>> linkedListOfListOfStack) {
    assertThatCollectionIsOf(linkedListOfListOfStack, ArrayList.class, Stack.class, SomeGeneric.class);
    this.linkedListOfListOfStack = linkedListOfListOfStack;
  }

  public void setStackOfVectorOfAttributeList(Stack<Vector<AttributeList>> stackOfVectorOfAttributeList) {
    assertThatCollectionIsOf(stackOfVectorOfAttributeList, Vector.class, AttributeList.class, Attribute.class);
    this.stackOfVectorOfAttributeList = stackOfVectorOfAttributeList;
  }

  private void assertThatCollectionIsOf(Collection collection, Class<?>... type) {
    Assert.assertNotNull("Should not be null", collection);
    Assert.assertTrue("Should not be empty", collection.size() > 0);
    for (Object entry : collection) {
      Assert.assertTrue("Expected type [" + type[0] + "], found type [" + entry.getClass() + "]", entry.getClass() == type[0]);
      if (type.length > 1) {
        Class<?>[] subTypes = new Class<?>[type.length - 1];
        System.arraycopy(type, 1, subTypes, 0, type.length - 1);
        assertThatCollectionIsOf((Collection) entry, subTypes);
      }
    }
  }

  public Collection getCollectionUndefined() {
    return collectionUndefined;
  }

  public Collection<?> getCollectionUnbounded() {
    return collectionUnbounded;
  }

  public Collection<SomeGeneric> getCollectionSomeGeneric() {
    return collectionSomeGeneric;
  }

  public Collection<? extends SomeGeneric> getCollectionExtendsSomeGeneric() {
    return collectionExtendsSomeGeneric;
  }

  public Collection<? super SomeGeneric> getCollectionSuperSomeGeneric() {
    return collectionSuperSomeGeneric;
  }

  public Collection<Collection> getCollectionOfCollectionUndefined() {
    return collectionOfCollectionUndefined;
  }

  public Collection<Collection<?>> getCollectionOfCollectionUnbounded() {
    return collectionOfCollectionUnbounded;
  }

  public Collection<Collection<SomeGeneric>> getCollectionOfCollectionOfSomeGeneric() {
    return collectionOfCollectionOfSomeGeneric;
  }

  public Collection<Collection<? extends SomeGeneric>> getCollectionOfCollectionOfExtendsSomeGeneric() {
    return collectionOfCollectionOfExtendsSomeGeneric;
  }

  public Collection<Collection<? super SomeGeneric>> getCollectionOfCollectionOfSuperSomeGeneric() {
    return collectionOfCollectionOfSuperSomeGeneric;
  }

  public Collection<AbstractCollection<AbstractList<SomeGeneric>>> getCollectionOfAbstractCollectionOfAbstractList() {
    return collectionOfAbstractCollectionOfAbstractList;
  }

  public AbstractList<AbstractSequentialList<ArrayList<SomeGeneric>>> getAbstractListOfAbstractSequentialListOfArrayList() {
    return abstractListOfAbstractSequentialListOfArrayList;
  }

  public ArrayList<CopyOnWriteArrayList<LinkedList<SomeGeneric>>> getArrayListOfCopyOnWriteArrayListOfLinkedList() {
    return arrayListOfCopyOnWriteArrayListOfLinkedList;
  }

  public LinkedList<List<Stack<SomeGeneric>>> getLinkedListOfListOfStack() {
    return linkedListOfListOfStack;
  }

  public Stack<Vector<AttributeList>> getStackOfVectorOfAttributeList() {
    return stackOfVectorOfAttributeList;
  }

  private static class SomeGeneric {

  }
}
