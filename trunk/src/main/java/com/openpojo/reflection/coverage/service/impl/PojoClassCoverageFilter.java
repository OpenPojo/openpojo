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

package com.openpojo.reflection.coverage.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * @author oshoukry
 */
public class PojoClassCoverageFilter implements PojoClassFilter {
    private Set<PojoClassFilter> filters = new HashSet<PojoClassFilter>();

    public void add(PojoClassFilter pojoClassFilter) {
        if (pojoClassFilter != null) {
            filters.add(pojoClassFilter);
        }
    }

    public boolean include(PojoClass pojoClass) {
        for (PojoClassFilter filter : filters) {
            if (!filter.include(pojoClass))
                return false;
        }
        return true;
    }
}
