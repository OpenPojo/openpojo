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

package com.openpojo.business.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to indicate a field is part of the business identity.
 * Fields tagged with BusinessKey annotation will be part of the equals / hashcode.
 * Key fields are: <br>
 * 1. caseSensitive (default is true) <br>
 * 2. required (default is true) <br>
 * 3. composite (default is false) <br>
 * <br>
 * <strong>caseSensitive usage:</strong><br>
 * If a field tagged with caseSensitive=false (i.e. not case sensitive) AND the field is of type
 * Character or CharacterSequence, the field must be normalized and then hashcode/equality is performed. <br>
 * <br>
 * <strong>Example:</strong> {@link BusinessKey}(caseSensitive = false)
 * String one = "hello world" <br>
 * must have the same hashcode &amp; equality as:<br>
 * one = "HELLO WORLD" <br>
 * <strong>required usage:</strong><br>
 * If a field is tagged with required = false, then the field will only be used in equality only if its populated.
 * Two null fields tagged with required = false are equal. <br>
 * <br>
 * <strong>composite usage:</strong><br>
 * If a field is tagged with composite = true, then two things happen, first, required configuration is ignored,
 * and this field is treated as optional part of a group. This also means that one of the fields tagged with composite
 * MUST have a value.<br>
 * <strong>Example:</strong>
 * If you had a POJO called Person, that had a FirstName &amp; LastName fields, and you want to set a rule that is for this
 * POJO to be complete, one of the names MUST be populated, but its not important which one. <br>
 * This is typical composite key usage.
 *
 * @author oshoukry
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface BusinessKey {
  /**
   * Set to true if the field is of type Character or CharSequence to ignore case when comparing.
   *
   * @return returns the value for caseSensitive; defaults to true.
   */
  boolean caseSensitive() default true;

  /**
   * Set to True to indicate field required to be populated.
   *
   * @return returns true if the field is required for business identity validation, defaults to true.
   */
  boolean required() default true;

  /**
   * Set to true if this key is part of a group where any one of the group needs to be not null.
   * Setting Composite to true, shadows the "required" above, since composite is OR-ing against any that are
   * populated.  With at least one of the composite group being non-null.
   *
   * @return returns weather this key is part of a group, defaults to false;
   */
  boolean composite() default false;
}
