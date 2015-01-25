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

package com.openpojo.reflection.coverage.service;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.coverage.CoverageDetector;
import com.openpojo.reflection.coverage.impl.Clover3;
import com.openpojo.reflection.coverage.impl.Clover4;
import com.openpojo.reflection.coverage.impl.Cobertura;
import com.openpojo.reflection.coverage.impl.Jacoco;
import com.openpojo.reflection.coverage.service.impl.DefaultPojoCoverageFilterService;

/**
 * @author oshoukry
 */
public class PojoCoverageFilterServiceFactory {
    private static final CoverageDetector[] KNOWN_COVERAGE_DETECTORS = new CoverageDetector[] {
            Clover3.getInstance(),
            Clover4.getInstance(),
            Cobertura.getInstance(),
            Jacoco.getInstance()
    };

    public static PojoCoverageFilterService configureAndGetPojoCoverageFilterService() {
        PojoCoverageFilterService pojoCoverageFilterService = new DefaultPojoCoverageFilterService();
        for (CoverageDetector coverageDetector : KNOWN_COVERAGE_DETECTORS) {
            Logger logger = LoggerFactory.getLogger(PojoCoverageFilterServiceFactory.class);
            if (coverageDetector.isLoaded()) {
                logger.info(coverageDetector.getName() + " detected, " + "auto-configuring " + "OpenPojo to ignore its " + "structures" +
                        ".");
                pojoCoverageFilterService.registerCoverageDetector(coverageDetector);
            } else logger.info(coverageDetector.getName() + " not detected, ignoring");
        }
        return pojoCoverageFilterService;
    }

    public static PojoCoverageFilterService createPojoCoverageFilterServiceWith(CoverageDetector coverageDetector) {
        PojoCoverageFilterService pojoCoverageFilterService = new DefaultPojoCoverageFilterService();
        pojoCoverageFilterService.registerCoverageDetector(coverageDetector);
        return pojoCoverageFilterService;
    }
}
