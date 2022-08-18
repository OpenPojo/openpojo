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

package com.openpojo.reflection;

/**
 * This interface provides a uniform way to validate accessibility levels.
 *
 * @author oshoukry
 */
public interface Accessible {
  /**
   * Locally visible within enclosing element.
   * @return true if private.
   */
  boolean isPrivate();

  /**
   * Visible to extending elements.
   * @return true if protected.
   */
  boolean isProtected();

  /**
   * Visible to all.
   * @return true if public.
   */
  boolean isPublic();

  /**
   * Package visibility only, often referred to default visibility when no visibility keywords are assigned.
   * @return true if default.
   */
  boolean isPackagePrivate();
}
