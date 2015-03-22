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

package com.openpojo.reflection;


import java.lang.reflect.Type;
import java.util.List;

/**
 * This class encapsulates the meta data definition of a method on a class.
 *
 * @author oshoukry
 */
public interface PojoMethod extends PojoElement {
    /**
     * Invokes the underlying method represented by this Method object, on the specified object with the specified
     * parameters.
     * <p/>
     * Individual parameters are automatically unwrapped to match primitive formal parameters, and both primitive
     * and reference parameters are subject to method invocation conversions as necessary.
     * <p/>
     * If the underlying method is static, then the specified instance argument is ignored. It may be null.
     * If the number of formal parameters required by the underlying method is 0,
     * the supplied parameters array may be of length 0 or null.
     * <p/>
     * If the method completes normally, the value it returns is returned to the caller of invoke;
     * if the value has a primitive type, it is first appropriately wrapped in an object.
     * However, if the value has the type of an array of a primitive type, the elements of the array are not
     * wrapped in objects; in other words, an array of primitive type is returned.
     * <p/>
     * If the underlying method return type is void, the invocation returns null.
     * <p/>
     * note: If an exception occurs, it will be thrown as ReflectionException.
     *
     * @param instance
     *         The class instance to invoke the method on.
     * @param parameters
     *         The parameters to pass to the method upon invocation.
     * @return Value returned by the underlying method.
     */
    public Object invoke(final Object instance, final Object... parameters);


    /**
     * Get method parameters as PojoParameters.
     * If method doesn't have any parameters an empty list is returned.
     *
     * @return
     *      a List of PojoParameters.
     */
    public List<PojoParameter> getPojoParameters();

    /**
     * Get the method parameters.
     *
     * @return An array of parameterTypes.
     */
    public Class<?>[] getParameterTypes();

    /**
     * Get the method generic parameter types
     * Note: Do not use, still in experimental mode.
     *
     * @return An array of generic parameter types.
     */
    public Type[] getGenericParameterTypes();

    /**
     * @return True if this PojoMethod is final-ly defined on the enclosing class.
     */
    public boolean isFinal();

    /**
     * @return
     *         true if this PojoMethod is synthetic (i.e. jdk compiler generated).
     */
    public boolean isSynthetic();

    /**
     * @return True if this PojoMethod is static-ly defined on the enclosing class.
     */
    public boolean isStatic();

    /**
     * @return True if this PojoMethod is private-ly defined on the enclosing class.
     */
    public boolean isPrivate();

    /**
     * @return True if this PojoMethod is protected-ly defined on the enclosing class.
     */
    public boolean isProtected();

    /**
     * @return True if this PojoMethod is public-ly defined on the enclosing class.
     */
    public boolean isPublic();

    /**
     * @return True if this method is a constructor method and returns a new instance.
     */
    public boolean isConstructor();

    /**
     * @return True if this method is abstract-ly defined on the enclosing class.
     */
    public boolean isAbstract();

    /**
     * @return Returns the return type.
     */
    public Class<?> getReturnType();
}
