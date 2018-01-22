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

package com.openpojo.validation.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.openpojo.validation.utils.ToStringHelper.safeToString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author oshoukry
 */
public class ToStringHelperTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void shouldNotConstruct() {
    expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("Should not be constructed");
    new ToStringHelper();
  }

  @Test
  public void shouldReturnNullWhenNull() {
    assertThat(safeToString(null), is("null"));
  }

  @Test
  public void shouldReturnToStringWhenToStringCalled() {
    Object object = new Object();
    assertThat(safeToString(object), is(object.toString()));
  }

  @Test
  public void shouldReturnErrorMessageWhenToStringThrowsErrors() {
    assertThat(safeToString(this), is("Error calling toString: 'java.lang.RuntimeException: " + anyRandomMessage() + "'"));
  }

  @Override
  public String toString() {
    throw new RuntimeException(anyRandomMessage());
  }

  private String anyRandomMessage() {
    return "Some Random Message " + this.hashCode();
  }
}
