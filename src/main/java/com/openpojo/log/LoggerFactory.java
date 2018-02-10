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

package com.openpojo.log;

import java.lang.reflect.Constructor;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.facade.FacadeFactory;

/**
 * This Factory will create and return a logger based on the current availability of the underlying logger.
 * The system has support currently for SL4J, Log4J, JavaLogger.
 * The factory will detect runtime which logger is available and return the appropriate logger
 * The order of precedence is as follows: <br>
 * 1. org.slf4j.Logger. <br>
 * 2. org.apache.log4j.Logger. <br>
 * 3. java.util.logging.Logger.
 */
public final class LoggerFactory {
  public static final String[] SUPPORTED_LOGGERS = new String[] {
      "com.openpojo.log.impl.SLF4JLogger",
      "com.openpojo.log.impl.Log4JLogger",
      "com.openpojo.log.impl.JavaLogger" };

  private static final String DEFAULT_CATEGORY = "UndefinedLogSource";

  private static volatile Class<? extends Logger> loggerClass;
  private static volatile Constructor<? extends Logger> loggerConstructor;

  static {
    autoDetect();
  }

  /**
   * This method will auto detect the underlying logging framework available and set the current active logging
   * framework accordingly.
   */
  @SuppressWarnings("unchecked")
  public static void autoDetect() {
    Class<Logger> loadedLoggerClass = (Class<Logger>) FacadeFactory.getLoadedFacadeClass(SUPPORTED_LOGGERS);
    if (loggerClass == null || loadedLoggerClass != loggerClass) {
      setActiveLogger(loadedLoggerClass);
    }
  }

  /**
   * This method returns an instance of Logger class for logging.
   *
   * @param clazz
   *     The Class you are using to log through.
   * @return returns an instance of the Logger.
   */
  public static Logger getLogger(final Class<?> clazz) {
    return getLogger(getLoggerCategory(clazz));
  }

  /**
   * This method returns an instance of Logger class for logging.
   *
   * @param category
   *     The category you want your logs to go through.
   * @return returns and instance of the logger.
   */
  public static Logger getLogger(final String category) {
    return createNewLoggerInstance(getLoggerCategory(category));
  }

  /**
   * This method allows full control to set the active logger to a specific one.
   *
   * @param newLogger
   *     The logger class to use.
   */
  public static synchronized void setActiveLogger(final Class<? extends Logger> newLogger) {
    loggerClass = newLogger;
    loggerConstructor = getConstructorAndMakeAccessible(loggerClass);
    reportActiveLogger();
  }

  private static String getLoggerCategory(final Class<?> clazz) {
    return clazz == null ? DEFAULT_CATEGORY : clazz.getName();
  }

  private static String getLoggerCategory(final String category) {
    return category == null ? DEFAULT_CATEGORY : category;
  }

  private static Logger createNewLoggerInstance(final String category) {
    try {
      return loggerConstructor.newInstance(category);
    } catch (Throwable throwable) {
      throw ReflectionException.getInstance("Error creating new logger", throwable);
    }
  }

  private static Constructor<? extends Logger> getConstructorAndMakeAccessible(Class<? extends Logger> activeLoggerClass) {
    try {
      Constructor<? extends Logger> constructor = activeLoggerClass.getDeclaredConstructor(String.class);
      constructor.setAccessible(true);
      return constructor;
    } catch (NoSuchMethodException ignored) {
      throw ReflectionException.getInstance("Unable to retrieve logger constructor for class [" + activeLoggerClass + "]");
    }
  }

  private static void reportActiveLogger() {
    getLogger(LoggerFactory.class.getName()).info("Logging subsystem initialized to [{0}]", loggerClass.getName());
  }

  private LoggerFactory() {
    throw new UnsupportedOperationException(LoggerFactory.class.getName() + " should not be constructed!");
  }
}
