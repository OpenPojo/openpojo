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

package com.openpojo.random.thread;

import java.util.HashSet;
import java.util.Set;

/**
 * This class holds random generated types on current thread.
 *
 * @author oshoukry
 */
public class GeneratedRandomValues {
    private static ThreadLocal<Set<Class<?>>> threadLocal = new ThreadLocal<Set<Class<?>>>() {
        @Override
        protected Set<Class<?>> initialValue() {
            return new HashSet<Class<?>>();
        }
    };

    /**
     * Add type to the thread list of types generated.
     *
     * @param type
     *            The type to add.
     */
    public static void add(final Class<?> type) {
        threadLocal.get().add(type);
    }

    /**
     * Check if this type was added by this thread already.
     *
     * @param type
     *            The type to check for.
     * @return
     *         Returns true if the type has been added by this thread already.
     */
    public static boolean contains(final Class<?> type) {
        Set<Class<?>> generatedValues = threadLocal.get();
        return generatedValues.contains(type);
    }

    /**
     * Remove a specific type from the list.
     *
     * @param type
     *            The type to remove.
     */
    public static void remove(final Class<?> type) {
        threadLocal.get().remove(type);
    }

}
