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
package com.openpojo.business.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate a field is part of the business identity.
 * Fields tagged with BusinessKey annotation will be part of the equals / hashcode.
 * 
 * @author oshoukry
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface BusinessKey {
    /**
     * Set to true if the field is of type Character or CharSequence to ignore case when comparing.
     */
    boolean caseSensitive() default true;

    /**
     * Set to True to indicate field required to be populated.
     */
    boolean required() default true;

    /**
     * Set to true if this key is part of a group where any one of the group needs to be not null.
     * Setting Composite to true, shadows the "required" above, since composite is OR-ing against any that are
     * populated.
     */
    boolean composite() default false;
}
