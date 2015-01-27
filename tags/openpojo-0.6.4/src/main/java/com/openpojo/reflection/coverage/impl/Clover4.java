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

package com.openpojo.reflection.coverage.impl;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.impl.CloverPojoClassAdapter;
import com.openpojo.reflection.filters.FilterCloverClasses;

/**
 * @author oshoukry
 */
public class Clover4 extends AbstractCoverageDetector {
    private static final Clover4 INSTANCE = new Clover4();

    private Clover4() {
    }

    public static Clover4 getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Clover 4";
    }

    @Override
    public String getCoverageClassName() {
        return "com_atlassian_clover.TestNameSniffer";
    }

    @Override
    public PojoClassFilter getPojoClassFilter() {
        return FilterCloverClasses.getInstance();
    }

    @Override
    public PojoClassAdapter getPojoClassAdapter() {
        return CloverPojoClassAdapter.getInstance();
    }
}
