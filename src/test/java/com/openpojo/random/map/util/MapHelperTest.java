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

package com.openpojo.random.map.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author oshoukry
 */
public class MapHelperTest {
  @Test
  public void shouldReturnWithoutGenerationIfTypeOrKeyAreNull() {
    Map emptyMap = new HashMap();
    Map actual = MapHelper.buildMap(emptyMap, null, null);
    assertThat(actual.size(), is(0));
    assertThat(actual, sameInstance(emptyMap));

    actual = MapHelper.buildMap(emptyMap, this.getClass(), null);
    assertThat(actual.size(), is(0));
    assertThat(actual, sameInstance(emptyMap));

    actual = MapHelper.buildMap(emptyMap, null, this.getClass());
    assertThat(actual.size(), is(0));
    assertThat(actual, sameInstance(emptyMap));

  }

  @Test
  public void shouldReturnNullIfMapIsNull() {
    assertThat(MapHelper.buildMap(null, null, null), nullValue());
    assertThat(MapHelper.buildMap(null, this.getClass(), null), nullValue());
    assertThat(MapHelper.buildMap(null, null, this.getClass()), nullValue());
    assertThat(MapHelper.buildMap(null, this.getClass(), this.getClass()), nullValue());
  }

}
