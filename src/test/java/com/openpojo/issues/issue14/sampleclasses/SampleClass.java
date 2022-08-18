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

package com.openpojo.issues.issue14.sampleclasses;

/**
 * @author oshoukry
 */
public class SampleClass {

  private Class<SampleClass> someMemberClass;

  /**
   * @return the someMemberClass
   */
  public Class<SampleClass> getSomeMemberClass() {
    return someMemberClass;
  }

  /**
   * @param someMemberClass
   *     the someMemberClass to set
   */
  public void setSomeMemberClass(final Class<SampleClass> someMemberClass) {
    this.someMemberClass = someMemberClass;
  }
}
