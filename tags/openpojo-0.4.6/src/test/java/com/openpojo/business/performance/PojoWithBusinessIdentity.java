/*
 * Copyright (c) 2010-2013 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.business.performance;

import com.openpojo.business.annotation.BusinessKey;
import static com.openpojo.business.BusinessIdentity.*;

/**
 * @author oshoukry
 */
class PojoWithBusinessIdentity {

    @BusinessKey
    private final String firstName;

    @BusinessKey(caseSensitive = false)
    private final String lastName;

    @BusinessKey
    private final Integer age;

    @BusinessKey
    private final Character sex;

    @Override
    public boolean equals(Object other) {
        return areEqual(this, other);
    }

    @Override
    public int hashCode() {
        return getHashCode(this);
    }

    public PojoWithBusinessIdentity(final String firstName, final String lastName, final Integer age, final Character sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    Integer getAge() {
        return age;
    }

    Character getSex() {
        return sex;
    }
}
