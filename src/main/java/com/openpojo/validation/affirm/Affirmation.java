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

package com.openpojo.validation.affirm;

/**
 * @author oshoukry
 */
public interface Affirmation {
  void fail(final String message);

  void affirmTrue(final String message, final boolean condition);

  void affirmFalse(final String message, final boolean condition);

  void affirmNotNull(final String message, final Object object);

  void affirmNull(final String message, final Object object);

  void affirmEquals(final String message, final Object expected, final Object actual);

  void affirmSame(final String message, final Object first, final Object second);
}
