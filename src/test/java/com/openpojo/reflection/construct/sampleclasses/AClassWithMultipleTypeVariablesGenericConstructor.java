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

package com.openpojo.reflection.construct.sampleclasses;

/**
 * @author oshoukry
 */
public class AClassWithMultipleTypeVariablesGenericConstructor<K, V, T extends CharSequence> {
    private K myK;
    private V myV;
    private T myT;

    public AClassWithMultipleTypeVariablesGenericConstructor(final K k, final V v, final T t) {
        this.myK = k;
        this.myV = v;
        this.myT = t;
    }

    public K getMyK() {
        return myK;
    }

    public V getMyV() {
        return myV;
    }

    public T getMyT() {
        return myT;
    }
}
