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

package com.openpojo.reflection.java.version;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oshoukry
 */
public class VersionParser {

  public static List<Integer> getVersionParts(String version) {
    List<Integer> parts = new ArrayList<Integer>();
    if (version == null)
      return parts;

    String part = "";
    for (char c : version.toCharArray()) {
      if (c >= '0' && c <= '9')
        part += c;
      if (c == '.') {
        if (part.length() > 0)
          parts.add(Integer.parseInt(part));
        part = "";
      }
    }

    if (part.length() > 0)
      parts.add(Integer.parseInt(part));

    return parts;
  }

  private VersionParser() {
    throw new UnsupportedOperationException(VersionParser.class.getName() +  " should not be constructed!");

  }
}
