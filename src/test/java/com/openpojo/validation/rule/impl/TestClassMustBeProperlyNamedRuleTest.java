/*
 * Copyright (c) 2010-2019 Osman Shoukry
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

package com.openpojo.validation.rule.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class TestClassMustBeProperlyNamedRuleTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldThrowExceptionIfNoAnnotationsLoaded() {
    String noneExistentClass = this.getClass().getName() + "DoesNotExist";

    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("No annotations loaded, expected any of [" + noneExistentClass + "]");

    new TestClassMustBeProperlyNamedRule(getEmptyList(), getEmptyList(), Arrays.asList(noneExistentClass));
  }

  @Test
  public void assertDefaultAnnotation() {
    String[] expectedList = { "org.testng.annotations.Test", "org.junit.Test", "org.junit.jupiter.api.Test" };
    assertThat(Arrays.asList(TestClassMustBeProperlyNamedRule.DEFAULT_ANNOTATIONS),
        containsInAnyOrder(expectedList));
  }

  private List<String> getEmptyList() {
    return Collections.<String>emptyList();
  }
}