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

package com.openpojo.validation.test.impl.sampleclasses;

import java.util.Map;

/**
 * @author oshoukry
 */
public final class Bad_AGetterAndSetterClass {
  private String attribute;
  private final String stringAttribute = "a String that is Final";
  public String stringNoGetterOrSetterAttribute;

  @SuppressWarnings("rawtypes")
  private Map myMap;

  /**
   * @return the myMap
   */
  @SuppressWarnings("rawtypes")
  public Map getMyMap() {
    return myMap;
  }

  /**
   * @param myMap
   *     the myMap to set
   */
  public void setMyMap(@SuppressWarnings("rawtypes") final Map myMap) {
    this.myMap = myMap;
  }

  /**
   * @return the stringAttribute
   */
  public String getStringAttribute() {
    return stringAttribute + stringAttribute;
  }

  /**
   * @return the attribute
   */
  public String getAttribute() {
    return attribute + attribute;
  }

  /**
   * @param attribute
   *     the attribute to set
   */
  public void setAttribute(final String attribute) {
    this.attribute = attribute + attribute;
  }
}
