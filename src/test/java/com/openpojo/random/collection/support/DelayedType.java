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

package com.openpojo.random.collection.support;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author oshoukry
 */
public class DelayedType implements Delayed {

    public long getDelay(TimeUnit unit) {
        return 0;
    }

    public int compareTo(Delayed o) {
        if (System.identityHashCode(this) > System.identityHashCode(o))
            return 1;
        if (System.identityHashCode(this) < System.identityHashCode(o))
            return -1;
        return  0;
    }
}
