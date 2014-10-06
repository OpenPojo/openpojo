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
import com.openpojo.reflection.adapt.impl.JacocoPojoClassAdapter;

/**
 * @author oshoukry
 */
public class Jacoco extends AbstractCoverageDetector {
    private static final Jacoco INSTANCE = new Jacoco();

    private Jacoco() {
    }

    public static Jacoco getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Jacoco";
    }

    @Override
    public String getCoverageClassName() {
        return "org.jacoco.agent.rt.IAgent";//return "org.jacoco.core.JaCoCo";
    }

    @Override
    public PojoClassFilter getPojoClassFilter() {
        return null;
    }

    @Override
    public PojoClassAdapter getPojoClassAdapter() {
        return JacocoPojoClassAdapter.getInstance();
    }
}
