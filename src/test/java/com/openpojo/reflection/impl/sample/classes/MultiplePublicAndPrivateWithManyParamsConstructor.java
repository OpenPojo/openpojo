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

package com.openpojo.reflection.impl.sample.classes;

import java.util.Date;

/**
 * @author oshoukry
 */
@SuppressWarnings("unused")
public final class MultiplePublicAndPrivateWithManyParamsConstructor {
  private final String name;
  private Integer age;
  private Character gender;
  private Boolean isAlive;
  private Date dateOfBirth;

  public MultiplePublicAndPrivateWithManyParamsConstructor(final String name) {
    this.name = name;
  }

  public MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age) {
    this(name);
    this.age = age;
  }

  private MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age, final Character gender) {
    this(name, age);
    this.gender = gender;
  }

  public MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age, final Character gender, final
  Boolean isAlive) {
    this(name, age, gender);
    this.isAlive = isAlive;
  }

  public MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age, final Character gender, final
  Date dateOfBirth) {
    this(name, age, gender);
    this.dateOfBirth = dateOfBirth;
  }

}
