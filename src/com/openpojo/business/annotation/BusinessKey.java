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
 * This annotation is used to determine what part of a POJO participates in a business key.
 * @author oshoukry
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface BusinessKey {
    /**
     * In case this is a string key and we need to ignore case.
     */
    boolean ignoreCase() default false;

    /**
     * Is this field required to be populated.
     * 
     * @return
     */
    boolean isRequired() default true;

    /**
     * Set to true if this key is part of a group where any one of the group needs to be not null.
     * Setting Composite to true, shadows the "isRequired" since composite is OR-ing against any that are populated.
     */
    boolean composite() default false;
}
