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

package com.openpojo.utils.filter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This class is a utility class to help with testing.
 * It will log every filter call for later validation.
 *
 * @author oshoukry
 */
public class LoggingPojoClassFilter implements PojoClassFilter {
  private final List<PojoClass> pojoClassCallLogs = new LinkedList<PojoClass>();
  private boolean returnValue;

  public void setReturnValue(final boolean returnValue) {
    this.returnValue = returnValue;
  }

  public boolean getReturnValue() {
    return returnValue;
  }

  public List<PojoClass> getPojoClassCallLogs() {
    return Collections.unmodifiableList(pojoClassCallLogs);
  }

  public boolean include(final PojoClass pojoClass) {
    pojoClassCallLogs.add(pojoClass);
    return returnValue;
  }
}
