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

package com.openpojo.random.exception;

/**
 * This Exception is thrown by the random package if an error occurs during random generation/handling.
 *
 * @author oshoukry
 */
public class RandomGeneratorException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Construct an Exception with message.
   *
   * @param message
   *     The description of the exception.
   * @return An instance of the Exception.
   */
  public static RandomGeneratorException getInstance(final String message) {
    return new RandomGeneratorException(message);
  }

  private RandomGeneratorException(final String message) {
    super(message);
  }

  /**
   * Construct an Exception with message and cause.
   *
   * @param message
   *     The description of the exception.
   * @param cause
   *     The cause of the exception.
   * @return An instance of the Exception.
   */
  public static RandomGeneratorException getInstance(final String message, final Throwable cause) {
    return new RandomGeneratorException(message, cause);
  }

  private RandomGeneratorException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
