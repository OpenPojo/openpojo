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
package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This filter will filter out any non-concrete class (i.e. interface, Enum and Abstract).
 *
 * @author oshoukry
 */
public class FilterNonConcrete implements PojoClassFilter {

    private PojoClassFilter nextFilter;

    public FilterNonConcrete() {
    }

    /**
     * This construtor will is deprecated, please use FilterChain to chain filters together.
     * @param nextFilter
     *          The filter that is next to be evaluated.
     * @deprecated please use {@link FilterChain instead}
     */
    @Deprecated
    public FilterNonConcrete(final PojoClassFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    public boolean include(final PojoClass pojoClass) {
        return pojoClass.isConcrete() && (nextFilter == null || nextFilter.include(pojoClass));
    }

}
