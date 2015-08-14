/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.validation;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This Validator interface defines how PojoValidation is handled.
 *
 * @author oshoukry
 */
public interface Validator {

    /**
     * Validate for a given package and a filter.
     *
     * @param packageName The package name to run the validation against.
     * @param filter The filter(s) to be used for filtering which classes are to be included in the validation.
     */
    void validate(String packageName, PojoClassFilter... filter);

    /**
     * Validate for a given list of pojo classes.
     *
     * @param pojoClasses the set of PojoClasses to validate.
     */
    void validate(List<PojoClass> pojoClasses);

    /**
     * Validate for a given pojoClass.
     *
     * @param pojoClass the PojoClass to validate.
     */
    void validate(PojoClass pojoClass);
}
