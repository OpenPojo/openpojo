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

package com.openpojo.random.map.sample;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.util.MapCollectionAssertionHelper;
import com.openpojo.random.util.SerializeableComparableObject;

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

    private Map<Map<Object, SomeGeneric>, Map<SomeInterface, SerializeableComparableObject>>
            mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject;

    private Map<Map<? extends SerializeableComparableObject, ? extends SomeInterface>, Map<? extends SomeGeneric, ?>>
            mapOfMapOfExtendsSomeGeneric;

    private Map<Map<? super SomeGeneric, ? super SomeGeneric>, Map<? super SomeGeneric, ? super SomeGeneric>> mapOfMapOfSuperSomeGeneric;

    private List<Map> listOfMap;
    private Map<List, Queue> mapOfListQueue;

    private Map<SomeGeneric, List<Map<List, Queue>>> mapOfSomeGenericListOfMapOfListQueue;
    private Map<SomeInterface, SomeGeneric> mapOfSomeInterfaceSomeGeneric;

    public void setMapUndefined(Map mapUndefined) {
        assertParameters(mapUndefined,
                Map.class,
                    SerializeableComparableObject.class,
                    SerializeableComparableObject.class);
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
        assertParameters(mapStringInteger,
                Map.class,
                    String.class,
                    Integer.class);
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
                        SerializeableComparableObject.class,
                        SerializeableComparableObject.class,
                    Map.class,
                        SerializeableComparableObject.class,
                        SerializeableComparableObject.class);

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
            Map<SomeInterface, SerializeableComparableObject>> mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject) {
        assertParameters(mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject,
                Map.class,
                    Map.class,
                        Object.class,
                        SomeGeneric.class,
                    Map.class,
                        SomeInterface.class,
                        SerializeableComparableObject.class);

        this.mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject =
                mapOfMap_Object_SomeGeneric_And_MapOfSomeGeneric_SerializableComparableObject;
    }

    public void setMapOfMapOfExtendsSomeGeneric(Map<Map<? extends SerializeableComparableObject, ? extends SomeInterface>, Map<? extends
            SomeGeneric, ?>> mapOfMapOfExtendsSomeGeneric) {
        assertParameters(mapOfMapOfExtendsSomeGeneric,
                Map.class,
                    Map.class,
                        SerializeableComparableObject.class,
                        SomeInterface.class,
                    Map.class,
                        SomeGeneric.class,
                        Object.class);

        this.mapOfMapOfExtendsSomeGeneric = mapOfMapOfExtendsSomeGeneric;
    }

    public void setMapOfMapOfSuperSomeGeneric(Map<Map<? super SomeGeneric, ? super SomeGeneric>, Map<? super SomeGeneric, ? super
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
                        SerializeableComparableObject.class,
                        SerializeableComparableObject.class);
        this.listOfMap = listOfMap;
    }

    public void setMapOfListQueue(Map<List, Queue> mapOfListQueue) {
        assertParameters(mapOfListQueue,
                Map.class,
                    List.class,
                        SerializeableComparableObject.class,
                        Queue.class,
                    SerializeableComparableObject.class);
        this.mapOfListQueue = mapOfListQueue;
    }

    public void setMapOfSomeGenericListOfMapOfListQueue(Map<SomeGeneric, List<Map<List, Queue>>> mapOfSomeGenericListOfMapOfListQueue) {
        assertParameters(mapOfSomeGenericListOfMapOfListQueue,
                Map.class,
                    SomeGeneric.class,
                    List.class,
                        Map.class,
                            List.class,
                                SerializeableComparableObject.class,
                            Queue.class,
                                SerializeableComparableObject.class);
        this.mapOfSomeGenericListOfMapOfListQueue = mapOfSomeGenericListOfMapOfListQueue;
    }

    public void setMapOfSomeInterfaceSomeGeneric(Map<SomeInterface, SomeGeneric> mapOfSomeInterfaceSomeGeneric) {
        assertParameters(mapOfSomeInterfaceSomeGeneric,
                Map.class,
                    SomeInterface.class,
                    SomeGeneric.class);
        this.mapOfSomeInterfaceSomeGeneric = mapOfSomeInterfaceSomeGeneric;
    }

    private void assertParameters(Object input, Class<?>... expectedTypes) {
        LoggerFactory.getLogger(this.getClass()).debug("Checking: [" + input + "] with types " + Arrays.toString(expectedTypes));

        MapCollectionAssertionHelper.assertParametersStructure(input, expectedTypes);
    }

}
