/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl.sampleclasses;

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

    private MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age,
            final Character gender) {
        this(name, age);
        this.gender = gender;
    }

    public MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age,
            final Character gender, final Boolean isAlive) {
        this(name, age, gender);
        this.isAlive = isAlive;
    }

    public MultiplePublicAndPrivateWithManyParamsConstructor(final String name, final Integer age,
            final Character gender, final Date dateOfBirth) {
        this(name, age, gender);
        this.dateOfBirth = dateOfBirth;
    }

}
