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

package com.openpojo.reflection.coverage.service.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.coverage.CoverageDetector;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterService;

/**
 * @author oshoukry
 */
public class DefaultPojoCoverageFilterService implements PojoCoverageFilterService {

    private PojoClassCoverageFilter pojoClassCoverageFilter = new PojoClassCoverageFilter();
    private PojoClassCoverageAdapter pojoClassCoverageAdapter = new PojoClassCoverageAdapter();

    public synchronized void registerCoverageDetector(CoverageDetector coverageDetector) {
        if (coverageDetector.isLoaded()) {
            System.out.println(coverageDetector.getName() + " detected, auto-configuring OpenPojo to ignore its structures.");
            pojoClassCoverageFilter.add(coverageDetector.getPojoClassFilter());
            pojoClassCoverageAdapter.add(coverageDetector.getPojoClassAdapter());
        } else System.out.println(coverageDetector.getName() + " not detected, ignoring");
    }

    public PojoClass adapt(PojoClass pojoClass) {
        return pojoClassCoverageAdapter.adapt(pojoClass);
    }

    public boolean include(PojoClass pojoClass) {
        return pojoClassCoverageFilter.include(pojoClass);
    }

}
