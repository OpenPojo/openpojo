/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.reflection.filters;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * @author oshoukry
 */
public class FilterChain implements PojoClassFilter {
    private List<PojoClassFilter> pojoClassFilters = new LinkedList<PojoClassFilter>();

    public FilterChain(final PojoClassFilter... pojoClassFilters) {
        for (PojoClassFilter pojoClassFilter : pojoClassFilters) {
            if (pojoClassFilter != null) {
                this.pojoClassFilters.add(pojoClassFilter);
            }
        }
    }

    public boolean include(final PojoClass pojoClass) {
        boolean returnValue = true;
        for (PojoClassFilter pojoClassFilter : pojoClassFilters) {
            if (returnValue) { // fail fast
                returnValue = returnValue && pojoClassFilter.include(pojoClass);
            }
        }
        return returnValue;
    }

    public Collection<PojoClassFilter> getPojoClassFilters() {
        return Collections.unmodifiableList(pojoClassFilters);
    }

    public int size() {
        return pojoClassFilters.size();
    }
}
