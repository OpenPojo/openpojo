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

package com.openpojo.issues.issue85;

/**
 * @author oshoukry
 */
@SuppressWarnings("unused")
class AClassWithPrimitiveArrays {
  private byte[] bytes;
  private char[] chars;
  private short[] shorts;
  private int[] ints;
  private long[] longs;
  private float[] floats;
  private double[] doubles;
  private boolean[] booleans;

  public AClassWithPrimitiveArrays(byte[] bytes, char[] chars, short[] shorts, int[] ints, long[] longs, float[] floats,
                                   double[] doubles, boolean[] booleans) {
    this.bytes = bytes;
    this.chars = chars;
    this.shorts = shorts;
    this.ints = ints;
    this.longs = longs;
    this.floats = floats;
    this.doubles = doubles;
    this.booleans = booleans;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public char[] getChars() {
    return chars;
  }

  public void setChars(char[] chars) {
    this.chars = chars;
  }

  public short[] getShorts() {
    return shorts;
  }

  public void setShorts(short[] shorts) {
    this.shorts = shorts;
  }

  public int[] getInts() {
    return ints;
  }

  public void setInts(int[] ints) {
    this.ints = ints;
  }

  public long[] getLongs() {
    return longs;
  }

  public void setLongs(long[] longs) {
    this.longs = longs;
  }

  public float[] getFloats() {
    return floats;
  }

  public void setFloats(float[] floats) {
    this.floats = floats;
  }

  public double[] getDoubles() {
    return doubles;
  }

  public void setDoubles(double[] doubles) {
    this.doubles = doubles;
  }

  public boolean[] getBooleans() {
    return booleans;
  }

  public void setBooleans(boolean[] booleans) {
    this.booleans = booleans;
  }
}
