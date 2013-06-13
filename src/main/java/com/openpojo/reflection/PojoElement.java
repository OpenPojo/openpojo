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

package com.openpojo.reflection;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This is the parent interface for everything wrappable.
 * 
 * @author oshoukry
 */
public interface PojoElement {

    /**
     * Get the name of the element.
     * 
     * @return
     *         Return the name of the package, class, field... etc.
     */
    public String getName();

    /**
     * Get all annotations defined on element.
     * 
     * @return
     *         Get Annotations
     */
    public List<? extends Annotation> getAnnotations();

    /**
     * Get specified instance of an annotation defined on element.
     * 
     * @param <T>
     *            Class Type of annotation.
     * @param annotationClass
     *            The annotation class.
     * @return
     *         The definition of this annotation on the PojoField.
     */
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass);

}
