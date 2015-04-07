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

package com.openpojo.reflection.coverage.impl;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.coverage.CoverageDetector;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public abstract class AbstractCoverageDetector implements CoverageDetector {

    public abstract String getName();
    public abstract String getCoverageClassName();
    public abstract PojoClassFilter getPojoClassFilter();
    public abstract PojoClassAdapter getPojoClassAdapter();

    public boolean isLoaded() {
        return ClassUtil.isClassLoaded(getCoverageClassName());
    }
}
