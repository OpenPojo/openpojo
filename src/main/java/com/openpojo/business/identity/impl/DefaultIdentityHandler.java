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

package com.openpojo.business.identity.impl;

import com.openpojo.business.identity.IdentityHandler;

/**
 * @author oshoukry
 */
public final class DefaultIdentityHandler implements IdentityHandler {
  private static final IdentityHandler INSTANCE = new DefaultIdentityHandler();

  private DefaultIdentityHandler() {
  }

  public static IdentityHandler getInstance() {
    return INSTANCE;
  }

  public boolean areEqual(final Object first, final Object second) {
    return DefaultIdentityEvaluator.getInstance().areEqual(first, second);
  }

  public int generateHashCode(final Object object) {
    return DefaultHashCodeGenerator.getInstance().doGenerate(object);
  }

  public String toString(Object object) {
    return DefaultStringanizer.getInstance().toString(object);
  }

  public void validate(final Object object) {
    DefaultBusinessValidator.getInstance().validate(object);
  }

  public boolean handlerFor(final Object object) {
    return true;
  }
}
