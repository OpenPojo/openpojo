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
import com.openpojo.reflection.adapt.impl.CoberturaPojoClassAdapter;

/**
 * @author oshoukry
 */
public class Cobertura extends AbstractCoverageDetector {
    private static final Cobertura INSTANCE = new Cobertura();

    private Cobertura() {
    }

    public static Cobertura getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Cobertura";
    }

    @Override
    public String getCoverageClassName() {
        return "net.sourceforge.cobertura.coveragedata.LightClassmapListener";
    }

    @Override
    public PojoClassFilter getPojoClassFilter() {
        return null;
    }

    @Override
    public PojoClassAdapter getPojoClassAdapter() {
        return CoberturaPojoClassAdapter.getInstance();
    }
}
