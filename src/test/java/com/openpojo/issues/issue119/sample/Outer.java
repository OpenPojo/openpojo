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

package com.openpojo.issues.issue119.sample;

import java.util.List;

@SuppressWarnings("unused")
public class Outer {
  private String outerName;

  public String getOuterName() {
    return outerName;
  }

  public void setOuterName(String outerName) {
    this.outerName = outerName;
  }

  public class Level1Inner {
    private String level1InnerName;

    private Level1Inner(String level1InnerName) {
      this.level1InnerName = level1InnerName;
    }

    public String getLevel1InnerName() {
      return level1InnerName;
    }

    public void setLevel1InnerName(String level1InnerName) {
      this.level1InnerName = level1InnerName;
    }

    public class Level2Inner {
      private String level2InnerName;

      private Level2Inner(List<String> names) {
        if (names.size() > 0)
          level2InnerName = names.get(0);
      }

      public String getLevel2InnerName() {
        return level2InnerName;
      }

      public void setLevel2InnerName(String level2InnerName) {
        this.level2InnerName = level2InnerName;
      }

    }
  }
}
