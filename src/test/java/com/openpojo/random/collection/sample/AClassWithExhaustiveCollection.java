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

package com.openpojo.random.collection.sample;

import java.util.ArrayList;
import java.util.Collection;

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

    public void setCollectionOfCollectionOfSuperSomeGeneric(Collection<Collection<? super SomeGeneric>> collectionOfCollectionOfSuperSomeGeneric) {
        assertThatCollectionIsOf(collectionOfCollectionOfSuperSomeGeneric, ArrayList.class, SomeGeneric.class);
        this.collectionOfCollectionOfSuperSomeGeneric = collectionOfCollectionOfSuperSomeGeneric;
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

    private static class SomeGeneric {

    }
}
