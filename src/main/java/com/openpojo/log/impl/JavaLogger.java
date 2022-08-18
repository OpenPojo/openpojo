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

import java.util.logging.Level;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * Our own JavaLogger adapter.<br>
 * The Mapping is done as follows:<br>
 * - Trace = Level.FINEST<br>
 * - Debug = Level.FINER<br>
 * - Info = Level.FINE<br>
 * - Warn = Level.WARNING<br>
 * - Error = Level.SEVERE<br>
 * - Fatal = Level.SEVERE<br>
 */
public final class JavaLogger extends Logger {

  private final java.util.logging.Logger logger;

  static {
    final String className = "java.util.logging.Logger";
    if (!ClassUtil.isClassLoaded(className)) {
      throw new RuntimeException(className + " - Not loaded");
    }
  }

  private JavaLogger(final String category) {
    logger = java.util.logging.Logger.getLogger(category);
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isLoggable(Level.FINEST);
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isLoggable(Level.FINER);
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isLoggable(Level.FINE);
  }

  @Override
  public boolean isWarnEnabled() {
    return logger.isLoggable(Level.WARNING);
  }

  @Override
  public boolean isErrorEnabled() {
    return logger.isLoggable(Level.WARNING);
  }

  @Override
  public boolean isFatalEnabled() {
    return logger.isLoggable(Level.SEVERE);
  }

  @Override
  public void trace(final Object message) {
    logger.finest(format(message));
  }

  @Override
  public void debug(final Object message) {
    logger.finer(format(message));
  }

  @Override
  public void info(final Object message) {
    logger.fine(format(message));
  }

  @Override
  public void warn(final Object message) {
    logger.warning(format(message));
  }

  @Override
  public void error(final Object message) {
    // JavaLogging doesn't have error level, so we'll treat it as severe.
    fatal(message);
  }

  @Override
  public void fatal(final Object message) {
    logger.severe(format(message));
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }

}
