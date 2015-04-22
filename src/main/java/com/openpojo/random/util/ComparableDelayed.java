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

package com.openpojo.random.util;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author oshoukry
 */
public class ComparableDelayed implements Delayed {
    private final long delay = (System.currentTimeMillis() % 5) - 3;

    public long getDelay(TimeUnit unit) {
        return delay;
    }

    public int compareTo(Delayed other) {
        if (other == null || this.hashCode() > other.hashCode())
            return 1;
        if (this.hashCode() == other.hashCode())
            return 0;
        return -1;
    }
}
