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

package com.openpojo.log.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class MessageFormatterTest {

  private List<UsingCurlyBracketsTestData> getUsingCurlyBracketsTestData() {
    final List<UsingCurlyBracketsTestData> usingCurlyBracketsTestData = new LinkedList<UsingCurlyBracketsTestData>();

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData("SimpleMessage", "SimpleMessage", null));
    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(null, null, new Object[] { "nothing to format to" }));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "Here is how to put single quotes in messages '[wrapped 'with' quotes]'",
        "Here is how to put single quotes in messages ''[{0}]''",
        new Object[] { "wrapped 'with' quotes" }));

    final Date date = new Date();
    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "Two Params a string=[myString] and a date=[" + date.toString() + "]",
        "Two Params a string=[{0}] and a date=[{1}]", new Object[] { "myString", date.toString() }));

    final Exception exception = new Exception("This is an exception");

    final StackTraceElement[] fakeStackTraceElements = new StackTraceElement[3];
    String expectedLog = "Exception=[[java.lang.Exception: This is an exception";
    for (int idx = 0; idx < 3; idx++) {
      Integer lineNumber = Integer.valueOf(RandomFactory.getRandomValue(Short.class));
      if (lineNumber < 0) {
        lineNumber = lineNumber * -1;
      }

      fakeStackTraceElements[idx] = (StackTraceElement) InstanceFactory.getInstance(PojoClassFactory.getPojoClass
          (StackTraceElement.class), RandomFactory.getRandomValue(String.class), RandomFactory.getRandomValue(String.class),
          RandomFactory.getRandomValue(String.class), lineNumber);
      expectedLog = expectedLog + ", \tat " + fakeStackTraceElements[idx].getClassName() + "." +
          fakeStackTraceElements[idx].getMethodName() + "(" + fakeStackTraceElements[idx].getFileName() + ":" + lineNumber + ")";
    }
    expectedLog = expectedLog + "]]";

    exception.setStackTrace(fakeStackTraceElements);

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(expectedLog, "Exception=[{0}]", new Object[] { exception }));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "only one param assigned 1=[1st Param] 2=[{1}]",
        "only one param assigned 1=[{0}] 2=[{1}]",
        new Object[] { "1st Param" }));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "only one will print - [extra parameter]",
        "only one will print - [{0}]",
        new Object[] { "extra parameter", " should not print" }));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "nothing shows up for null args=[{0}]",
        "nothing shows up for null args=[{0}]", null));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "Message and one param [param one] and this one is null=[null]",
        "Message and one param [{0}] and this one is null=[{1}]",
        new Object[] { "param one", null }));

    usingCurlyBracketsTestData.add(new UsingCurlyBracketsTestData(
        "Nested Array unfolds [[1, 2, 3], [check, me, out]]",
        "Nested Array unfolds [{0}, {1}]", new Object[] {
        new Integer[] { 1, 2, 3 },
        new String[] { "check", "me", "out" } }));

    return usingCurlyBracketsTestData;
  }

  private List<FlattenArrayToStringTestData> getFlattenArrayToStringTestData() {
    final List<FlattenArrayToStringTestData> flattenArrayToStringTestData = new LinkedList<FlattenArrayToStringTestData>();

    flattenArrayToStringTestData.add(new FlattenArrayToStringTestData("[[1, 2, 3]]", new Object[] { new Integer[] { 1, 2, 3 } }));

    flattenArrayToStringTestData.add(new FlattenArrayToStringTestData(
        "[[This, is, a string]]",
        new Object[] { new String[] { "This", "is", "a string" } }));

    return flattenArrayToStringTestData;
  }

  @Test
  public final void testUsingCurlyBrackets() {
    for (final UsingCurlyBracketsTestData entry : getUsingCurlyBracketsTestData()) {
      Assert.assertEquals(entry.expected, MessageFormatter.usingCurlyBrackets(entry.message, entry.fields));
    }
  }

  @Test
  public final void testFlattenArrayToString() {
    for (final FlattenArrayToStringTestData entry : getFlattenArrayToStringTestData()) {
      Assert.assertEquals(entry.expected, Arrays.toString(MessageFormatter.formatArgsToStrings(entry.array)));
    }

  }

  private static final String GENERATE_CURLY_BRACKET_TOKEN_PREFIX = "[{";
  private static final String GENERATE_CURLY_BRACKET_TOKEN_POSTFIX = "}]";
  private static final int TOKEN_COUNTER_START = 0;

  @Test
  public final void testBoundaryConditionsOnGenerateCurlyBracketTokens() {

    Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(Integer.MIN_VALUE));
    Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(-1));
    Assert.assertEquals("", MessageFormatter.generateCurlyBracketTokens(0));
    Assert.assertEquals(GENERATE_CURLY_BRACKET_TOKEN_PREFIX + TOKEN_COUNTER_START + GENERATE_CURLY_BRACKET_TOKEN_POSTFIX,
        MessageFormatter.generateCurlyBracketTokens(1));
  }

  @Test
  public final void testActualArraySentAsObject() {
    final String expected = "[false, true]";
    final Object array = Array.newInstance(boolean.class, 2);
    Array.set(array, 0, false);
    Array.set(array, 1, true);
    Assert.assertEquals(expected, MessageFormatter.format(array));
  }

  @Test
  public final void testActualArraySentAsObjectWithNulls() {
    final String expected = "[3, null, 14]";
    final Object array = Array.newInstance(Integer.class, 3);
    Array.set(array, 0, 3);
    Array.set(array, 2, 14);
    Assert.assertEquals(expected, MessageFormatter.format(array));
  }

  @Test
  public final void testActualArraySentWithSizeZero() {
    final String expected = "[]";
    final Object array = Array.newInstance(Integer.class, 0);
    Assert.assertEquals(expected, MessageFormatter.format(array));
  }

  @Test
  public void formatArrayOfArrays() {
    final String expected = "[[1, 2]]";
    int[][] arrayOfArray = { { 1, 2 } };
    Assert.assertEquals(expected, MessageFormatter.format(arrayOfArray));
  }

  private static final int MAX_NUMBER_OF_RANDOM_TOKENS = 10;

  @Test
  public final void testRandomGenerateCurlyBracketTokens() {
    final int randomNumberOfTokensBetween0And10 = new Random().nextInt(MAX_NUMBER_OF_RANDOM_TOKENS + 1);

    String assertString = "";
    for (int counter = 0; counter < randomNumberOfTokensBetween0And10; counter++) {
      assertString += GENERATE_CURLY_BRACKET_TOKEN_PREFIX + counter + GENERATE_CURLY_BRACKET_TOKEN_POSTFIX;
    }
    Assert.assertEquals(assertString, MessageFormatter.generateCurlyBracketTokens(randomNumberOfTokensBetween0And10));

  }

  private static final class UsingCurlyBracketsTestData {
    private final String expected;
    private final String message;
    private final Object[] fields;

    private UsingCurlyBracketsTestData(final String expected, final String message, final Object[] fields) {
      this.expected = expected;
      this.message = message;
      this.fields = fields;
    }
  }

  private static final class FlattenArrayToStringTestData {
    private final String expected;
    private final Object[] array;

    private FlattenArrayToStringTestData(final String expected, final Object[] array) {
      this.expected = expected;
      this.array = array;
    }
  }
}
