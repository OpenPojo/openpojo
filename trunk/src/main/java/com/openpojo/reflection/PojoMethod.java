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


/**
 * This class encapsulates the meta data definition of a method on a class.
 *
 * @author oshoukry
 */
public interface PojoMethod extends PojoElement {
    /**
     * Invokes the underlying method represented by this Method object, on the specified object with the specified
     * parameters.
     *
     * Individual parameters are automatically unwrapped to match primitive formal parameters, and both primitive
     * and reference parameters are subject to method invocation conversions as necessary.
     *
     * If the underlying method is static, then the specified instance argument is ignored. It may be null.
     * If the number of formal parameters required by the underlying method is 0,
     * the supplied parameters array may be of length 0 or null.
     *
     * If the method completes normally, the value it returns is returned to the caller of invoke;
     * if the value has a primitive type, it is first appropriately wrapped in an object.
     * However, if the value has the type of an array of a primitive type, the elements of the array are not
     * wrapped in objects; in other words, an array of primitive type is returned.
     *
     * If the underlying method return type is void, the invocation returns null.
     *
     * note: If an exception occurs, it will be thrown as ReflectionException.
     *
     * @param instance
     *          The class instance to invoke the method on.
     * @param parameters
     *          The parameters to pass to the method upon invocation.
     * @return
     *          Value returned by the underlying method.
     *
     */
    public Object invoke(final Object instance, final Object...parameters);

    /**
     * Get the method parameters.
     * @return
     *      An array of parameterTypes.
     */
    public Class<?>[] getParameterTypes();

    /**
     * @return
     *         True if this PojoField is final-ly defined on the enclosing class.
     */
    public boolean isFinal();

    /**
     * @return
     *         True if this PojoField is static-ly defined on the enclosing class.
     */
    public boolean isStatic();

    /**
     * @return
     *         True if this PojoField is private-ly defined on the enclosing class.
     */
    public boolean isPrivate();

    /**
     * @return
     *         True if this PojoField is protected-ly defined on the enclosing class.
     */
    public boolean isProtected();

    /**
     * @return
     *         True if this PojoField is public-ly defined on the enclosing class.
     */
    public boolean isPublic();

    /**
     * @return
     *          True if this method is a constructor method and returns a new instance.
     */
    public boolean isConstructor();

    /**
     * @return
     *          Returns the return type.
     */
    public Class<?> getReturnType();
}
