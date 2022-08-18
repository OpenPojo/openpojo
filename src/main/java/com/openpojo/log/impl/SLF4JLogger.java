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

package com.openpojo.log.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;
import com.openpojo.reflection.java.load.ClassUtil;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class wraps the SLF4J underlying layer.
 */
public final class SLF4JLogger extends Logger {

  private final org.slf4j.Logger logger;

  static {
    final String className = "org.slf4j.Logger";
    if (!ClassUtil.isClassLoaded(className)) {
      throw new RuntimeException(className + " - Not loaded");
    }
  }

  private SLF4JLogger(final String category) {
    logger = getLogger(category);
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  @Override
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  @Override
  public boolean isFatalEnabled() {
    return logger.isErrorEnabled();
  }

  @Override
  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  @Override
  public void trace(final Object message) {
    logger.trace(format(message));
  }

  @Override
  public void debug(final Object message) {
    logger.debug(format(message));
  }

  @Override
  public void info(final Object message) {
    logger.info(format(message));
  }

  @Override
  public void warn(final Object message) {
    logger.warn(format(message));
  }

  @Override
  public void error(final Object message) {
    logger.error(format(message));
  }

  @Override
  public void fatal(final Object message) {
    // SLF4J has no fatal level, so we're marking this as error
    this.error(message);
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }
}
