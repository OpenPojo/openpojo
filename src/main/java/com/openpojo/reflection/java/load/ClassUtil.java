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

package com.openpojo.reflection.java.load;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;

/**
 * @author oshoukry
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private ClassUtil() {
        throw new IllegalStateException(ClassUtil.class.getName() + " should not be constructed!");
    }

    public static boolean isClassLoaded(String className) {
        return loadClass(className) != null;
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    public static Class<?> loadClass(String className, boolean initialize) {
        return loadClass(className, initialize, getThreadClassLoader());
    }

    public static Class<?> loadClass(String className, boolean initialize, ClassLoader classloader) {
        try {
            return Class.forName(className, initialize, classloader);
        } catch (LinkageError linkageError) { // class depends on another that wasn't found.
            LOGGER.debug("Class [{0}] has unmet dependency, loading failed [{1}]", className, linkageError);
        } catch (ClassNotFoundException classNotFoundException) { // no such class found.
            LOGGER.debug("Class [{0}] not found, loading failed [{1}]", className, classNotFoundException);
        }
        return null;
    }

    private static ClassLoader getThreadClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
