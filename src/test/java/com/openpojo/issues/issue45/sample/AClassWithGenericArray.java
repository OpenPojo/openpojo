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

package com.openpojo.issues.issue45.sample;

/**
 * @author oshoukry
 */
public class AClassWithGenericArray<T extends SomeGeneric> {
  private T[] someGenericArray;

  public T[] getSomeGenericArray() {
    return someGenericArray;
  }

  public void setSomeGenericArray(T[] someGenericArray) {
    if (someGenericArray == null)
      throw new IllegalArgumentException("Array must not be null");
    for (SomeGeneric entry : someGenericArray) {
      if (!entry.getClass().equals(SomeGeneric.class)) {
        throw new IllegalArgumentException("Expected entries of type [" + SomeGeneric.class.toString() + "] got ["
            + entry.getClass().toString() + "]");
      }
    }
    this.someGenericArray = someGenericArray;
  }

}
