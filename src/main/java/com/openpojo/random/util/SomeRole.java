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

package com.openpojo.random.util;

import java.util.Collections;
import java.util.List;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.relation.Role;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 */
public class SomeRole extends Role {

  @SuppressWarnings("ConstantConditions")
  public SomeRole() {
    super(anyString(), anyRoleValue());
  }

  private static String anyString() {
    return RandomFactory.getRandomValue(String.class);
  }

  @SuppressWarnings("ConstantConditions")
  private static List<ObjectName> anyRoleValue() {
    try {
      return Collections.singletonList(new ObjectName("*:type=" + anyString() + ",name=" + anyString()));
    } catch (MalformedObjectNameException e) {
      throw RandomGeneratorException.getInstance("Failed to create Role", e);
    }
  }
}
