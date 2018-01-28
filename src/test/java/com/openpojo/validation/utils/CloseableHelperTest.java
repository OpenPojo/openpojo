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

import java.io.Closeable;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author oshoukry
 */
public class CloseableHelperTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void shouldNotConstruct() {
    expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("Should not be constructed");
    new CloseableHelper();
  }

  @Test
  public void shouldNotFailIfNull() {
    CloseableHelper.closeResources((Closeable[]) null);
  }

  @Test
  public void shouldNotFailIfArrayElementsAreNull() {
    CloseableHelper.closeResources(null, null);
  }

  @Test
  public void shouldCallCloseOnClosable() {
    CloseableSpy closeableSpy = new CloseableSpy();
    Assert.assertThat(closeableSpy.closeCalled, is(false));

    CloseableHelper.closeResources(closeableSpy);
    Assert.assertThat(closeableSpy.closeCalled, is(true));
  }

  @Test
  public void nullEntryShouldNotblockCloseCallOnSubsequentClosable() {
    CloseableSpy closeableSpy = new CloseableSpy();
    Assert.assertThat(closeableSpy.closeCalled, is(false));

    CloseableHelper.closeResources(null, closeableSpy);
    Assert.assertThat(closeableSpy.closeCalled, is(true));
  }

  @Test
  public void exceptionalEntryShouldNotBlockCloseCallOnSubsequentClosable() {
    CloseableSpy closeableSpy = new CloseableSpy();
    Assert.assertThat(closeableSpy.closeCalled, is(false));

    CloseableHelper.closeResources(new ExceptionalCloseable(), closeableSpy);
    Assert.assertThat(closeableSpy.closeCalled, is(true));

  }

  private static class CloseableSpy implements Closeable {
    private boolean closeCalled = false;
    public void close() throws IOException {
      closeCalled = true;
    }
  }

  private static class ExceptionalCloseable implements Closeable {
    public void close() throws IOException {
      throw new IOException();
    }
  }
}