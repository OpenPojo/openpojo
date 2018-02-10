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

package com.openpojo.reflection.construct.sampleclasses;

import java.util.List;

/**
 * @author oshoukry
 */
public class AClassWithGenericConstructor {
  private final List<Child> myChildren;

  public AClassWithGenericConstructor(final List<Child> myChildren) {
    this.myChildren = myChildren;
  }

  public List<Child> getMyChildren() {
    return myChildren;
  }

  public static class Child {
    private final String name;

    public Child(final String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
